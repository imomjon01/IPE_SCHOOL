package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.ScienceReq;
import ipe.school.ipe_school.models.dtos.res.ScienceDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ScienceRes;
import ipe.school.ipe_school.service.interfaces.ScienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + SCIENCE)
@RequiredArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ScienceRes> addScience(@RequestBody ScienceReq scienceReq) {
        ScienceRes scienceRes = scienceService.addScience(scienceReq);
        return ResponseEntity.ok().body(scienceRes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{scienceId}")
    public ResponseEntity<ScienceDetailsRes> getScience(@PathVariable Long scienceId){
        ScienceDetailsRes scienceDetailedRes=scienceService.getScienceByIdAndActive(scienceId);
        return ResponseEntity.ok().body(scienceDetailedRes);
    }

    @PreAuthorize("hasAnyRole('MENTOR','ADMIN','SUPER_MENTOR')")
    @GetMapping
    public ResponseEntity<List<ScienceDetailsRes>> getAllSciences() {
        List<ScienceDetailsRes> scienceDetailsResList = scienceService.getAllSciencesByActive();
        return ResponseEntity.ok().body(scienceDetailsResList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{scienceId}")
    public ResponseEntity<ScienceDetailsRes> updateScience(@PathVariable Long scienceId, @RequestBody ScienceReq scienceReq) {
        ScienceDetailsRes scienceDetailedRes = scienceService.updateScience(scienceId, scienceReq);
        return ResponseEntity.ok().body(scienceDetailedRes);
    }

    @DeleteMapping("/{scienceId}")
    public ResponseEntity<Void> deleteScience(@PathVariable Long scienceId) {
        scienceService.deleteScience(scienceId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public ResponseEntity<Long> getScienceCount() {
        Long count = scienceService.getScienceCount();
        return ResponseEntity.ok().body(count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{scienceId}/groups/{groupId}")
    public ResponseEntity<Void> deleteScienceFromGroup(@PathVariable Long scienceId, @PathVariable Long groupId) {
        scienceService.deleteScienceFromGroup(scienceId, groupId);
        return ResponseEntity.noContent().build();
    }

}
