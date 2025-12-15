package com.example.mvvm_master.model



class SimuladorHipoteca {

    data class Solicitud(
        val capital: Double,
        val terminiAnys: Int
    )

    interface Callback {
        fun onIniciCalcul()
        fun onQuotaCalculada(quota: Double)
        fun onErrorCapitalMinim(capitalMinim: Double)
        fun onErrorTerminiMinim(terminiMinim: Int)
        fun onFiCalcul()
    }

    fun calcular(solicitud: Solicitud, callback: Callback) {

        callback.onIniciCalcul()

        // Simulem operació llarga (IO / servidor)
        Thread.sleep(2500)

        val interes = 0.01605
        val capitalMinim = 1000.0
        val terminiMinim = 2

        var error = false

        if (solicitud.capital < capitalMinim) {
            callback.onErrorCapitalMinim(capitalMinim)
            error = true
        }

        if (solicitud.terminiAnys < terminiMinim) {
            callback.onErrorTerminiMinim(terminiMinim)
            error = true
        }

        if (!error) {
            val quota =
                solicitud.capital * interes / 12 /
                        (1 - Math.pow(1 + interes / 12, -solicitud.terminiAnys * 12.0))

            callback.onQuotaCalculada(quota)
        }

        callback.onFiCalcul()
    }
}