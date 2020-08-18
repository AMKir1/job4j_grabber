package ru.job4j.html;

import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber implements Grab {

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) {
        try {
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            data.put("parse", parse);
            JobDetail job = newJob(ParseToDB.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(86400)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class ParseToDB implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            Store store = (PsqlStore) context.getJobDetail().getJobDataMap().get("store");
            Parse parse = (SqlRuParse) context.getJobDetail().getJobDataMap().get("parse");
            parse.list("https://www.sql.ru/forum/job-offers/").forEach(store::save);
        }
    }

}
