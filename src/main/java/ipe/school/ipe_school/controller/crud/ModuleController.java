package ipe.school.ipe_school.controller.crud;

import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.models.dtos.res.TaskRes;
import ipe.school.ipe_school.models.entity.User;
import ipe.school.ipe_school.models.repo.UserRepository;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import ipe.school.ipe_school.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH + API_VERSION + MODULE)
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @PostMapping
    public ResponseEntity<ModuleRes> createModule(@RequestBody ModuleReq moduleReq) {
        ModuleRes moduleRes = moduleService.createModule(moduleReq);
        return new ResponseEntity<>(moduleRes, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<ModuleRes>> getModules(@AuthenticationPrincipal User user) {
        List<ModuleRes> moduleReses = moduleService.getAllModulesBy_active(user);
        return new ResponseEntity<>(moduleReses, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_MENTOR','MENTOR')")
    @GetMapping("/{moduleId}")
    public ResponseEntity<ModuleDetailsRes> getModuleById(@PathVariable Long moduleId) {
        ModuleDetailsRes moduleDetailsRes = moduleService.getModuleById(moduleId);
        return new ResponseEntity<>(moduleDetailsRes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('STUDNET')")
    @GetMapping("/get/{moduleId}")
    public ResponseEntity<List<TaskRes>> getTasksByModule(@AuthenticationPrincipal User user, @PathVariable Long moduleId) {
        List<TaskRes> taskRes = moduleService.getAllModuleById(user, moduleId);
        return new ResponseEntity<>(taskRes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPER_MENTOR')")
    @PostMapping("/update/{moduleId}")
    public ResponseEntity<ModuleDetailsRes> updateModule(@PathVariable Long moduleId, @RequestBody ModuleReq moduleReq) {
        ModuleDetailsRes moduleDetailsRes = moduleService.updateModule(moduleId, moduleReq);
        return new ResponseEntity<>(moduleDetailsRes, HttpStatus.OK);
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<ModuleRes> deleteModule(@PathVariable Long moduleId) {
        moduleService.updateModule_Active(moduleId, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{moduleId}")
    public ResponseEntity<Void> updateModule(@PathVariable Long moduleId) {
        moduleService.updateModule_Active(moduleId, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
