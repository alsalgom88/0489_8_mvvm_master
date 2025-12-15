package com.example.mvvm_master.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_master.model.SimuladorHipoteca
import java.util.concurrent.Executors

class MiHipotecaViewModel(application: Application) : AndroidViewModel(application) {

    private val executor = Executors.newSingleThreadExecutor()
    private val simulador = SimuladorHipoteca()

    val quota = MutableLiveData<Double>()
    val errorCapital = MutableLiveData<Double?>()
    val errorTermini = MutableLiveData<Int?>()
    val calculant = MutableLiveData<Boolean>()

    fun calcular(capital: Double, termini: Int) {

        val solicitud = SimuladorHipoteca.Solicitud(capital, termini)

        executor.execute {
            simulador.calcular(solicitud, object : SimuladorHipoteca.Callback {

                override fun onIniciCalcul() {
                    calculant.postValue(true)
                }

                override fun onQuotaCalculada(quotaResultat: Double) {
                    errorCapital.postValue(null)
                    errorTermini.postValue(null)
                    quota.postValue(quotaResultat)
                }

                override fun onErrorCapitalMinim(capitalMinim: Double) {
                    errorCapital.postValue(capitalMinim)
                }

                override fun onErrorTerminiMinim(terminiMinim: Int) {
                    errorTermini.postValue(terminiMinim)
                }

                override fun onFiCalcul() {
                    calculant.postValue(false)
                }
            })
        }
    }
}