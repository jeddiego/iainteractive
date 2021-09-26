package mx.com.ia.cinemorelia.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.policy.CinemoreliaPolicy.Companion.alert_kind_confirmation

class FeatureProvider(
    private val context: Context
): IFeatureProvider {
    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showAlert(
        context: Context,
        title: String,
        message: String,
        finish: Boolean,
        flash: Boolean,
        alertKind: Int?,
        cancelable: Boolean?,
    ) {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.alert_cinemorelia, null)
        val ivIcono = v.findViewById<ImageView>(R.id.ivIcono)
        val tvTitulo = v.findViewById<TextView>(R.id.tvTitulo)
        val tvMensaje = v.findViewById<TextView>(R.id.tvMensaje)
        val btAceptar = v.findViewById<TextView>(R.id.btAceptar)
        val builder: AlertDialog = AlertDialog.Builder(context).create()

        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.setView(v)
        builder.setCancelable( cancelable == true )

        when (alertKind) {
            alert_kind_confirmation -> {
                ivIcono.setImageResource(R.drawable.ic_baseline_check_circle_72)
            }
            else -> {
                ivIcono.setImageResource(R.drawable.ic_baseline_info_72)
            }
        }

        tvTitulo.text = title
        tvMensaje.text = message
        btAceptar.setOnClickListener {
            builder.dismiss()
            if(finish) {
                (context as AppCompatActivity).finish()
            }
        }

        if(flash) {
            btAceptar.visibility = View.GONE
            val timer = object: CountDownTimer(4000, 1000) {
                override fun onTick(millisUntilFinished: Long) { }
                override fun onFinish() {
                    builder.dismiss()
                    if(finish) {
                        (context as AppCompatActivity).finish()
                    }
                }
            }
            timer.start()
        }

        builder.show()
    }

    override fun getAppContext(): Context = context
}

interface IFeatureProvider {
    fun showToast(message: String)
    fun showAlert(
        context: Context,
        title: String,
        message: String,
        finish: Boolean,
        flash: Boolean,
        alertKind: Int? = null,
        cancelable: Boolean? = null)
/*    fun showFlashWithAction(
        context: Context,
        title: String,
        message: String,
        finish: Boolean,
        viewModel: Any,
        action: Any,
        alertKind: Int? = null
    )*/
    fun getAppContext(): Context
}