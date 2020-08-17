package ru.job4j.html;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import ru.job4j.html.Parse;
import ru.job4j.html.Store;

public interface Grab {
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
