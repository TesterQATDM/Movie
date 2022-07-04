package com.ru.tdm.movie.app.screen.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ru.tdm.movie.R
import com.ru.tdm.movie.app.screen.base.BaseFragment
import com.ru.tdm.movie.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.layout_try_again.*


@AndroidEntryPoint
class MovieFragment : BaseFragment(R.layout.fragment_movie){

    private lateinit var bindingMovie: FragmentMovieBinding
    private lateinit var adapter: MovieListAdapter
    override val viewModel by viewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingMovie = FragmentMovieBinding.bind(view)
        layoutTryAging.visibility = View.GONE
        viewModel.loadCity()
        observeState()
        viewModel.movieViewModelFromRetrofit.observe(viewLifecycleOwner) {
            adapter.movies = it.results
        }

        adapter = MovieListAdapter()
        bindingMovie.rvMoveList.layoutManager = LinearLayoutManager(requireContext())
        bindingMovie.rvMoveList.adapter = adapter
        btmTryAging.setOnClickListener {
            layoutTryAging.visibility = View.GONE
            viewModel.loadCity()
        }
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        if (it.showProgress){
            loading.visibility = View.VISIBLE
        }
        else{
            loading.visibility = View.GONE
        }
        if (it.showError)
            layoutTryAging.visibility = View.VISIBLE
    }
}
