package mx.edu.ittepic.ladm_u3_practica1_luismartinez_bd

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Actividades(d: String, fc: String, fe: String) {
    var descripcion = d
    var fechaCaptura = fc
    var fechaEntrega = fe
    var id_actividad = 0
    var error = -1
    /*
        VALORES DE ERROR
        -----------------
        1 = Error en tabla, no se creó o no se conectó a Base de datos
        2 = No se pudo insertar
        3 = No se pudo realizar consulta / Tabla vacía
        4 = No se encontró ID
        5 = No actualizó
        6 = No borró

    */

    val nombreDBHelper = "Escuela"
    var puntero: Context? = null

    fun asignarPuntero(p: Context) {
        puntero = p
    }//asignar puntero

    fun insertar(): Boolean {
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("DESCRIPCION", descripcion)
            datos.put("FECHACAPTURA", fechaCaptura)
            datos.put("FECHAENTREGA", fechaEntrega)

            var respuesta = insertar.insert("ACTIVIDADES", "ID_ACTIVIDAD", datos)
            if (respuesta.toInt() == -1) {
                error = 2
                return false
            }
        } catch (e: SQLiteException) {
            error = 1
            return false
        }
        return true

    }//insertar

    fun mostrarTodos(): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarTodos

    fun mostrarPorDescripcion(des:String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "DESCRIPCION = ?",  arrayOf(des), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarPorDescripción

    fun mostrarPorFechaCaptura(fc: String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "FECHACAPTURA = ?",  arrayOf(fc), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarFECHACAPTURA

    fun mostrarPorFechaEntrega(fe: String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "FECHAENTREGA = ?",  arrayOf(fe), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarFECHAENTREGA

    fun mostrarPorIdAct(id:String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "ID_ACTIVIDAD = ?",  arrayOf(id), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarIdAct


    fun buscar(id: String): Actividades {
        var actividadEncontrada = Actividades("-1", "-1", "-1")
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var idBuscar = arrayOf(id)
            var cursor = select.query(
                "ACTIVIDADES",
                columnas,
                "ID_ACTIVIDAD = ?",
                idBuscar,
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                actividadEncontrada.id_actividad = id.toInt()
                actividadEncontrada.descripcion = cursor.getString(1)
                actividadEncontrada.fechaCaptura = cursor.getString(2)
                actividadEncontrada.fechaEntrega = cursor.getString(3)

            } else {
                error = 4
            }

        } catch (e: SQLiteException) {
            error = 1
        }
        return actividadEncontrada
    }

    fun eliminar(): Boolean {
        error = -1
        try {
            var base = DBHelper(puntero!!, nombreDBHelper, null, 1)
            var eliminar = base.writableDatabase
            var idEliminar = arrayOf(id_actividad.toString())

            var respuesta = eliminar.delete("ACTIVIDADES", "ID_ACTIVIDAD = ?", idEliminar)
            if (respuesta.toInt() == 0) {
                error = 6
                return false
            }
        } catch (e: SQLiteException) {
            error = 1
            return false
        }
        return true

    }//eliminar


}