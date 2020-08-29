package ru.job4j.html;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;

public interface Grab {
    void init(List<Parse> parse, Store store, Scheduler scheduler) throws SchedulerException;
}
