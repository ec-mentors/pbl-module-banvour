package com.banvour.pomogatto.controller;

import com.banvour.pomogatto.dto.*;
import com.banvour.pomogatto.service.BreakActivityService;
import com.banvour.pomogatto.service.SessionEntryService;
import com.banvour.pomogatto.service.WorkPresetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    private final WorkPresetService workPresetService;
    private final BreakActivityService breakActivityService;
    private final SessionEntryService sessionEntryService;

    public WebController(WorkPresetService workPresetService, BreakActivityService breakActivityService, SessionEntryService sessionEntryService) {
        this.workPresetService = workPresetService;
        this.breakActivityService = breakActivityService;
        this.sessionEntryService = sessionEntryService;
    }

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("home"); // Show home.html
        List<WorkPresetDto> presets = workPresetService.getAllPresets();
        // Get the first preset as the default, or null if none exist
        if (!presets.isEmpty()) {
            modelAndView.addObject("defaultPreset", presets.get(0));
        } else {
            modelAndView.addObject("defaultPreset", null);
        }
        return modelAndView;
    }

    @GetMapping("/presets")
    public ModelAndView showPresetsPage() {
        List<WorkPresetDto> presets = workPresetService.getAllPresets();
        ModelAndView modelAndView = new ModelAndView("presets");
        modelAndView.addObject("preters", presets);
        return modelAndView;
    }

    @GetMapping("/presets/add")
    public ModelAndView showAddPresetForm() {
        ModelAndView modelAndView = new ModelAndView("add-preset");

        // 1. Provide an empty preset object for the form to bind to
        modelAndView.addObject("preset", new CreateOrUpdateWorkPresetDto());

        // 2. Provide the list of all break activities for the dropdown
        List<BreakActivityDto> activities = breakActivityService.getAllActivities();
        modelAndView.addObject("allActivities", activities);

        return modelAndView;
    }

    // --- NEW METHOD 2: Process the submitted form data ---
    @PostMapping("/presets/add")
    public String handleAddPresetForm(@ModelAttribute("preset") CreateOrUpdateWorkPresetDto presetDto) {
        // The presetDto object is automatically filled with form data by Spring
        workPresetService.createPreset(presetDto);

        // Redirect back to the main page to see the new list
        return "redirect:/";
    }

    @PostMapping("/presets/delete/{id}")
    public String handleDeletePreset(@PathVariable("id") Long id) {
        workPresetService.deletePreset(id);
        return "redirect:/"; // Redirect to the main list after deleting
    }

    // --- NEW METHOD 1: Show the form to edit an existing preset ---
    @GetMapping("/presets/edit/{id}")
    public ModelAndView showEditPresetForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit-preset");

        // 1. Fetch the existing preset data to populate the form
        // Note: We need a way to convert a Dto back to a CreateOrUpdateDto, or fetch the entity and map it.
        // Let's create a helper method in the service for this. (See next code block)
        CreateOrUpdateWorkPresetDto presetDto = workPresetService.getPresetForUpdate(id); // We'll create this method
        modelAndView.addObject("preset", presetDto);

        // 2. Fetch all activities for the dropdown, just like in the 'add' form
        List<BreakActivityDto> activities = breakActivityService.getAllActivities();
        modelAndView.addObject("allActivities", activities);

        modelAndView.addObject("presetId", id);

        return modelAndView;
    }

    // --- NEW METHOD 2: Process the submitted edit form ---
    @PostMapping("/presets/edit/{id}")
    public String handleUpdatePreset(@PathVariable("id") Long id, @ModelAttribute("preset") CreateOrUpdateWorkPresetDto presetDto) {
        workPresetService.updatePreset(id, presetDto);
        return "redirect:/";
    }

    @GetMapping("/activities")
    public ModelAndView showActivitiesPage() {
        ModelAndView modelAndView = new ModelAndView("activities"); // 1. The view name is "activities"

        // 2. Get all activities from the service
        List<BreakActivityDto> activities = breakActivityService.getAllActivities();

        // 3. Add the list to the model so the template can use it
        modelAndView.addObject("activities", activities);

        return modelAndView;
    }

    // --- NEW METHOD 1: Show the form for adding a new activity ---
    @GetMapping("/activities/add")
    public ModelAndView showAddActivityForm() {
        ModelAndView modelAndView = new ModelAndView("add-activity");
        // Provide an empty activity object for the form to bind to
        modelAndView.addObject("activity", new CreateOrUpdateBreakActivityDto());
        return modelAndView;
    }

    // --- NEW METHOD 2: Process the submitted form data ---
    @PostMapping("/activities/add")
    public String handleAddActivityForm(@ModelAttribute("activity") CreateOrUpdateBreakActivityDto activityDto) {
        breakActivityService.createActivity(activityDto);
        // Redirect to the activities list to see the new entry
        return "redirect:/activities";
    }

    // --- NEW METHOD: Handle the delete activity request ---
    @PostMapping("/activities/delete/{id}")
    public String handleDeleteActivity(@PathVariable("id") Long id) {
        // NOTE: You may need to handle cases where an activity is in use by a preset.
        // For now, we'll assume it can be deleted.
        breakActivityService.deleteActivity(id);
        return "redirect:/activities"; // Redirect back to the activities list
    }

    // --- NEW METHOD 1: Show the form to edit an existing activity ---
    @GetMapping("/activities/edit/{id}")
    public ModelAndView showEditActivityForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit-activity");

        // We'll create a helper method for this, just like with presets
        CreateOrUpdateBreakActivityDto activityDto = breakActivityService.getActivityForUpdate(id);
        modelAndView.addObject("activity", activityDto);
        modelAndView.addObject("activityId", id);

        return modelAndView;
    }

    // --- NEW METHOD 2: Process the submitted edit form ---
    @PostMapping("/activities/edit/{id}")
    public String handleUpdateActivity(@PathVariable("id") Long id, @ModelAttribute("activity") CreateOrUpdateBreakActivityDto activityDto) {
        breakActivityService.updateActivity(id, activityDto);
        return "redirect:/activities";
    }

    // --- NEW METHOD: Show the session history page ---
    @GetMapping("/history")
    public ModelAndView showHistoryPage() {
        ModelAndView modelAndView = new ModelAndView("history"); // 1. The view name is "history"

        // 2. Get all session entries from the service
        // We might want to sort this by most recent later, but for now, this is fine.
        List<SessionEntryDto> sessions = sessionEntryService.getAllSessionEntries();

        // 3. Add the list to the model
        modelAndView.addObject("sessions", sessions);

        return modelAndView;
    }
}
