package com.orange.prophet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange.prophet.R
import com.orange.prophet.ui.model.Question
import kotlinx.android.synthetic.main.view_option_text.view.*
import kotlinx.android.synthetic.main.view_stakced_card.view.*

class QuestionViewAdapter(val question: Question,
                          var inflater: LayoutInflater,
                          var container: ViewGroup,
                          var isFinish: Boolean
) {

    var view: View
        get() = field
    var choice: Int = -1
        get() = field
    var chosen: Boolean = false
        get() = field
    private var optionsList = ArrayList<OptionViewAdapter>()

   init {
       view = this.inflater.inflate(R.layout.view_stakced_card, container, false)
       view.question_title.setText(question.title)

       for (option in question.option) {
           val ov = OptionViewAdapter(option, inflater, this, isFinish)
           optionsList.add(ov)
       }
   }

    fun setChoose(myAnswer: Int) {
        for ( option in optionsList) {
            option.choose(myAnswer)
        }
    }

    fun resetChoose(optionView: OptionViewAdapter) {
        chosen = true
        choice = optionView.optionId
        for ( option in optionsList) {
            option.optionView.setBackgroundResource(R.drawable.text_border)
            option.optionView.option_text.setTextColor(Color.parseColor("#878787"))
        }
    }
}

