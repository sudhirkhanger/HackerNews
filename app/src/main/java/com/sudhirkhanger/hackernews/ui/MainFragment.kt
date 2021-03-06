package com.sudhirkhanger.hackernews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.hackernews.NewsComponent
import com.sudhirkhanger.hackernews.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val viewModel by activityViewModels<MainViewModel> {
        NewsComponent.provideMainViewModelFactory()
    }
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var rvItemSize = -1

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter { }

        layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        binding?.newsRv?.apply {
            setHasFixedSize(true)
            layoutManager = this@MainFragment.layoutManager
            addItemDecoration(dividerItemDecoration)
        }

        viewModel.news().observe(viewLifecycleOwner) {
            if (binding?.newsRv?.adapter == null) binding?.newsRv?.adapter = newsAdapter
            rvItemSize = it.size
            newsAdapter.submitList(it)
        }

        binding?.newsRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = binding?.newsRv?.layoutManager as LinearLayoutManager

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == rvItemSize - 1) {
                    viewModel.fetchNews()
                }
            }
        })
    }
}