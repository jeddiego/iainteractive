package mx.com.ia.cinemorelia.features.movies.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.databinding.ActivityDetailBinding
import mx.com.ia.cinemorelia.features.movies.viewmodel.MoviesViewModel
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.extra_id_movie
import mx.com.ia.cinemorelia.ui.CinemoreliaActivity
import org.koin.android.viewmodel.ext.android.viewModel


class MoviesDetailActivity: CinemoreliaActivity() {
    lateinit var bind: ActivityDetailBinding
    private val viewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val idMovie = intent.getLongExtra(extra_id_movie, 0)
        actionBarSetup()
        viewBinding()
        actions(idMovie)
    }

    private fun actionBarSetup() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bind.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun actions(idMovie: Long) {
        viewModel.action(MoviesViewModel.Actions.GetMovie(idMovie))
    }

    private fun viewBinding() {
        viewModel.getMovie().observe(this, {
            when (it) {
                is MoviesViewModel.StateActions.Loading -> {
                    bind.pbDetail.visibility = View.VISIBLE
                }
                is MoviesViewModel.StateActions.GetMovieResult -> {
                    val result = it.result
                    if(result.hasError) {
                        showAlert(getString(R.string.unexpected_error), result.error!!.errorMessage, false, false, cancelable =  true)
                    } else {
                        val movie = result.result!!
                        bind.tvGenre.text = movie.genre
                        bind.tvLength.text = movie.length
                        bind.tvRating.text = movie.rating
                        bind.tvSynopsis.text = movie.synopsis
                        supportActionBar?.title = movie.name

                        playMedia("${movie.trailerUrl}${movie.trailer}")
                    }
                    bind.pbDetail.visibility = View.GONE
                }
            }
        })
    }

    private fun playMedia(trailer: String) {
        bind.vvTrailer.setVideoURI(Uri.parse(trailer))
        bind.vvTrailer.requestFocus();
        bind.vvTrailer.start();
    }
}