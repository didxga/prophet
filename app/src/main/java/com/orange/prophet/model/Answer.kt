package com.orange.prophet.ui.model

import java.util.*

data class Answer(
        val id: String,
        val questionId: String,
        val answer: String,
        val userId: String,
        val answerAt: Date,
        val updatedAt: Date
)
