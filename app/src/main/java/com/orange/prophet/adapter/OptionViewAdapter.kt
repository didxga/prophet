package com.orange.prophet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.orange.prophet.R
import com.orange.prophet.ui.model.Option
import kotlinx.android.synthetic.main.view_option_result.view.*
import kotlinx.android.synthetic.main.view_option_text.view.*
import kotlinx.android.synthetic.main.view_option_text.view.option_text
import kotlinx.android.synthetic.main.view_stakced_card.view.*

class OptionViewAdapter (option: Option, var inflater: LayoutInflater, var container: QuestionViewAdapter, var isFinish: Boolean) {

    var optionView: View
        get() = field
    var optionId: Int
        get() = field

    init {
        optionView = this.inflater.inflate(
            R.layout.view_option_result, container.view.option_container,
            false
        )
        optionView.option_text.text = option.desc
        container.view.option_container.addView(optionView)
        optionId = option.id

        if(!isFinish) {
            optionView.option_percentage_bar.isVisible = false
            optionView.option_percentage_text.isVisible = false
            optionView.setBackgroundResource(R.drawable.text_border)
            optionView.option_text.setTextColor(Color.parseColor("#878787"))
            hookEvent()

        } else {
            optionView.option_text.setTextColor(Color.parseColor("#ffffff"))
            optionView.setBackgroundResource(R.drawable.item_finished_boder)
            optionView.option_percentage_bar.progress = 10
            optionView.option_percentage_bar.isEnabled = false
            optionView.option_percentage_text.text = "10%"
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