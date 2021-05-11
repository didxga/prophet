package com.orange.prophet.ui.model

import java.util.*

data class User(
        val id: String,
        val userName: String,
        val firstName: String,
        val LastName: String,
        val phone: String,
        val email: String,
        val createdAt: Date,
        val password: String,
        val status: String
)
