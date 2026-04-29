package com.example.conversionpeso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var etValor: EditText
    lateinit var spOrigen: Spinner
    lateinit var spDestino: Spinner
    lateinit var btnConvertir: Button
    lateinit var tvResultado: TextView
    lateinit var tvPantalla: TextView

    val unidades = arrayOf("Kilogramos", "Libras", "Onzas", "Gramos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etValor = findViewById(R.id.etValor)
        spOrigen = findViewById(R.id.spOrigen)
        spDestino = findViewById(R.id.spDestino)
        btnConvertir = findViewById(R.id.btnConvertir)
        tvResultado = findViewById(R.id.tvResultado)
        tvPantalla = findViewById(R.id.tvPantalla)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unidades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spOrigen.adapter = adapter
        spDestino.adapter = adapter

        // BOTONES CALCULADORA
        val botones = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnPunto
        )

        for (id in botones) {
            findViewById<Button>(id).setOnClickListener {
                val texto = (it as Button).text.toString()
                if (tvPantalla.text == "0") {
                    tvPantalla.text = texto
                } else {
                    tvPantalla.append(texto)
                }
                etValor.setText(tvPantalla.text)
            }
        }

        btnConvertir.setOnClickListener {
            convertir()
        }
    }

    private fun convertir() {
        val valorTexto = etValor.text.toString()

        if (valorTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese un valor", Toast.LENGTH_SHORT).show()
            return
        }

        val valor = valorTexto.toDouble()
        val origen = spOrigen.selectedItem.toString()
        val destino = spDestino.selectedItem.toString()

        val resultado = convertirPeso(valor, origen, destino)

        tvResultado.text = formatearResultado(resultado, destino)
    }

    private fun convertirPeso(valor: Double, origen: String, destino: String): Double {

        val enGramos = when (origen) {
            "Kilogramos" -> valor * 1000
            "Libras" -> valor * 453.592
            "Onzas" -> valor * 28.3495
            "Gramos" -> valor
            else -> valor
        }

        return when (destino) {
            "Kilogramos" -> enGramos / 1000
            "Libras" -> enGramos / 453.592
            "Onzas" -> enGramos / 28.3495
            "Gramos" -> enGramos
            else -> enGramos
        }
    }

    private fun formatearResultado(valor: Double, unidad: String): String {
        return when (unidad) {
            "Kilogramos" -> DecimalFormat("#.####").format(valor) + " kg"
            "Libras" -> DecimalFormat("#.####").format(valor) + " lb"
            "Onzas" -> DecimalFormat("#.###").format(valor) + " oz"
            "Gramos" -> valor.toInt().toString() + " g"
            else -> valor.toString()
        }
    }
}