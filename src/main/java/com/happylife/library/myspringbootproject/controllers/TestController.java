package com.happylife.library.myspringbootproject.controllers;

import com.happylife.library.myspringbootproject.dto.PeriodicalDTO;
import com.happylife.library.myspringbootproject.dto.SubscriberDTO;
import com.happylife.library.myspringbootproject.payload.request.PostNewPeriodicalRequest;
import com.happylife.library.myspringbootproject.payload.request.SubscribeRequest;
import com.happylife.library.myspringbootproject.payload.response.MessageResponse;
import com.happylife.library.myspringbootproject.security.services.library.LibraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final LibraryService libraryService;

    public TestController(@Autowired LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/post-new")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> postNewPeriodical(@RequestParam String publisher,
                                               @RequestParam String name,
                                               @RequestParam String description,
                                               @RequestParam String available,
                                               @RequestParam MultipartFile file) throws IOException {

        PostNewPeriodicalRequest request = new PostNewPeriodicalRequest(
                publisher,
                name,
                description,
                available,
                file
        );

        libraryService.postNewPeriodical(request);

        return ResponseEntity.ok(new MessageResponse("Post created successfully"));
    }

    @GetMapping("/all-periodicals")
/*    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')") */
    public ResponseEntity<?> allPeriodicals() {
        List<PeriodicalDTO> periodicals = libraryService.getAllPeriodicals();
        return ResponseEntity.ok().body(periodicals);
    }

    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<?> subscribeTo(@RequestBody SubscribeRequest subscribeRequest) {

        libraryService.subscribeTo
                (subscribeRequest.getPeriodicalId(), subscribeRequest.getUsername());

        List<SubscriberDTO> subscribersList = libraryService
                .getSubscribersOfPeriodicalId(subscribeRequest.getPeriodicalId());

        return ResponseEntity.ok().body(subscribersList);
    }

    @PostMapping("/unsubscribe")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity<?> unsubscribeFrom(@RequestBody SubscribeRequest subscribeRequest) {

        libraryService.unsubscribeFrom
                (subscribeRequest.getPeriodicalId(), subscribeRequest.getUsername());

        List<SubscriberDTO> subscribersList = libraryService.
                getSubscribersOfPeriodicalId(subscribeRequest.getPeriodicalId());

        return ResponseEntity.ok().body(subscribersList);
    }

}
