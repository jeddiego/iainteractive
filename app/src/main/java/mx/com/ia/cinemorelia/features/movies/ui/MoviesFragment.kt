package mx.com.ia.cinemorelia.features.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import mx.com.ia.cinemorelia.databinding.FragmentMoviesBinding
import mx.com.ia.cinemorelia.features.movies.viewmodel.MoviesViewModel
import mx.com.ia.cinemorelia.ui.CinemoreliaFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : CinemoreliaFragment() {

    private val viewModel: MoviesViewModel by viewModel()
    private var _bind: FragmentMoviesBinding? = null
    private val binding get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentMoviesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        viewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}