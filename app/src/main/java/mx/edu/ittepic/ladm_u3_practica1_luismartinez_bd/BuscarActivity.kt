package mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_buscar.btnBuscar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class BuscarActivity : AppCompatActivity() {
    var listaID = ArrayList<String>()
    var posicion=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        cargarCombo()

        btnBuscar.setOnClickListener {

            when (posicion) {
                0->{mensaje("Por favor seleccione una opción 🙄😅")}
                1 -> { //mostrarTodo
                    cargarListaTodos()
                }//1
                2->{//IdAct
                    cargarListaIdAct()
                }
                3->{//Descripcion
                    cargarListaPorDescripcion()
                }//3
                4->{//fechaCaptura
                    cargarListaFechaCaptura()
                }//4
                5->{//fechaEntrega
                    cargarListaFechaEntrega()
                }//5
            }//when

        }//btnBuscar
        cargarListaTodos()


    }//onCreate

    fun cargarCombo() {
        // access the items of the list
        val seleccion = resources.getStringArray(R.array.seleccion)

        // access the spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, seleccion
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long) {
                   posicion=position
                    when (position) {
                        0->{
                        //    mensaje("Por favor seleccione una opción 🙄😅")
                        }
                        1 -> { //mostrarTodo
                            mensaje("Mostrar todo")
                        }//1
                        2->{//IdAct

                            mensaje("Buscar por Id de actividad")
                        }
                        3->{//Descripcion

                            mensaje("Buscar por descripción")
                        }//3
                        4->{//fechaCaptura

                            mensaje("Buscar por fecha de captura")
                        }//4
                        5->{//fechaEntrega

                            mensaje("Buscar por fecha de entrega")
                        }//5
                    }//when

                }//long

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }//OverrideonItem
            }//onItemSelected
        }//if
    }//cargarcombo

    fun cargarListaTodos() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodos()
            if (data.size == 0) {
                if (conexion.error == 3) {
                    var vector = Array<String>(data.size, { "" })
                    dialogo("No se encontraron resultados")
                    lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver") { d, i ->
                         cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    fun cargarListaIdAct() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarPorIdAct(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("No se encontraron resultados")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    fun cargarListaPorDescripcion() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarPorDescripcion(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("No se encontraron resultados")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    fun cargarListaFechaCaptura() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarPorFechaCaptura(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("No se encontraron resultados")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    fun cargarListaFechaEntrega() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarPorFechaEntrega(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("No se encontraron resultados")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    dialogo("Error, no se encontró ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("Cancelar") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun dialogo(s: String) {
        AlertDialog.Builder(this)
            .setTitle("Atención").setMessage(s)
            .setPositiveButton("Ok") { d, i -> }
            .show()
    }//dialogo

    private fun cargarEnOtroActivity(a: Actividades) {
        var intento = Intent(this, ShowActivity::class.java)
        intento.putExtra("descripcion", a.descripcion)
        intento.putExtra("fechaCaptura", a.fechaCaptura)
        intento.putExtra("fechaEntrega", a.fechaEntrega)
        intento.putExtra("id_actividad", a.id_actividad)
        startActivityForResult(intento, 0)


    }//cargarEnOtroActivity

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cargarListaTodos()
    }

}//class
