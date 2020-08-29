package ru.job4j.html;

import java.util.Arrays;

public class StartParse {
    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Store store = grab.store();
        grab.init(Arrays.asList(new SqlRuParse(), new HHRuParse()), store, grab.scheduler());
        grab.web(store);
    }
}
