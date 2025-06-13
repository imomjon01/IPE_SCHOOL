package ipe.school.ipe_school.controller;

import ipe.school.ipe_school.models.dtos.req.ModuleReq;
import ipe.school.ipe_school.models.dtos.res.ModuleDetailsRes;
import ipe.school.ipe_school.models.dtos.res.ModuleRes;
import ipe.school.ipe_school.service.interfaces.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ipe.school.ipe_school.utils.ApiConstants.*;

@RestController
@RequestMapping(API_PATH+API_VERSION+MODULE)
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<ModuleRes> createModule(@RequestBody ModuleReq moduleReq) {
        ModuleRes moduleRes =moduleService.createModule(moduleReq);
        return new ResponseEntity<>(moduleRes, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ModuleRes>> getModules() {
        List<ModuleRes> moduleReses=moduleService.getAllModulesBy_active();
        return new ResponseEntity<>(moduleReses, HttpStatus.OK);
    }

    @GetMapping("/{module_id}")
    public ResponseEntity<ModuleDetailsRes> getModuleById(@PathVariable Long module_id) {
        ModuleDetailsRes moduleDetailsRes=moduleService.getModuleById(module_id);
        return new ResponseEntity<>(moduleDetailsRes, HttpStatus.OK);
    }
}
