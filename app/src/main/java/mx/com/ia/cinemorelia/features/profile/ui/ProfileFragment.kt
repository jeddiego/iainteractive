package mx.com.ia.cinemorelia.features.profile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.com.ia.cinemorelia.R
import mx.com.ia.cinemorelia.databinding.FragmentProfileBinding
import mx.com.ia.cinemorelia.features.profile.models.ProfileBodyModel
import mx.com.ia.cinemorelia.features.profile.viewmodel.ProfileViewModel
import mx.com.ia.cinemorelia.ui.CinemoreliaFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment: CinemoreliaFragment() {

    private val viewModel: ProfileViewModel by viewModel()
    private var _bind: FragmentProfileBinding? = null
    private val bind get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = bind.root

        actions()
        viewBinding()
        return root
    }

    private fun actions() {
        viewModel.action(ProfileViewModel.Actions.GetUserData)
    }

    private fun viewBinding() {
        viewModel.getUserData().observe(viewLifecycleOwner, ::responses)
    }

    private fun responses(states: ProfileViewModel.StateActions) {
        when(states) {
            is ProfileViewModel.StateActions.Loading -> {
                bind.pbProfile.visibility = View.VISIBLE
            }
            is ProfileViewModel.StateActions.GetUserDataResult -> {
                val response = states.result
                if(response.hasError) {
                    showAlert("Error inesperado", response.error!!.errorMessage, false, false, cancelable =  true)
                } else {
                    val user = response.result!!
                    bind.tvName.text = getString(R.string.full_name, user.firstName, user.lastName)
                    bind.tvEmail.text = user.email
                    bind.tvClub.text = user.card.toString()
                }
                bind.pbProfile.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }
}