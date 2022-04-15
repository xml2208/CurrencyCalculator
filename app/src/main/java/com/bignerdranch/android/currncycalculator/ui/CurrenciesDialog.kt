package com.bignerdranch.android.currncycalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.currncycalculator.databinding.DialogFragmentBinding
import com.bignerdranch.android.currncycalculator.databinding.ItemViewBinding
import com.bignerdranch.android.currncycalculator.model.CurrencyViewModel
import com.bignerdranch.android.currncycalculator.model.ViewModelFactory

class CurrenciesDialog : DialogFragment() {
    private lateinit var binding: DialogFragmentBinding
    private val viewModel: CurrencyViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rView.layoutManager = LinearLayoutManager(requireContext())
    }

    private class CurrencyAdapter() : RecyclerView.Adapter<CurrencyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
            val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CurrencyViewHolder(view)
        }

        override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {

        }

        override fun getItemCount(): Int = 0

    }

    class CurrencyViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }


}

