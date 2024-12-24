package com.example.snowboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboard.Adapters.NewsAdapter
import com.example.snowboard.Lists.NewsList
import com.example.snowboard.R
import com.example.snowboard.databinding.FragmentMainScreenBinding
import kotlinx.android.synthetic.main.fragment_main_screen.*

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsArrayList: ArrayList<NewsList>
    private lateinit var imageId: Array<Int>
    private lateinit var titleId: Array<String>
    private lateinit var commentId: Array<String>
    private lateinit var newsDateId: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToolbar()
        getArrayListNews()
        getRecyclerView()
        getUserData()
    }

    private fun showToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun getRecyclerView() {
        newsRecyclerView = recycler_view
        newsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsRecyclerView.setHasFixedSize(true)

        newsArrayList = arrayListOf<NewsList>()
    }

    private fun getArrayListNews() {
        imageId = arrayOf(
            R.drawable.resort
        )

        titleId = arrayOf(
            "Bansko"
        )

        commentId = arrayOf(
            "Opening the Ski Season"
        )

        newsDateId = arrayOf(
            "24.12.2024"
        )
    }

    private fun getUserData() {

        for (i in imageId.indices) {

            val listNews = NewsList(imageId[i], titleId[i], commentId[i], newsDateId[i])
            newsArrayList.add(listNews)
        }

        var adapter = NewsAdapter(newsArrayList)
        newsRecyclerView.adapter = adapter
    }
}