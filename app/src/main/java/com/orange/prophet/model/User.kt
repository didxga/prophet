package com.orange.prophet.ui.model

import java.util.*

data class User(
        val email: String,
        val username: String,
        val firstname: String,
        val lastname: String,
)

data class Account(
        val user: User,
        val token: String,
)