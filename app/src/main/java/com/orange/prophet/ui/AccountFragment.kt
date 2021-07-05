package com.orange.prophet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.orange.prophet.BuildConfig
import com.orange.prophet.R
import com.orange.prophet.ui.adapter.QuizAdapter
import com.orange.prophet.ui.api.QuizEndpoint
import com.orange.prophet.ui.model.Quiz
import kotlinx.android.synthetic.main.fragment_quiz.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AccountFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val ENDPOINT_URL = BuildConfig.SERVER_URL
    private lateinit var quizEndpoint: QuizEndpoint
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var quizList: ArrayList<Quiz>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mMyQuizListButton: Button
    private lateinit var mChangePasswordButton: Button
    private lateinit var mLogoutButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val rootView: View = inflater.inflate(R.layout.account_fragment, container, false)

        mMyQuizListButton = rootView.findViewById(R.id.myquizlist_button) as Button
        mChangePasswordButton = rootView.findViewById(R.id.changepassword_button) as Button
        mLogoutButton = rootView.findViewById(R.id.logout_button) as Button

        mMyQuizListButton.setOnClickListener(mButtonListener)

        return rootView
    }

    private var mButtonListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.myquizlist_button -> {
                //TODO: show my quiz list

            }
            R.id.changepassword_button -> {
                //TODO: show change password screen
            }
            R.id.logout_button -> {
                //TODO: send logout cmd to server

            }

        }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val retrofit: Retrofit = makeRetrofit()
//        quizEndpoint = retrofit.create(QuizEndpoint::class.java)
//        quizList = ArrayList<Quiz>()
//        quizAdapter = QuizAdapter(quizList)
//
//        swipe_refresh.setOnRefreshListener(this)
//        swipe_refresh.setColorSchemeResources(R.color.colorAccent)
//
//        linearLayoutManager = LinearLayoutManager(requireContext())
//        recycler_view.apply {
//            setHasFixedSize(true)
//            layoutManager = linearLayoutManager
//            itemAnimator = DefaultItemAnimator()
//            adapter = quizAdapter
//        }
//
//        hookScrollListenerForRecycleView()
//        fetchContent(curPage)
//    }


    private fun makeRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}