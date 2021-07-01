package com.orange.prophet.ui.model

import java.util.*
import kotlin.collections.ArrayList

data class Quiz(
      val id: Int,
      val title: String,
      val createdAt: Date,
      val endAt: Date,
      val status: String,
      val questions: ArrayList<Question>
)
