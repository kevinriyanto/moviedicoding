package com.example.moviedicoding.database.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.moviedicoding.database.table.FavoriteTv.TvColumns.Companion.TABLE_NAME_TV
import com.example.moviedicoding.database.table.FavoriteTv.TvColumns.Companion.TV_ID
import com.example.moviedicoding.database.table.FavoriteTv.TvColumns.Companion._ID


class FavoriteTvHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME_TV
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: FavoriteTvHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): FavoriteTvHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoriteTvHelper(context)
                    }
                }
            }
            return INSTANCE as FavoriteTvHelper
        }
    }
    init {
        dataBaseHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }
    fun queryMovieById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$TV_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$TV_ID = '$id'", null)
    }
}