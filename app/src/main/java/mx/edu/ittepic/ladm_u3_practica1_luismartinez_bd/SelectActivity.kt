package mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select.*
import mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd.Utils.Utils

class SelectActivity : AppCompatActivity() {
    val SELECT_PHOTO = 2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        btnSelect.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"

            startActivityForResult(photoPicker, SELECT_PHOTO)
        }//btnSelect

        btnSave.setOnClickListener {
            val bitmap = (img_select.drawable as BitmapDrawable).bitmap
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Ingrese ID de actividad")
            val editText = EditText(this)
            alertDialog.setView((editText))
            alertDialog.setPositiveButton("Ok") { dialog, which ->
                var evidencia = Evidencias(editText.text.toString(), Utils.getBytes(bitmap))
                evidencia.asignarPuntero(this)
                var resultado = evidencia.insertarImagen()
                if (resultado == true) {
                    mensaje("SE GUARDÓ LA EVIDENCIA")
                } else {
                    when (evidencia.error) {
                        1 -> {
                            dialogo("error en tabla, no se creó o no se conectó a la base de datos")
                        }
                        2 -> {
                            dialogo("error no se pudo insertar en la tabla")
                        }
                    }
                }
                }//setPositive
                alertDialog.show()
            }//btnSave
        }//onCreate
        fun mensaje(s: String) {
            Toast.makeText(this, s, Toast.LENGTH_LONG).show()
        }//mensaje

        fun dialogo(s: String) {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Atención").setMessage(s)
                .setPositiveButton("Ok") { d, i -> }
                .show()
        }//dialogo


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data !== null) {
                val pickedImage = data.data
                img_select.setImageURI(pickedImage)
            }
        }
    }
