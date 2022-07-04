package com.ru.tdm.movie.app.screen.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ru.tdm.movie.R
import com.ru.tdm.movie.app.screen.base.BaseFragment
import com.ru.tdm.movie.databinding.FragmentMovieBinding
import com.ru.tdm.movie.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_loading.*

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details){

    private lateinit var bindingMovieDetail: FragmentMovieDetailsBinding
    override val viewModel by viewModels<MovieDetailsViewModel>()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingMovieDetail = FragmentMovieDetailsBinding.bind(view)
        println(args.toString())
        //no more
    }
}
