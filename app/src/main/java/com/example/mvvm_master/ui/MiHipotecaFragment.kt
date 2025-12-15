package com.example.mvvm_master.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_master.databinding.FragmentMiHipotecaBinding
import com.example.mvvm_master.viewmodel.MiHipotecaViewModel


class MiHipotecaFragment : Fragment() {

    private var _binding: FragmentMiHipotecaBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MiHipotecaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MiHipotecaViewModel::class.java]

        binding.calcular.setOnClickListener {

            var error = false

            val capital = try {
                binding.capital.text.toString().toDouble()
            } catch (e: Exception) {
                binding.capital.error = "Introdueix un número"
                error = true
                0.0
            }

            val termini = try {
                binding.plazo.text.toString().toInt()
            } catch (e: Exception) {
                binding.plazo.error = "Introdueix un número"
                error = true
                0
            }

            if (!error) {
                viewModel.calcular(capital, termini)
            }
        }

        viewModel.quota.observe(viewLifecycleOwner) {
            binding.cuota.text = String.format("%.2f €", it)
        }

        viewModel.errorCapital.observe(viewLifecycleOwner) {
            binding.capital.error =
                it?.let { min -> "Capital mínim: $min €" }
        }

        viewModel.errorTermini.observe(viewLifecycleOwner) {
            binding.plazo.error =
                it?.let { min -> "Termini mínim: $min anys" }
        }

        viewModel.calculant.observe(viewLifecycleOwner) { calculant ->
            binding.calculando.visibility =
                if (calculant) View.VISIBLE else View.GONE
            binding.cuota.visibility =
                if (calculant) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}