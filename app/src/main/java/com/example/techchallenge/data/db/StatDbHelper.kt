package com.example.techchallenge.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.techchallenge.App
import org.jetbrains.anko.db.*

class StatDbHelper(ctx: Context = App.instance) : ManagedSQLiteOpenHelper(ctx,
    DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "statContext.db"
        const val DB_VERSION = 1
        val instance by lazy { StatDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(StatTable.NAME, true,
            StatTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            StatTable.VISIT_ID to INTEGER,
            StatTable.TIME to INTEGER,
            StatTable.LOCATION to TEXT,
            StatTable.CATEGORY to TEXT)

        db.createTable(VisitTable.NAME, true,
            VisitTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            VisitTable.START to INTEGER,
            VisitTable.DURATION to INTEGER,
            VisitTable.END to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(VisitTable.NAME, true)
        db.dropTable(StatTable.NAME, true)
        onCreate(db)
    }
}
