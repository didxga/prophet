package com.orange.prophet.ui.model

import java.util.*

data class UserParticipation(
        val id: String,
        val quizId: String,
        val score: Float,
        val isCompleted: Boolean,
        val judge: String,
        val correctSubmitAt: Date
)
