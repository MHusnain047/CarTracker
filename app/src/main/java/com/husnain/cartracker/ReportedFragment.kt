package com.husnain.cartracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.husnain.cartracker.databinding.FragmentReportedBinding
import kotlinx.coroutines.launch

class ReportedFragment : Fragment() {

    lateinit var adapter: CarAdapter
    val items=ArrayList<CarDetails>()
    lateinit var binding: FragmentReportedBinding
    lateinit var viewModel: ReportedFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentReportedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter= CarAdapter(items)
        binding.homerecycler.adapter=adapter
        binding.homerecycler.layoutManager= LinearLayoutManager(context)

        viewModel= ReportedFragmentViewModel()
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.data.collect {
                it?.let {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }


    }

}