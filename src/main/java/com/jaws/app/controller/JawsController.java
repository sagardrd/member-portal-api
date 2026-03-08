package com.jaws.app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jaws.app.model.AttendanceRequest;
import com.jaws.app.model.EventModel;
import com.jaws.app.model.JawsModel;
import com.jaws.app.repository.JawsService;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@CrossOrigin(origins = "http://localhost:4200")
public class JawsController {

    private final JawsService jawsService;

    @Value("${photo.folder.path}")
    private String photoFolderPath;

    @GetMapping("/byMembershipNo/{membershipNo}")
    public ResponseEntity<List<JawsModel>> getByMembershipNo(@PathVariable String membershipNo) {
        List<JawsModel> members = jawsService.getByMembershipNo(membershipNo);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<List<JawsModel>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(jawsService.getByName(name));
    }

    @GetMapping("/byMobileNo/{mobileNo}")
    public ResponseEntity<List<JawsModel>> getByMobileNo(@PathVariable String mobileNo) {
        return ResponseEntity.ok(jawsService.getByMobileNo(mobileNo));
    }

    @PostMapping("/attendance")
    public ResponseEntity<String> storeAttendance(@RequestBody AttendanceRequest request) {

        jawsService.storeAttendance(request.getEventId(), request.getUniqueId());
        return ResponseEntity.ok("Attendance stored successfully.");
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventModel>> getEventList() {
        return ResponseEntity.ok(jawsService.getAllEvents());
    }

    @GetMapping(value = "/downloadPDF/{eventId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadEventPdf(@PathVariable Long eventId) {

        byte[] pdfBytes = jawsService.generateAttendancePdf(eventId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("event-" + eventId + "-attendance.pdf")
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/{membershipNo}/photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String membershipNo,
            @RequestParam("file") MultipartFile file) throws Exception {

        jawsService.updatePhoto(membershipNo, file.getBytes());
        return ResponseEntity.ok("Photo uploaded successfully.");
    }

    @PostMapping("/bulk-photo-upload")
    public ResponseEntity<String> bulkPhotoUpload() throws Exception {

        File folder = new File(photoFolderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return ResponseEntity.badRequest()
                    .body("Photo folder does not exist: " + photoFolderPath);
        }

        File[] files = folder.listFiles((dir, name) ->
                name.toLowerCase().matches(".*\\.(jpg|jpeg|png)$"));

        if (files == null || files.length == 0) {
            return ResponseEntity.ok("No photo files found.");
        }

        int updated = 0;

        for (File file : files) {
            String mobileNo = file.getName()
                    .substring(0, file.getName().lastIndexOf('.'));

            byte[] photoBytes = Files.readAllBytes(file.toPath());
            jawsService.updatePhotoByMobileNo(mobileNo, photoBytes);
            updated++;
        }

        return ResponseEntity.ok("Bulk upload completed. Updated " + updated + " records.");
    }
}