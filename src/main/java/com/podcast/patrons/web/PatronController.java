package com.podcast.patrons.web;

import com.podcast.patrons.model.Patron;
import com.podcast.patrons.model.PatronList;
import com.podcast.patrons.service.CacheService;
import com.podcast.patrons.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static com.podcast.patrons.conf.CachesConst.PATRONS;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;
    private final CacheService cacheService;

    @RequestMapping(value = "/patrons", method = RequestMethod.GET)
    public PatronList getAllPatrons() throws IOException {
        List<Patron> patronList = patronService.getAllPatrons();
        return new PatronList(patronList.size(), patronList);
    }

    @RequestMapping(value = "/clearPatronsCaches", method = RequestMethod.GET)
    public ResponseEntity clearPatronsCaches() {
        cacheService.clearAllCaches(PATRONS);
        return new ResponseEntity<>(singletonMap("status", "done"), OK);
    }

}
