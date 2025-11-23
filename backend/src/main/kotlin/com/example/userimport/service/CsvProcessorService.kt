package com.example.userimport.service

import java.io.File
import jakarta.validation.Validation
import jakarta.validation.Validator

import com.example.userimport.model.User

import org.springframework.stereotype.Service

@Service
class CsvProcessorService {

    private val validator: Validator = Validation.buildDefaultValidatorFactory().validator

    fun process(file: File) {
        println("Processing CSV: ${file.absolutePath}")

        val lines = file.readLines().filter { it.isNotBlank() }

        if (lines.isEmpty()) {
            println("CSV is empty")
            return
        }

        // Detect header: simple check if first line contains column names
        val firstLine = lines.first()
        val hasHeader = firstLine.lowercase().contains("id") &&
                firstLine.lowercase().contains("firstname") &&
                firstLine.lowercase().contains("lastname") &&
                firstLine.lowercase().contains("email")

        val dataLines = if (hasHeader) lines.drop(1) else lines

        for (line in dataLines) {
            try {
                val parts = line.split(",")
                if (parts.size < 4) {
                    println("Skipping invalid row (missing columns): [$line]")
                    continue
                }

                val user = User(
                    id = parts[0].trim(),
                    firstName = parts[1].trim(),
                    lastName = parts[2].trim(),
                    email = parts[3].trim()
                )

                // Validate user
                val violations = validator.validate(user)
                if (violations.isNotEmpty()) {
                    violations.forEach { v ->
                        println("Validation error on field '${v.propertyPath}': ${v.message} in row: [$line]")
                    }
                    continue
                }

                println("Parsed user: $user")

            } catch (e: Exception) {
                println("Error processing row: $line, ${e.message}")
            }
        }

        println("Finished processing CSV: ${file.name}")
    }
}