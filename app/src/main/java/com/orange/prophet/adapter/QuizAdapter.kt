package com.orange.prophet.ui.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.orange.prophet.R
import com.orange.prophet.ui.QuizDetailActivity
import com.orange.prophet.ui.model.Quiz
import kotlinx.android.synthetic.main.card_layout.view.*


class QuizAdapter(
        private var quizList: ArrayList<Quiz>
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): QuizViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)

        return QuizViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz: Quiz = quizList.get(position)
        setPropertiesForArticleViewHolder(holder, quiz)
        holder.cardView.setOnClickListener {
            val intent = Intent(holder.cardView.context, QuizDetailActivity::class.java)
            intent.putExtra("quiz", quizList.get(position).id)
            holder.cardView.context.startActivity(intent)
        }
    }

    private fun setPropertiesForArticleViewHolder(holder: QuizViewHolder, quiz: Quiz) {
        holder.cardView.idProphetQuizTitle.text = quiz?.title
        holder.cardView.idProphetQuizDes.text = quiz?.title
        if(quiz?.status == "=open") {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#eedcc5"))
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#e38342"))
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    inner class QuizViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView by lazy { view.card_view}
    }

    fun setQuizList(quizList: ArrayList<Quiz>) {
        this.quizList = quizList
    }
}