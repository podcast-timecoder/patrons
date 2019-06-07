package com.podcast.patrons.service;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.patreon.PatreonAPI;
import com.patreon.resources.Campaign;
import com.patreon.resources.Pledge;
import com.podcast.patrons.CampaingNotFoundException;
import com.podcast.patrons.model.Patron;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatreonAPI patreonAPI;

    @Value("${campaign}")
    private String campaign;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return fetchAllPledges()
                .stream()
                .map(this::createPatron)
                .filter(Patron::isActive)
                .collect(Collectors.toList());
    }

    private Set<Pledge> fetchAllPledges() {
        Set<Pledge> pledges = new HashSet<>();
        final Campaign campaignAutomationRemarks = getCampaign(patreonAPI);
        final Collection<Pledge.PledgeField> optionalFields = new ArrayList<>();
        optionalFields.add(Pledge.PledgeField.TotalHistoricalAmountCents);

        String cursor = null;
        while (true) {
            JSONAPIDocument<List<Pledge>> pledgesPage = getListJSONAPIDocument(campaignAutomationRemarks, optionalFields, cursor);

            pledges.addAll(pledgesPage.get());
            cursor = patreonAPI.getNextCursorFromDocument(pledgesPage);
            if (cursor == null) {
                break;
            }
        }
        return pledges;
    }

    private JSONAPIDocument<List<Pledge>> getListJSONAPIDocument(Campaign campaignAutomationRemarks, Collection<Pledge.PledgeField> optionalFields, String cursor) {
        try {
            return patreonAPI.fetchPageOfPledges(campaignAutomationRemarks.getId(), 12, cursor, optionalFields);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Campaign getCampaign(PatreonAPI patreonAPI) {
        try {
            return patreonAPI
                    .fetchCampaigns().get()
                    .stream()
                    .filter(campaign -> campaign.getCreationName().equals(this.campaign))
                    .findFirst().orElseThrow(CampaingNotFoundException::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Patron createPatron(Pledge pledge) {
        Patron patron = new Patron();
        patron.setId(Long.parseLong(pledge.getPatron().getId()));
        patron.setFullName(pledge.getPatron().getFullName());
        patron.setAmount(pledge.getAmountCents());
        patron.setTotalAmount(pledge.getTotalHistoricalAmountCents());
        if (pledge.getDeclinedSince() == null) {
            patron.setActive(true);
        }
        patron.setEmail(pledge.getPatron().getEmail());

        return patron;
    }
}
