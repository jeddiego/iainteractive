package mx.com.ia.cinemorelia.ui

import androidx.fragment.app.Fragment
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.koin.android.ext.android.inject

open class CinemoreliaFragment: Fragment() {
    val featuresProvider: IFeatureProvider by inject()
    fun showAlert(
        title: String,
        message: String,
        finish: Boolean,
        flash: Boolean,
        alertKind: Int? = null,
        cancelable: Boolean? = null,
    ) {
        featuresProvider.showAlert(
            requireActivity(),
            title,
            message,
            finish,
            flash,
            alertKind,
            cancelable
        )
    }
}