package ru.job4j.html;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {

    private final Properties cfg = new Properties();

    public void cfg() throws IOException {
        try (InputStream in = new FileInputStream(new File("./src/main/resources/rabbit.properties"))) {
            cfg.load(in);
        }
    }

    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public Store store() {
        return new PsqlStore(cfg);
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) {
        try {
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            data.put("parse", parse);
            JobDetail job = newJob(GrabJob.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static class GrabJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            JobDataMap map = context.getJobDetail().getJobDataMap();
            Store store = (PsqlStore) map.get("store");
            Parse parse = (SqlRuParse) map.get("parse");
            parse.list("https://www.sql.ru/forum/job-offers/").forEach(store::save);
        }
    }
}
