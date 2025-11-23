package com.example.userimport.controller

import java.io.File
import java.util.*

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import com.example.userimport.event.EventBus
import com.example.userimport.event.FileUploadedEvent

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/files")
class CsvUploadController(
    private val eventBus: EventBus
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (!file.originalFilename!!.endsWith(".csv", ignoreCase = true)) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed")
        }

        // Save file temporarily
        val uploadDir = File(System.getProperty("java.io.tmpdir"), "uploads")
        if (!uploadDir.exists()) uploadDir.mkdirs()
        val savedFile = File(uploadDir, "${UUID.randomUUID()}_${file.originalFilename!!}")
        file.transferTo(savedFile)

        // Publish event for background processing using injected EventBus
        eventBus.publish(FileUploadedEvent(savedFile.absolutePath))

        return ResponseEntity.ok("File uploaded successfully — processing started")
    }
}
