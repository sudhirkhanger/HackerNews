package com.sudhirkhanger.hackernews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.hackernews.NewsComponent
import com.sudhirkhanger.hackernews.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val viewModel by activityViewModels<MainViewModel> {
        NewsComponent.provideMainViewModelFactory()
    }
    private lateinit var newsAdapter: NewsAdapter

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

        binding?.newsRv?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        viewModel.getNews().observe(viewLifecycleOwner) {
            if (binding?.newsRv?.adapter == null) binding?.newsRv?.adapter = newsAdapter
            newsAdapter.submitList(it)
        }

        binding?.newsRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = binding?.newsRv?.layoutManager as LinearLayoutManager

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                    viewModel.getNews().value?.size?.minus(1) ?: -1
                ) {
                    viewModel.fetchNews()
                }
            }
        })
    }
}