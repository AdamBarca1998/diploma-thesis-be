package sk.adambarca.managementframework.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.adambarca.managementframework.resource.Resource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/management")
final class ManagementController {

    private final ManagementService managementService;

    ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping
    List<Resource> getAll() {
        return managementService.getResourceList();
    }

    @GetMapping("/{type}")
    Optional<Resource> getResourceByType(@PathVariable String type) {
        return managementService.findResourceByType(type);
    }

    @PostMapping("/{classType}/{functionName}")
    ResponseEntity<Object> callFunction(
            @PathVariable String classType,
            @PathVariable String functionName,
            @RequestBody(required = false) Optional<Map<String, Object>> params
    ) {
        try {
            return ResponseEntity.ok(managementService.callFunction(classType, functionName, params));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
