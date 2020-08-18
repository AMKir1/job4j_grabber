package ru.job4j.html;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartParse {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream("./src/main/resources/rabbit.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Store store = new PsqlStore(prop);
        Parse parse = new SqlRuParse();
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Grabber grab = new Grabber();
        try {
            grab.init(parse, store, scheduler);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
