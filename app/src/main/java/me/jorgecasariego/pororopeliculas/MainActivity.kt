package me.jorgecasariego.pororopeliculas

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Leer licencia si existe y mostrar en el EditText
        if (fileList().contains("licencia.txt")) {
            try {
                val file = InputStreamReader(openFileInput("licencia.txt"))
                val br = BufferedReader(file)
                var linea = br.readLine()
                val textoLicencia = StringBuilder()
                while (linea != null) {
                    textoLicencia.append(linea + "\n")
                    linea = br.readLine()
                }
                br.close()
                file.close()
                licencia.setText(textoLicencia)
            } catch (e: IOException) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            }

        }

        // 2. Escribir nueva licencia o actualizar si ya existe
        escribir.setOnClickListener {
            try {
                val file = OutputStreamWriter(
                        openFileOutput("licencia.txt", Activity.MODE_PRIVATE)
                )

                file.write(licencia.text.toString())
                file.flush()
                file.close()

                Toast.makeText(this, "Licencia guardada exitosamente", Toast.LENGTH_LONG).show()
            } catch (e: IOException) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            }

        }
    }
}










