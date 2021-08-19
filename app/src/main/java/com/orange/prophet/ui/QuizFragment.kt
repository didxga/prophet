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
import com.orange.prophet.ui.adapter.QuizAdapter
import com.orange.prophet.ui.api.QuizEndpoint
import com.orange.prophet.ui.model.Quiz
import kotlinx.android.synthetic.main.fragment_quiz.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

import android.util.Log

class QuizFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val ENDPOINT_URL = BuildConfig.SERVER_URL
    private lateinit var quizEndpoint: QuizEndpoint
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var quizList: ArrayList<Quiz>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var loading = false
    private var visibleThreshold = 2
    private var curPage = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val retrofit: Retrofit = makeRetrofit()
        quizEndpoint = retrofit.create(QuizEndpoint::class.java)
        quizList = ArrayList<Quiz>()
        quizAdapter = QuizAdapter(quizList)

        swipe_refresh.setOnRefreshListener(this)
        swipe_refresh.setColorSchemeResources(R.color.colorAccent)

        linearLayoutManager = LinearLayoutManager(requireContext())
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = quizAdapter
        }

        hookScrollListenerForRecycleView()
        fetchContent(curPage)
    }

    private fun hookScrollListenerForRecycleView() {
        recycler_view
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

    private fun fetchContent(page: Int) {
        val call = quizEndpoint.getQuiz(page)
        call.enqueue(object : Callback<ArrayList<Quiz>> {
            override fun onResponse(call: Call<ArrayList<Quiz>>, response: Response<ArrayList<Quiz>>) {
                quizList.addAll(response.body()!!)
                quizAdapter.notifyItemInserted(quizList.size)
                loading = false
                swipe_refresh.isRefreshing = false
            }

            override fun onFailure(call: Call<ArrayList<Quiz>>, t: Throwable) {
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

    override fun onRefresh() {
        curPage = 1
        quizList.clear()
        fetchContent(curPage)
    }

    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}