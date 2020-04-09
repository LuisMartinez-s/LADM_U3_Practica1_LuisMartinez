package mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //calendario
    var c = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
    var fecha = "${year}/${month + 1}/${day}"

    //fin de calendario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setear la fecha actual a fecha captura
        fechaCaptura.setText(fecha)
//---------------------------------------------BOTON FECHA DE CAPTURA----------------------------------------------------------------------
        fechaCaptura.setOnClickListener {
            //CALENDAR
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            //PICKER
            var datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    //setToTextView
                    var fecha = "${myear}/${mmonth + 1}/${mdayOfMonth}"
                    fechaCaptura.setText(fecha)
                },
                year,
                month,
                day
            )
            //showDialog
            datePicker.show()
        }//fechacaptura
        // ---------------------------------------------FIN BOTON FECHA DE CAPTURA----------------------------------------------------------------------

        //---------------------------------------------BOTON FECHA DE ENTREGA----------------------------------------------------------------------
        fechaEntrega.setOnClickListener {
            //CALENDAR
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var month = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            //PICKER
            var datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    //setToTextView
                    var fecha = "${myear}/${mmonth + 1}/${mdayOfMonth}"
                    fechaEntrega.setText(fecha)
                },
                year,
                month,
                day
            )
            //showDialog
            datePicker.show()
        }//fechaEntrega
        //---------------------------------------------FIN BOTON FECHA DE CAPTURA----------------------------------------------------------------------

        //-----------------------------------------------Boton Insertar Actividad----------------------------------------------------------------
        btnInsertarActividad.setOnClickListener {
            var actividades = Actividades(
                editDescripcion.text.toString(),
                fechaCaptura.text.toString(),
                fechaEntrega.text.toString()
            )
            actividades.asignarPuntero(this)
            var resultado = actividades.insertar()
            if (resultado == true) {
                mensaje("Actividad capturada")
                editDescripcion.setText("")
                fechaCaptura.setText("")
                fechaEntrega.setText("")

            } else {
                when (actividades.error) {
                    1 -> {
                        dialogo("Error en tabla, no se creó o no se conectó a Base de datos")
                    }
                    2 -> {
                        dialogo("Error, no se pudo insertar")
                    }
                }//when

            }//else

        }//btninsertarActividad


//-----------------------------------------------fin Boton Insertar Actividad----------------------------------------------------------------

 ///---------------------------------------------Boton buscar-----------------------------------------------------------------
        btnBuscar.setOnClickListener {
            startActivity(Intent(this, BuscarActivity::class.java))
        }//boton buscar

 ///---------------------------------------------fin Boton buscar-----------------------------------------------------------------

 //----------------------------------------------------Boton evidencia---------------------------------------------
        btnEvidencia.setOnClickListener {
            startActivity(Intent(this, SelectActivity::class.java))
        }//btnSelect

//----------------------------------------------------Fin Boton evidencia---------------------------------------------

    }//Oncreate


//------------------------------------------------------OTRAS FUNCIONES----------------------------------------------------------------
    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun dialogo(s: String) {
        AlertDialog.Builder(this)
            .setTitle("Atención").setMessage(s)
            .setPositiveButton("Ok") { d, i -> }
            .show()
    }//dialogo

}//class
