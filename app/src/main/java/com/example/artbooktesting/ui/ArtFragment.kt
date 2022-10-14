package com.example.artbooktesting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbooktesting.R
import com.example.artbooktesting.databinding.FragmentArtsBinding
import com.example.artbooktesting.ui.adapter.ArtRecyclerAdapter
import com.example.artbooktesting.ui.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val adapter: ArtRecyclerAdapter
):Fragment(R.layout.fragment_arts) {

    private var fragmentBinding:FragmentArtsBinding? = null
    lateinit var artViewModel:ArtViewModel


    private val swipeCallback = object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = adapter.arts[layoutPosition]
            artViewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artViewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeObserver()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recycler)

        binding.fav.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }


    private fun subscribeObserver(){
        artViewModel.artList.observe(viewLifecycleOwner, Observer {
            adapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}