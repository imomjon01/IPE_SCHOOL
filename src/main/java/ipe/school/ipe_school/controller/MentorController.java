package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.service.interfaces.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + MENTOR)
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;




}
