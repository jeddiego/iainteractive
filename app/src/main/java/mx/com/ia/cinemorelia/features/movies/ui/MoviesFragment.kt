package mx.com.ia.cinemorelia.features.movies.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.databinding.FragmentMoviesBinding
import mx.com.ia.cinemorelia.datasource.entities.MoviesEntity
import mx.com.ia.cinemorelia.features.movies.viewmodel.MoviesViewModel
import mx.com.ia.cinemorelia.features.movies.views.MoviesItemView
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.extra_id_movie
import mx.com.ia.cinemorelia.ui.CinemoreliaFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : CinemoreliaFragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _bind: FragmentMoviesBinding? = null
    private val bind get() = _bind!!
    private val moviesAdapter = GroupAdapter<GroupieViewHolder>().apply {
        setOnItemClickListener { item, _ ->
            if(item is MoviesItemView) {
                val movie = item.movie
                val intent = Intent(requireActivity(), MoviesDetailActivity::class.java).apply {
                    putExtra(extra_id_movie, movie.id)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = bind.root

        viewBinding()
        actions()
        initRecyclerView()
        return root
    }

    private fun initRecyclerView() {
        bind.rvMovies.setHasFixedSize(true)
        bind.rvMovies.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = moviesAdapter
        }
    }

    private fun populateMovies(movies: List<MoviesEntity>) {
        moviesAdapter.apply {
            clear()
            movies.forEach {
                add(MoviesItemView(it, requireActivity()))
            }
        }
    }

    private fun actions() {
        viewModel.action(MoviesViewModel.Actions.GetMovies)
    }

    private fun viewBinding() {
        viewModel.getMovies().observe(viewLifecycleOwner, ::responses)
    }

    private fun responses(states: MoviesViewModel.StateActions) {
        when(states) {
            is MoviesViewModel.StateActions.Loading -> {
                bind.pbMovies.visibility = View.VISIBLE
            }
            is MoviesViewModel.StateActions.GetMoviesResult -> {
                val response = states.result
                if(response.hasError) {
                    showAlert(getString(R.string.unexpected_error), response.error!!.errorMessage, false, false, cancelable =  true)
                } else {
                    populateMovies(response.result!!)
                }
                bind.pbMovies.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}