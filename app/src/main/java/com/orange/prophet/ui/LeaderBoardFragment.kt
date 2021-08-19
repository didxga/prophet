package com.orange.prophet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.orange.prophet.BuildConfig
import com.orange.prophet.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

import android.util.Log
import com.orange.prophet.ui.adapter.RankAdapter
import com.orange.prophet.ui.api.RankEndpoint
import com.orange.prophet.ui.model.Rank
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*

class LeaderBoardFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val ENDPOINT_URL = BuildConfig.SERVER_URL
    private lateinit var rankEndpoint: RankEndpoint
    private lateinit var rankList: ArrayList<Rank>
    private lateinit var rankAdapter: RankAdapter
    private lateinit var layoutView: View

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var loading = false

    private var visibleThreshold = 2
    private var curPage = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutView =  inflater.inflate(R.layout.fragment_leaderboard, container, false)
        return layoutView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofit: Retrofit = makeRetrofit()
        rankEndpoint = retrofit.create(RankEndpoint::class.java)
        rankList = ArrayList<Rank>()
        rankAdapter = RankAdapter(rankList)

        linearLayoutManager = LinearLayoutManager(requireContext())
        leaderbord_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = rankAdapter
        }

        swipe_refresh.setEnabled(false)

        hookScrollListenerForRecycleView()
        fetchContent(curPage)
    }


    private fun hookScrollListenerForRecycleView() {
        leaderbord_recycler_view
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView,
                                        dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = linearLayoutManager.getItemCount()
                    val lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition()
                    if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        fetchContent(++curPage)
                        loading = true
                    }
                }
            })
    }

    private fun inflate(topRankers: MutableList<Rank>) {
        if (topRankers.size >=1) {
            layoutView.winner_name.text = topRankers[0].username
            layoutView.winner_score.text = topRankers[0].score
        }

        if (topRankers.size >= 2) {
            layoutView.runner_up_name.text = topRankers[1].username
            layoutView.runner_up_score.text = topRankers[1].score
        }

        if (topRankers.size >=3) {
            layoutView.third_name.text = topRankers[2].username
            layoutView.third_score.text = topRankers[2].score
        }
    }

    private fun fetchContent(page: Int) {
        val call = rankEndpoint.getRank(page)
        call.enqueue(object : Callback<ArrayList<Rank>> {
            override fun onResponse(call: Call<ArrayList<Rank>>, response: Response<ArrayList<Rank>>) {
                loading = false
                if(page == 1) {
                    var result = response.body()!!
                    inflate(result.subList(0, 3))
                    rankList.addAll(result.subList(3, result.size))
                } else {
                    rankList.addAll(response.body()!!)
                }
                rankAdapter.notifyItemInserted(rankList.size)
            }

            override fun onFailure(call: Call<ArrayList<Rank>>, t: Throwable) {
                //${t.message}
                if (t is IOException) {
                    Log.d("Orange_Prophet","network error: "+ t.message)
                    Toast.makeText(requireContext(), "network error"+t.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("Orange_Prophet","unexcepted error: "+ t.message)
                    Toast.makeText(requireContext(), "unexcepted error"+t.message, Toast.LENGTH_SHORT).show()
                }

                //Toast.makeText(requireContext(), "Error occurred while fetching quiz", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onRefresh() {
        curPage = 1
        rankList.clear()
        fetchContent(curPage)
    }
}