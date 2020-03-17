package com.ngangavic.test.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.ngangavic.test.R

class JobSchedulerActivity : AppCompatActivity() {

    lateinit var radioGroupNetwork: RadioGroup
    lateinit var buttonScheduleJob: Button
    lateinit var buttonCancelJob: Button
    lateinit var jobScheduler: JobScheduler
    lateinit var switchIdle:Switch
    lateinit var switchCharging:Switch

    companion object {
        val JOB_ID: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)
        radioGroupNetwork = findViewById(R.id.radioGroupNetwork)
        buttonScheduleJob = findViewById(R.id.buttonScheduleJob)
        buttonCancelJob = findViewById(R.id.buttonCancelJob)
        switchCharging = findViewById(R.id.switchCharging)
        switchIdle = findViewById(R.id.switchIdle)

        buttonScheduleJob.setOnClickListener { scheduleJob() }

        buttonCancelJob.setOnClickListener { cancelJob() }

    }

    private fun cancelJob() {
        if (jobScheduler != null) {
            jobScheduler.cancelAll()
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scheduleJob() {
        val networkId = radioGroupNetwork.checkedRadioButtonId
        var networkOption = JobInfo.NETWORK_TYPE_NONE

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        when (networkId) {
            R.id.radioButtonNone -> {
                networkOption = JobInfo.NETWORK_TYPE_NONE
            }
            R.id.radioButtonAny -> {
                networkOption = JobInfo.NETWORK_TYPE_ANY
            }
            R.id.radioButtonWifi -> {
                networkOption = JobInfo.NETWORK_TYPE_UNMETERED
            }
        }

        val serviceName = ComponentName(packageName, NotificationJobService::class.java.name)
        val builder = JobInfo.Builder(JOB_ID, serviceName)
        //network
        builder.setRequiredNetworkType(networkOption)

        //charging
        builder.setRequiresDeviceIdle(switchIdle.isChecked)
        builder.setRequiresCharging(switchCharging.isChecked)

        val constraintSet: Boolean = networkOption != JobInfo.NETWORK_TYPE_NONE|| switchCharging.isChecked || switchIdle.isChecked
        if (constraintSet) {
            val jobInfo = builder.build()
            jobScheduler.schedule(jobInfo)
            Toast.makeText(this, "Job Scheduled, job will run when the constraints are met.",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please set at least one constraint.",Toast.LENGTH_SHORT).show()
        }


    }


}
