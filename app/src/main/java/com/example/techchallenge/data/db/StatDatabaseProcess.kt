package com.example.techchallenge.data.db

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


class StatDatabaseProcess {

    private val elapsedTimeThreshold: Long = 20 * 1000

    fun begin(statInformation: StatInformation) {

        val visits = StatDb().getAllVisits()
        for(visit in visits) {
            Log.i("mkodb", "visit ${visit._id.toString()}: duration: " + visit.duration.toString())
        }

        val time = Date().time

        //check there are previous visits
        if(visits.isNotEmpty()) {
            val lastVisit = visits[0]

            //check if the last visit occurred on the previous day
            if(isLastVisitDuringCurrentDay(lastVisit.endTime)) {
                //check if duration is null, indicating the visit is still open
                if(lastVisit.duration == null) {
                    //check if the last stat time occurred less than a set amount of time in the past
                    if(lastVisit.endTime > time - elapsedTimeThreshold) {
                        //add the stat to this visit
                        addStat(lastVisit._id, time, statInformation)
                        return
                    } else {
                        closeVisit(lastVisit)
                    }
                }
            } else {
                closeVisit(lastVisit)
            }
        }

        //create new visit
        val insertedVisitId = addVisit(time, time)
        //add stat to the new visit
        addStat(insertedVisitId, time, statInformation)
    }

    private fun isLastVisitDuringCurrentDay(time: Long): Boolean {
        val current = LocalDateTime.now()
        val givenDateString = "${current.dayOfMonth} ${current.month} ${current.year} 00:00:00"
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.UK)
        val mDate = sdf.parse(givenDateString)
        val midnight = mDate?.time

        var isToday = true
        if (midnight != null) {
            if(midnight > time) {
                isToday = false
            }
        }
        return isToday
    }

    private fun addVisit(startTime: Long, endTime: Long, duration: Long? = null): Long {
        //create new visit
        val insertedVisitId = StatDb().insertVisit(Visit(startTime, duration, endTime))
        Log.i("mkodb", "inserted visit Id: $insertedVisitId, startTime: $startTime, duration: $duration, endTime: $endTime")
        return insertedVisitId
    }

    private fun addStat(visitId: Long, time: Long, statInformation: StatInformation) {
        val insertedId = StatDb().insertStat(Stat(visitId, time, statInformation.location.location, statInformation.category.category))
        Log.i("mkodb", "inserted stat Id: $insertedId, " +
                "visitId: $visitId, time: $time," +
                " location: ${statInformation.location.location} category: ${statInformation.category.category}")
        updateVisit(visitId, time)
    }

    private fun updateVisit(id: Long, endTime: Long) {
        //close previous visit by setting the duration to a value
        StatDb().updateVisitEndTime(id.toString(), endTime)
        Log.i("mkodb", "visit $id updated with new end: $endTime")
    }

    private fun closeVisit(visit: Visit) {
        //close previous visit by setting the duration to a value
        val duration = visit.endTime - visit.startTime
        StatDb().updateVisitDuration(visit._id.toString(), duration)
        Log.i("mkodb", "visit closed with Id: ${visit._id}, startTime: ${visit.startTime}, duration: $duration, endTime: ${visit.endTime}")
    }

    fun displayStats() {
        val visits = StatDb().getAllVisits()
        for (visit in visits) {
            val stats = StatDb().getStatsDuringVisit(visit._id)
            for (stat in stats) {
                Log.i(
                    "mkodb", "Stat Id: ${stat._id}, " +
                            "visitId: ${stat.visitId}, time: ${stat.time}," +
                            " location: ${stat.location} category: ${stat.category}"
                )
            }
        }
    }

    fun addDummyData() {
        val visits = mutableListOf<Visit>()
        val visitCount = 1
        for (x in 0 until visitCount) {
            val time = Date().time
            val startTime = time + (x * 10000)
            val endTime = startTime + 5000
            var duration: Long? = endTime - startTime
            if(x == visitCount - 1) duration = null

            visits.add(Visit(startTime, duration, endTime))
        }
        StatDb().insertVisits(visits)
    }
}