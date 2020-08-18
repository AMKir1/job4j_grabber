package ru.job4j.html;

public class StartParse {
    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        Store store = grab.store();
        grab.init(new SqlRuParse(), store, grab.scheduler());
        grab.web(store);
    }
}
