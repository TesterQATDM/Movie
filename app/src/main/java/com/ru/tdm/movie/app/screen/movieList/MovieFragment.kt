package com.ru.tdm.movie.app.screen.movieList

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ru.tdm.movie.R
import com.ru.tdm.movie.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie){

    private lateinit var bindingMovie: FragmentMovieBinding
    private lateinit var pagingAdapter: MovieListAdapter
    private val viewModel by viewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingMovie = FragmentMovieBinding.bind(view)

        pagingAdapter = MovieListAdapter(MovieListAdapter.UserComparator)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { movies ->
                Log.e("MyLog", movies.toString())
                pagingAdapter.submitData(movies)
            }
        }
        bindingMovie.rvMoveList.layoutManager = LinearLayoutManager(requireContext())
        bindingMovie.rvMoveList.adapter = pagingAdapter.withLoadStateFooter(
            footer = PlayersLoadingStateAdapter())

        pagingAdapter.addLoadStateListener { loadState ->

            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (pagingAdapter.snapshot().isEmpty()) {
                    bindingMovie.progress.isVisible = true
                }
                bindingMovie.errorTxt.isVisible = false

            } else {
                bindingMovie.progress.isVisible = false
                bindingMovie.swipeRefreshLayout.isRefreshing = false

                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (pagingAdapter.snapshot().isEmpty()) {
                        bindingMovie.errorTxt.isVisible = true
                        bindingMovie.errorTxt.text = it.error.localizedMessage
                    }

                }

            }
        }

    }
}
