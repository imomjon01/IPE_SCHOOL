package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.utils.ApiConstants;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ipe.school.ipe_school.utils.ApiConstants.API_PATH;
import static ipe.school.ipe_school.utils.ApiConstants.API_VERSION;

@RestController
@RequestMapping(API_PATH + API_VERSION + "/mentor")
public class MentorController {

    @GetMapping()
    public HttpEntity<?> getGroups() {
        System.out.println("Keldi");
        return null;
    }

}
