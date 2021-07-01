package com.orange.prophet.ui.model

import kotlin.collections.ArrayList

data class Question(
        val id: String,
        val quizId: String,
        val title: String,
        val desc: String,
        val type: String,
        val option: ArrayList<Option>
)
