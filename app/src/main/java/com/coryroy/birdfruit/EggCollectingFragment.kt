package com.coryroy.birdfruit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coryroy.birdfruit.adapters.EggCollectionAdapter
import com.coryroy.birdfruit.databinding.FragmentEggCollectingBinding
import com.coryroy.birdfruit.viewmodels.EggCollectionViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EggCollectingFragment : Fragment() {

    private var _binding: FragmentEggCollectingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEggCollectingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(EggCollectionViewModel::class.java)

        val recyclerView = binding.eggCollectionList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = EggCollectionAdapter(viewModel, viewLifecycleOwner)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}