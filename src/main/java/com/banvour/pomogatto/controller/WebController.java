package com.banvour.pomogatto.controller;

import com.banvour.pomogatto.dto.WorkPresetDto;
import com.banvour.pomogatto.service.WorkPresetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    private final WorkPresetService service;

    public WebController(WorkPresetService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView getIndexPage() {
        List<WorkPresetDto> presets = service.getAllPresets();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("preters", presets);
        return modelAndView;
    }
}
