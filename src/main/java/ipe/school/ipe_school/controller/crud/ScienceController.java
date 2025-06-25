package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.ScienceReq;
import ipe.school.ipe_school.models.dtos.res.ScienceDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ScienceRes;
import ipe.school.ipe_school.service.interfaces.ScienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+SCIENCE)
@RequiredArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;

    @PostMapping
    public ResponseEntity<ScienceRes> addScience(@RequestBody ScienceReq scienceReq){
        ScienceRes scienceRes=scienceService.addScience(scienceReq);
        return ResponseEntity.ok().body(scienceRes);
    }

    @GetMapping("/{scienceId}")
    public ResponseEntity<ScienceDetailsRes> getScience(@PathVariable Long scienceId){
        ScienceDetailsRes scienceDetailedRes=scienceService.getScienceByIdAndActive(scienceId);
        return ResponseEntity.ok().body(scienceDetailedRes);
    }

    @GetMapping
    public ResponseEntity<List<ScienceDetailsRes>> getAllSciences(){
        List<ScienceDetailsRes> scienceDetailsResList=scienceService.getAllSciencesByActive();
        return ResponseEntity.ok().body(scienceDetailsResList);
    }

    @PostMapping("/{scienceId}")
    public ResponseEntity<ScienceDetailsRes>  updateScience(@PathVariable Long scienceId,@RequestBody ScienceReq scienceReq){
        ScienceDetailsRes scienceDetailedRes=scienceService.updateScience(scienceId,scienceReq);
        return ResponseEntity.ok().body(scienceDetailedRes);
    }

    @DeleteMapping("/{scienceId}")
    public ResponseEntity<Void> deleteScience(@PathVariable Long scienceId) {
        scienceService.deleteScience(scienceId);
        return ResponseEntity.noContent().build();
    }

}
