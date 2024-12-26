package com.example.prototype.Service;

import com.example.prototype.Entity.CronJob;
import com.example.prototype.Repository.CronJobRepository;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CronJobScheduler {
    @Autowired
    private CronJobRepository cronJobRepository;
    @Autowired
    private Scheduler scheduler;

    @Transactional
    public void scheduleCronJobs() throws ClassNotFoundException, SchedulerException {
        List<CronJob> cronJobs = cronJobRepository.findAllByEnabledTrue();

        for (CronJob cronJob : cronJobs) {
            JobDetail job = JobBuilder.newJob((Class<? extends Job>) Class.forName(cronJob.getJobClass()))
                    .withIdentity("job" + cronJob.getId())
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger" + cronJob.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronJob.getCronExpression()))
                    .build();

            if (scheduler.checkExists(job.getKey())){
                scheduler.deleteJob(job.getKey());
            }

            scheduler.scheduleJob(job, trigger);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void checkAndRunCronJobs() throws SchedulerException, ClassNotFoundException {
        scheduleCronJobs();
    }
}