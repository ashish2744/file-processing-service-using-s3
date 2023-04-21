package com.s3.controller;

import com.s3.service.FileStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileStorageController {

    private FileStorageService service;

    public FileStorageController(FileStorageService service) {
        this.service = service;
    }

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file){
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
        byte[] downloadFile = service.downloadFile(fileName);
        ByteArrayResource byteArrayResource = new ByteArrayResource(downloadFile);
        return ResponseEntity
                .ok()
                .contentLength(downloadFile.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition","attachment; filemame\""+fileName+"\"")
                .body(byteArrayResource);
    }

    @GetMapping("/convert-csv-to-json/{bucketName}/{fileName}")
    public ResponseEntity<String> convertCsvToJson(@PathVariable String bucketName, @PathVariable String fileName) throws IOException {
        return new ResponseEntity<>(service.convertCsvToJson(bucketName,fileName),HttpStatus.OK);
    }

}
