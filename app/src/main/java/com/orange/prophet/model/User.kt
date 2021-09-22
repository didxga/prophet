package com.orange.prophet.ui.model

data class User(
        var email: String,
        var username: String,
        var firstname: String,
        var lastname: String,
)

data class Account(
        var user: User ,
        var token: String,
)