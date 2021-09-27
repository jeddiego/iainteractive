package mx.com.ia.cinemorelia.features.movies.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xwray.groupie.viewbinding.BindableItem
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.databinding.ItemMovieBinding
import mx.com.ia.cinemorelia.features.movies.models.MoviesModel
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.koin.core.KoinComponent
import org.koin.core.inject

class MoviesItemView(
    val movie: MoviesModel,
    private val context: Context
): BindableItem<ItemMovieBinding>() {

    override fun bind(bind: ItemMovieBinding, position: Int) {
        var media = ""
        movie.media.forEach { if(it.code == "poster") { media = it.resource } }
        val url = "${movie.posterUrl}$media"

        bind.tvMovie.text = movie.name
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.logo)
            .into(bind.ivPoster)
    }

    override fun getLayout(): Int = R.layout.item_movie

    override fun initializeViewBinding(view: View): ItemMovieBinding = ItemMovieBinding.bind(view)
}