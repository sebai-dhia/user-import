package com.example.userimport.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class User(
    @field:NotBlank(message = "Id cannot be blank")
    val id: String,

    @field:NotBlank(message = "First name cannot be blank")
    @field:Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    val firstName: String,

    @field:NotBlank(message = "Last name cannot be blank")
    @field:Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    val lastName: String,

    @field:NotBlank(message = "Email name cannot be blank")
    @field:Email(message = "Email must be a valid email address")
    val email: String
)