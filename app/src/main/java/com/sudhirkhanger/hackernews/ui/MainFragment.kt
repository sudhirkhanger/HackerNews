package com.sudhirkhanger.hackernews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhirkhanger.hackernews.HackerNewsComponent
import com.sudhirkhanger.hackernews.databinding.FragmentMainBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding
    private val viewModel by activityViewModels<MainViewModel> {
        HackerNewsComponent.provideMainViewModelFactory()
    }
    private lateinit var newsAdapter: NewsAdapter

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter { }

        binding?.newsRv?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = newsAdapter
        }

        viewModel.getNews().observe(viewLifecycleOwner) {
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