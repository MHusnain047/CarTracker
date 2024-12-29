 package com.husnain.cartracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.husnain.cartracker.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

 class HomeFragment : Fragment() {

     lateinit var adapter: CarAdapter
     val items=ArrayList<CarDetails>()
     lateinit var binding: FragmentHomeBinding
     lateinit var viewModel: HomeFragmentViewModel

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         binding=FragmentHomeBinding.inflate(inflater,container,false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         adapter= CarAdapter(items)
         binding.homerecycler.adapter=adapter
         binding.homerecycler.layoutManager= LinearLayoutManager(context)


         viewModel= HomeFragmentViewModel()
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

         binding.floatingActionButton.setOnClickListener {
             startActivity(Intent(context, ReportCarActivity::class.java))
         }
     }

 }