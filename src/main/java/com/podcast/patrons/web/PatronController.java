package com.podcast.patrons.web;

import com.podcast.patrons.model.Patron;
import com.podcast.patrons.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;

    @RequestMapping(value = "/patrons", method = RequestMethod.GET)
    public List<Patron> getAllPatrons() throws IOException {
        return patronService.getAllPatrons();
    }
}
