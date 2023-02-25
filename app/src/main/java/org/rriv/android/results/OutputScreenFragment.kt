package org.rriv.android.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.rriv.android.R

class OutputScreenFragment : Fragment() {

    companion object {
        fun newInstance() = OutputScreenFragment()
    }

    private lateinit var viewModel: OutputScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_output_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OutputScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}