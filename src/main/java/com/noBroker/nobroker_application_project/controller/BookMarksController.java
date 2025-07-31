package com.noBroker.nobroker_application_project.controller;

import com.noBroker.nobroker_application_project.model.User;
import com.noBroker.nobroker_application_project.service.BookMarksService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class BookMarksController {

    private final BookMarksService bookMarksService;

    public BookMarksController(BookMarksService bookMarksService) {
        this.bookMarksService = bookMarksService;
    }

    @PostMapping("/toggleBookmark/{propertyId}")
    @ResponseBody
    public Map<String, Boolean> toggleBookmark(@PathVariable Long propertyId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        boolean bookmarked = bookMarksService.saveBookMarks(propertyId, user);

        return Map.of(
                "success", true,
                "bookmarked", bookmarked
        );
    }

    @PostMapping("/removeBookMarks/{userId}/{propertyId}")
    public String removeBookMarka(@PathVariable("userId") Long userId, @PathVariable("propertyId") Long propertyId) {
        bookMarksService.removeBookmarks(propertyId, userId);

        return "redirect:/shortlisted-properties/"+userId;
    }
}