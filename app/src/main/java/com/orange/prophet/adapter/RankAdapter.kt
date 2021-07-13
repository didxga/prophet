package com.orange.prophet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.orange.prophet.R
import com.orange.prophet.ui.model.Rank
import kotlinx.android.synthetic.main.card_layout.view.card_view
import kotlinx.android.synthetic.main.leaderboard_card_layout.view.*


class RankAdapter(
        private var rankList: ArrayList<Rank>
) : RecyclerView.Adapter<RankAdapter.QuizViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): QuizViewHolder {
        val itemView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.leaderboard_card_layout, viewGroup, false)
        return QuizViewHolder(itemView.card_view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val rank: Rank = rankList.get(position)
        setPropertiesForArticleViewHolder(holder, rank, position)
    }

    private fun setPropertiesForArticleViewHolder(holder: QuizViewHolder, rank: Rank, position: Int) {
        holder.cardView.rank_number.text = (position + 4).toString()
        holder.cardView.rank_username.text = rank.username
        holder.cardView.rank_score.text = rank.score
    }

    override fun getItemCount(): Int {
        return rankList.size
    }

    inner class QuizViewHolder(private val view: CardView) : RecyclerView.ViewHolder(view) {
        val cardView: CardView by lazy { view }
    }

    fun setQuizList(quizList: ArrayList<Rank>) {
        this.rankList = quizList
    }

}