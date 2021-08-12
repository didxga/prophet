package com.orange.prophet.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.orange.prophet.R
import com.orange.prophet.ui.QuizDetailActivity
import com.orange.prophet.ui.model.Quiz
import kotlinx.android.synthetic.main.card_layout.view.*
import kotlinx.android.synthetic.main.card_layout.view.idProphetQuizDes
import kotlinx.android.synthetic.main.card_layout.view.idProphetQuizTitle
import kotlinx.android.synthetic.main.card_openquiz_layout.view.*


class QuizAdapter(
        private var quizList: ArrayList<Quiz>
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): QuizViewHolder {
        if(p1 == 1) {
            val openitemView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_openquiz_layout, viewGroup, false)
            return QuizViewHolder(openitemView.card_open_view)
        } else {
            val itemView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
            return QuizViewHolder(itemView.card_view)
        }

    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz: Quiz = quizList.get(position)
        setPropertiesForArticleViewHolder(holder, quiz)
    }

    private fun setPropertiesForArticleViewHolder(holder: QuizViewHolder, quiz: Quiz) {
        if(quiz?.status == "=open") {
            holder.cardView.idProphetQuizTitle.text = quiz?.title
            holder.cardView.idProphetQuizDes.text = quiz?.title
            holder.cardView.setOnClickListener {
                val intent = Intent(holder.cardView.context, QuizDetailActivity::class.java)
                intent.putExtra("quiz", quiz.id)
                intent.putExtra("isfinish", false)
                holder.cardView.context.startActivity(intent)
            }
        } else {
            holder.cardView.idProphetQuizTitle.text = quiz?.title
            holder.cardView.idProphetQuizDes.text = quiz?.title
            holder.cardView.setOnClickListener {
                val intent = Intent(holder.cardView.context, QuizDetailActivity::class.java)
                intent.putExtra("quiz", quiz.id)
                intent.putExtra("isfinish", true)
                holder.cardView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    inner class QuizViewHolder(private val view: CardView) : RecyclerView.ViewHolder(view) {
        val cardView: CardView by lazy { view }
    }

    fun setQuizList(quizList: ArrayList<Quiz>) {
        this.quizList = quizList
    }

    override fun getItemViewType(position: Int): Int {
        if (quizList[position].status == "=open") {
            return 1
        } else {
            return 2
        }
    }
}