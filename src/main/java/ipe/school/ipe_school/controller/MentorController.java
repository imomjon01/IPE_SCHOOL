package ipe.school.ipe_school.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MentorController {

    @GetMapping()
    public HttpEntity<?> getGroups() {
        return null;
    }

}
