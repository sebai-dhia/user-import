package com.example.userimport.event

import java.io.File

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

import org.springframework.stereotype.Component

import com.example.userimport.service.CsvProcessorService

@Component
class EventBus(
    private val csvProcessorService: CsvProcessorService
) {
    private val channel = Channel<FileUploadedEvent>(Channel.UNLIMITED)
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        repeat(4) {
            scope.launch {
                for (event in channel) {
                    try {
                        csvProcessorService.process(File(event.filePath))
                    } catch (e: Exception) {
                        println("Error processing file ${event.filePath}: ${e.message}")
                    }
                }
            }
        }
    }

    fun publish(event: FileUploadedEvent) {
        scope.launch { channel.send(event) }
    }
}