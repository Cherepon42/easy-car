package by.easycar.controller;

import by.easycar.model.Payment;
import by.easycar.model.administration.Admin;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.dto.ModerationResponse;
import by.easycar.model.dto.user.UserInnerResponse;
import by.easycar.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get advertisements for moderation", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/advertisements", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Advertisement>> getAdvertisementsForModeration() {
        List<Advertisement> advertisements = adminService.getAllAdvertisementsNotModerated();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Operation(summary = "Get inner user", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/get-user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<UserInnerResponse> getInnerUser(@PathVariable Long userId) {
        UserInnerResponse user = adminService.getUserInner(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Get inner advertisement", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/advertisement/{adId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Advertisement> getInnerAdvertisement(@PathVariable Long adId) {
        Advertisement advertisement = adminService.getInnerAdvertisement(adId);
        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @Operation(summary = "Delete user", security = {@SecurityRequirement(name = "Admin JWT")})
    @DeleteMapping("/user")
    private ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
        return new ResponseEntity<>("User was deleted.", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete advertisement", security = {@SecurityRequirement(name = "Admin JWT")})
    @DeleteMapping("/advertisement")
    private ResponseEntity<String> deleteAdvertisement(@RequestParam Long adId) {
        adminService.deleteAdvertisement(adId);
        return new ResponseEntity<>("Advertisement was deleted.", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get payments of user", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/payments/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Payment>> getAllPaymentsOfUser(@PathVariable Long userId) {
        List<Payment> payments = adminService.getAllPaymentsOfUser(userId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Operation(summary = "Accept moderation for advertisement", security = {@SecurityRequirement(name = "Admin JWT")})
    @PutMapping("/accept-advertisement")
    private ResponseEntity<String> acceptAdvertisement(@RequestParam Long adId,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        adminService.acceptAdvertisement(adId, admin);
        return new ResponseEntity<>("Advertisement was accepted.", HttpStatus.OK);
    }

    @Operation(summary = "Reject moderation for advertisement", security = {@SecurityRequirement(name = "Admin JWT")})
    @PutMapping("/reject-advertisement")
    private ResponseEntity<String> rejectAdvertisement(@RequestParam Long adId,
                                                       @RequestParam String message,
                                                       @AuthenticationPrincipal @Parameter(hidden = true) Admin admin) {
        adminService.rejectAdvertisement(adId, admin, message);
        return new ResponseEntity<>("Advertisement was rejected.", HttpStatus.OK);
    }

    @Operation(summary = "Register new admin", security = {@SecurityRequirement(name = "Admin JWT")})
    @PostMapping("/add")
    private ResponseEntity<String> addNewAdmin(@RequestBody Admin admin) {
        adminService.saveNewAdmin(admin);
        return new ResponseEntity<>("Admin was added.", HttpStatus.OK);
    }

    @Operation(summary = "Get advertisements of user", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/advertisements/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Advertisement>> getAllAdvertisementsOfUser(@PathVariable Long userId) {
        List<Advertisement> advertisements = adminService.getAllAdvertisementsOfUser(userId);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Operation(summary = "Get moderation by user", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/moderation/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<ModerationResponse>> getAllModerationByUser(@PathVariable Long userId) {
        List<ModerationResponse> moderation = adminService.getModerationOfUser(userId);
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @Operation(summary = "Get all moderation", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping(path = "/moderation", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<ModerationResponse>> getAllModeration() {
        List<ModerationResponse> moderation = adminService.getAllModeration();
        return new ResponseEntity<>(moderation, HttpStatus.OK);
    }

    @Operation(summary = "Delete image", security = {@SecurityRequirement(name = "Admin JWT")})
    @DeleteMapping("/image/{adId}/{imageUUID}")
    private ResponseEntity<String> deleteImage(@PathVariable String imageUUID, @PathVariable Long adId) throws IOException {
        adminService.deleteImage(adId, imageUUID);
        return new ResponseEntity<>("Image was deleted.", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all inner users", security = {@SecurityRequirement(name = "Admin JWT")})
    @GetMapping("/user")
    private ResponseEntity<List<UserInnerResponse>> getAllInnerUsers() {
        return new ResponseEntity<>(adminService.getAllInnerUsers(), HttpStatus.OK);
    }
}