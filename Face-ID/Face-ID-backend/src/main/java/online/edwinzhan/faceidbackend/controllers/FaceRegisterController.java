package online.edwinzhan.faceidbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FaceRegisterController {
    @PostMapping("/register")
    public ResponseEntity<String> registerFace(
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2,
            @RequestParam("image3") MultipartFile image3) {

        // TODO: Handle the images, save or validate as per requirements

        // TODO: Add face recognition logic here in future
        // Placeholder response until we implement logic
        return ResponseEntity.ok("Images received, face recognition pending implementation!");
    }
}
