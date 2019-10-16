package com.example.techchallenge.data.db


import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

class StatDb(private val statDbHelper: StatDbHelper = StatDbHelper.instance,
             private val dataMapper: DbDataMapper = DbDataMapper()) {

    fun getAllVisits() = statDbHelper.use {

        select(VisitTable.NAME).orderBy(VisitTable.ID,SqlOrderDirection.DESC).parseList {
                Visit(HashMap(it))
        }
    }

    fun insertStat(stat: Stat): Long = statDbHelper.use {
        insert(StatTable.NAME, *stat.map.toVarargArray())
    }

    fun insertVisit(visit: Visit) = statDbHelper.use {
        insert(VisitTable.NAME,
            VisitTable.START to visit.startTime,
            VisitTable.DURATION to visit.duration,
            VisitTable.END to visit.endTime
        )
    }

    fun updateVisitDuration(id: String, duration: Long?) = statDbHelper.use {
        update(VisitTable.NAME, VisitTable.DURATION to duration)
            .whereSimple("_id = ?", id)
            .exec()
    }

    fun updateVisitEndTime(id: String, endTime: Long) = statDbHelper.use {
        update(VisitTable.NAME, VisitTable.END to endTime)
            .whereSimple("_id = ?", id)
            .exec()
    }

    fun insertVisits(visits: List<Visit>) = statDbHelper.use {

        clear(VisitTable.NAME)
        clear(StatTable.NAME)

        for(visit in visits) insertVisit(visit)
    }

    fun getStatsDuringVisit(id: Long): List<Stat> = statDbHelper.use {
        select(StatTable.NAME)
            .whereSimple("${StatTable.VISIT_ID} = ?", id.toString())
            .orderBy(StatTable.TIME,SqlOrderDirection.ASC)
            .parseList {
            Stat(HashMap(it))
        }
    }
}
