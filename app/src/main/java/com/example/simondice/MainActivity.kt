package com.example.simondice


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var contadorRonda = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var juego = ArrayList<Int>()
        var jugador = ArrayList<Int>()
        var finalizado = false
        val jugar = findViewById<Button>(R.id.jugar)
        val comprobarSecuencia = findViewById<Button>(R.id.check)
        val azul = findViewById<Button>(R.id.azul)
        val rojo = findViewById<Button>(R.id.rojo)
        val amarillo = findViewById<Button>(R.id.amarillo)
        val verde = findViewById<Button>(R.id.verde)
        val listaBotones = listOf(rojo, verde, amarillo, azul)
        val toast = Toast.makeText(applicationContext, "GAME OVER", Toast.LENGTH_SHORT)
        val toast3 = Toast.makeText(applicationContext, "Repite la secuencia", Toast.LENGTH_SHORT)
        val bot: Button = findViewById(R.id.jugar)
        Log.d("Estado", "onCreate")

        jugar.setOnClickListener {
            finalizado = false
            reset(juego, jugador)
            añadirSecuencia(juego)
            ejecutarSecuencia(juego, listaBotones)
            toast3.show()
            mostrarRonda()
            bot.visibility = View.INVISIBLE
            Log.d("Estado", "Jugar")
        }

        comprobarSecuencia.setOnClickListener {

            contadorRonda++
            if (finalizado == false) {
                if (checkSecuencia(juego, jugador)) {
                    añadirSecuencia(juego)
                    jugador.clear()
                    ejecutarSecuencia(juego, listaBotones)
                    mostrarRonda()
                } else {
                    finalizado = true
                    toast.show()
                    contadorRonda = 0
                    val bot: Button = findViewById(R.id.jugar)
                    bot.visibility = View.VISIBLE
                }
            }
            Log.d("Estado", "ComprobarSecuencia")
        }

        rojo.setOnClickListener {
            añadirSecuenciaUsuario(jugador, 1)
        }
        verde.setOnClickListener {
            añadirSecuenciaUsuario(jugador, 2)
        }
        amarillo.setOnClickListener {
            añadirSecuenciaUsuario(jugador, 3)
        }
        azul.setOnClickListener {
            añadirSecuenciaUsuario(jugador, 4)
        }
        //showSec(sec)

    }

    fun añadirSecuencia(sec: MutableList<Int>) {
        val numb = (1..4).random()
        sec.add(numb)
    }

    fun mostrarRonda() {
        findViewById<TextView>(R.id.ronda).text = contadorRonda.toString()
    }

    fun checkSecuencia(sec: MutableList<Int>, secUsr: MutableList<Int>): Boolean {
        return sec == secUsr
    }

    fun reset(sec: MutableList<Int>, secUsr: MutableList<Int>) {
        sec.clear()
        secUsr.clear()
    }

    fun añadirSecuenciaUsuario(secUsr: MutableList<Int>, color: Int) {
        /*when (color+1) {
            1 -> secUsr.add(1)
            2 -> secUsr.add(2)
            3 -> secUsr.add(3)
            else -> secUsr.add(4)
        }*/
        secUsr.add(color)
    }

    fun ejecutarSecuencia(sec: MutableList<Int>, listaBotones: List<Button>) {
        /*var t = Toast.makeText(applicationContext, "Inicio", Toast.LENGTH_SHORT)
        for (color in sec) {
            when (color) {
                1 -> Toast.makeText(applicationContext, "Rojo", Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(applicationContext, "Verde", Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(applicationContext, "Amarillo", Toast.LENGTH_SHORT).show()
                4 -> Toast.makeText(applicationContext, "Azul", Toast.LENGTH_SHORT).show()
            }

        }*/
        Log.d("Estado", "Ejecutar secuencia")
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("Estado", "Ejecutar secuencia corrutina")
            for (color in sec) {
                delay(350)
                Log.d("Estado", "Cambiar a blanco")
                listaBotones[color-1].backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                delay(800)
                when (color) {
                    1 -> listaBotones[color-1].backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("red"))
                    2 -> listaBotones[color-1].backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("green"))
                    3 -> listaBotones[color-1].backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("yellow"))
                    4 -> listaBotones[color-1].backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("blue"))
                }
            }
            Log.d("Estado", "Repite secuencia")
        }
    }
}