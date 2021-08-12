package com.orange.prophet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.orange.prophet.R
import com.orange.prophet.ui.model.Option
import kotlinx.android.synthetic.main.view_option_text.view.*
import kotlinx.android.synthetic.main.view_stakced_card.view.*

class OptionViewAdapter (option: Option, var inflater: LayoutInflater, var container: QuestionViewAdapter, var isFinish: Boolean) {

    var optionView: View
        get() = field
    var optionId: Int
        get() = field

    init {
        optionView = this.inflater.inflate(
            R.layout.view_option_text, container.view.option_container,
            false
        )
        optionView.option_text.text = option.desc
        container.view.option_container.addView(optionView)
        optionId = option.id

        if(!isFinish) {
            hookEvent()
        } else {
            optionView.option_text.setBackgroundColor(Color.parseColor("#ababab"))
            optionView.option_text.setTextColor(Color.parseColor("#ffffff"))
        }
    }

    fun choose(myAnswer: Int) {
        if(myAnswer == optionId) {
            optionView.setBackgroundResource(R.drawable.text_selected_border)
            optionView.option_text.setTextColor(Color.parseColor("#2E2EFF"))
        }
    }

    private fun hookEvent() {
        optionView.setOnClickListener {
            container.resetChoose(this)
            optionView.setBackgroundResource(R.drawable.text_selected_border)
            optionView.option_text.setTextColor(Color.parseColor("#2E2EFF"))
        }
    }
}