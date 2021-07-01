package com.orange.prophet.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orange.prophet.BuildConfig
import com.orange.prophet.R
import com.orange.prophet.ui.api.QuestionEndpoint
import com.orange.prophet.ui.model.Question
import kotlinx.android.synthetic.main.view_option_text.view.*
import kotlinx.android.synthetic.main.view_stakced_card.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class QuizDetailActivity : AppCompatActivity() {

    private val ENDPOINT_URL = BuildConfig.SERVER_URL
    private var quizId: Int = 0
    private lateinit var questionEndpoint: QuestionEndpoint
    private lateinit var questionList: ArrayList<Question>

    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_quiz_detail)

        val intent = getIntent()
        quizId = intent.getIntExtra("quiz", 0)

        val retrofit: Retrofit = makeRetrofit()
        questionEndpoint = retrofit.create(QuestionEndpoint::class.java)
        questionList = ArrayList<Question>()

        inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        fetchContent(quizId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun inflate() {
        val container = findViewById<RelativeLayout>(R.id.container)
        var i=1
        for (question in questionList) {
            val view = this.inflater.inflate(R.layout.view_stakced_card, container, false)
            view.question_title.setText(question.title)
            for (option in question.option) {
                val optionView = this.inflater.inflate(
                    R.layout.view_option_text,
                    view.option_container,
                    false
                )
                optionView.option_text.text = option.desc
                view.option_container.addView(optionView)
            }
            var params = view.card_scrollable.getLayoutParams() as ViewGroup.MarginLayoutParams
            params.bottomMargin = 20 * i
            container.addView(view)
            view.card_scrollable.requestLayout()
            i++
        }
    }

    private fun fetchContent(quizId: Int) {
        val call = questionEndpoint.getQuestion(quizId)
        call.enqueue(object : Callback<ArrayList<Question>> {
            override fun onResponse(
                call: Call<ArrayList<Question>>,
                response: Response<ArrayList<Question>>
            ) {
                questionList.addAll(response.body()!!)
                inflate()
            }

            override fun onFailure(call: Call<ArrayList<Question>>, t: Throwable) {
                //${t.message}
                Toast.makeText(
                    this@QuizDetailActivity,
                    "Error occurred while fetching question",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}