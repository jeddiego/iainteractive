package mx.com.ia.cinemorelia.ui

import androidx.appcompat.app.AppCompatActivity
import mx.com.ia.cinemorelia.utils.IFeatureProvider
import org.koin.android.ext.android.inject

open class CinemoreliaActivity: AppCompatActivity() {
    val featuresProvider: IFeatureProvider by inject()

    fun alerta(
        title: String,
        message: String,
        finish: Boolean,
        flash: Boolean,
        alertKind: Int? = null,
        cancelable: Boolean? = null,
    ) {
        featuresProvider.showAlert(
            this,
            title,
            message,
            finish,
            flash,
            alertKind,
            cancelable
        )
    }

    fun showToast(message: String) {
        featuresProvider.showToast(message)
    }
}