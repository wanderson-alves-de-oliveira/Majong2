package com.example.majong.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Created by wanderson on 08/08/19.
 * @noinspection resource
 */
class BDTile(context: Context?) {
    private val db: SQLiteDatabase

    init {
        val aux = Conection(context)
        db = aux.writableDatabase
    }


    fun atualizar(dados: Base) {
        val valores = ContentValues()
        valores.put("nivel", dados.nivel.toString())
        valores.put("pontos", dados.pontos.toString())

        db.update("tile", valores, null, null)
    }


    fun buscar(): Base {
        val cursor = db.rawQuery(
            "SELECT  nivel,pontos" +
                    " FROM tile ", null
        )
        cursor.moveToNext()
        val p = Base(
            cursor.getString(0).toInt(),
            cursor.getString(1).toLong()
        )


        cursor.close()

        return p
    }
}