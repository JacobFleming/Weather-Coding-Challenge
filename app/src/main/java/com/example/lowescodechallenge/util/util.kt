package com.example.lowescodechallenge.util

class util {
    companion object {
        fun Double.Companion.kelvinToFahrenheit(temp: Double): Int {

            val celsius = temp - 273.15
            val fahrenheit = (celsius * 9 / 5) + 32

            return fahrenheit.toInt()

        }
    }
}

