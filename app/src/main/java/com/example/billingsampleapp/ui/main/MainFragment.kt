package com.example.billingsampleapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.billingsampleapp.R
import com.example.billingsampleapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private lateinit var viewModel: MainViewModel
    private lateinit var billingViewModel: BillingViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        billingViewModel = BillingViewModel(requireActivity())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.button.setOnClickListener {
            onClick(it)
        }
    }

    fun onClick(v: View?){
        billingViewModel.purchase(
            requireActivity(),
            BillingItem.NonConsumableItem
        )
        Toast.makeText(requireContext(),"Tapped",Toast.LENGTH_LONG).show()
    }

}
