package com.codebeforedawn.be.areas;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreasController {

    @GetMapping("{areaId}")
    public String getAreaById() {
        return "area";
    }
}
