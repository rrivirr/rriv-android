package org.rriv.android.attachDevice

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.rriv.android.R

class AttachDeviceScreenFragment : Fragment() {

    companion object {
        fun newInstance() = AttachDeviceScreenFragment()
    }

    private lateinit var viewModel: AttachDeviceScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attach_device_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AttachDeviceScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}