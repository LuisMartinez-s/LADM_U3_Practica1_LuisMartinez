package mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd

import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_show.*
import mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd.Utils.Utils
import java.lang.Exception
import java.util.ArrayList

class ShowActivity : AppCompatActivity() {
    var id=0
    var listaID = ArrayList<String>()
internal lateinit var dbHelper:DBHelper //Declarar DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        var extra = intent.extras

        id=extra!!.getInt("id_actividad")
        editIdAct.setText(id.toString())
        editDescripcion.setText(extra.getString("descripcion"))
        fechaCaptura.setText(extra.getString("fechaCaptura"))
        fechaEntrega.setText(extra.getString("fechaEntrega"))
        cargarImagenes(editIdAct.text.toString())
        apagarEdits()

        dbHelper= DBHelper(this,"Escuela",null,1)
        btnMostrar.setOnClickListener {
          cargarImagenes(editIdAct.text.toString())
        }//btnMostrar

        btnEliminar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¿Desea eliminar la actividad?")
                .setMessage("Realizar esta acción eliminará la actividad con todas sus evidencias")
                .setPositiveButton("Eliminar"){d,i->
                    deleteActividad(editIdAct.text.toString())
                    finish()
                }
                .show()

        }//botonEliminar


    }//onCreate
    fun cargarImagenes(id: String) {

        listaID = ArrayList<String>()
        try {
            var baseDatos = DBHelper(this, "Escuela", null, 1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM EVIDENCIAS WHERE Id_actividad = ?"

            var parametros = arrayOf(id)
            var cursor = select.rawQuery(SQL, parametros)
            if(cursor.count<=0){
                var idEv= ArrayList<String>()
                var idAct= ArrayList<String>()
                var arreglo = ArrayList<Bitmap>()
                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val adapter =  AdapterLista(this, idEv.toArray(arrayid),
                    idAct.toArray(arrayidAct),
                    arreglo.toArray(array))
                listView.adapter = adapter
            }
            if (cursor.count > 0) {
                var bit: Bitmap? = null
                var arreglo = ArrayList<Bitmap>()
                this.listaID = ArrayList<String>()
                var idEv=ArrayList<String>()
                var idAct=ArrayList<String>()
                cursor.moveToFirst()
                var cantidad = cursor.count - 1
                (0..cantidad).forEach {
                    arreglo.add(Utils.getImage(buscarImagen(cursor.getString(0).toInt())!!))
                    listaID.add(cursor.getString(0  ))

                    idEv.add(cursor.getString(0))
                    idAct.add(cursor.getString(1))
                    cursor.moveToNext()

                }
                //CONVERTIR ARRAY LIST A ARRAY
                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val miAdaptador = AdapterLista(this, idEv.toArray(arrayid),idAct.toArray(arrayidAct),arreglo.toArray(array))

                listView.adapter = miAdaptador
               listView.setOnItemClickListener { parent, view, position, id ->
                    AlertDialog.Builder(this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar esta evidencia?" +"\nEvidencia: ${listaID[position]}")
                        .setPositiveButton("Eliminar") { d, i ->
                            deleteEvidenciaByID(listaID[position])
                            cargarImagenes(editIdAct.text.toString())
                        }

                        .setNegativeButton("Cancelar") { d, i -> }
                        .show()
                }


            } else {
                mensaje("No se han encontrado evidencias")
            }

            select.close()
            baseDatos.close()


        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }
    fun deleteEvidenciaByID(id: String) {
        try {
            var baseDatos = DBHelper(this, "Escuela", null, 1)

            var borrar = baseDatos.writableDatabase
            var SQL = "DELETE FROM EVIDENCIAS WHERE IDEVIDENCIA=?"
            var seleccion = arrayOf(id)
            borrar.execSQL(SQL, seleccion)
            mensaje("Se ha eliminado la evidencia")
            borrar.close()
            baseDatos.close()


        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }
    fun deleteActividad(id: String) {
        try {
            var baseDatos = DBHelper(this, "Escuela", null, 1)
            var eliminar = baseDatos.writableDatabase
            var SQLEvidencia = "DELETE FROM EVIDENCIAS WHERE ID_ACTIVIDAD=?"

            var parametros = arrayOf(id)
            eliminar.execSQL(SQLEvidencia, parametros)
            var SQLActividad = "DELETE FROM ACTIVIDADES WHERE ID_ACTIVIDAD=?"
            eliminar.execSQL(SQLActividad, parametros)
            mensaje("Se ha eliminado la actividad")
            eliminar.close()
            baseDatos.close()

        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }
    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun buscarImagen(id: Int): ByteArray? {
        var base = DBHelper(this, "Escuela", null, 1)
        var buscar = base.readableDatabase
        var columnas = arrayOf("FOTO") // * = todas las columnas
        var cursor = buscar.query(
            "EVIDENCIAS",
            columnas,
            "IDEVIDENCIA = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var result: ByteArray? = null
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getBlob(cursor.getColumnIndex("FOTO"))
            } while (cursor.moveToNext())
        }//if
        return result
    }

    fun apagarEdits(){
        editIdAct.isFocusable=false
        editDescripcion.isFocusable=false
        fechaEntrega.isFocusable=false
        fechaCaptura.isFocusable=false

    }//apagarEdits

}//class
