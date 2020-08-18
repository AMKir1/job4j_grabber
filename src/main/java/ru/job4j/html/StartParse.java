package ru.job4j.html;

public class StartParse {
    public static void main(String[] args) throws Exception {
        Grabber grab = new Grabber();
        grab.cfg();
        grab.init(new SqlRuParse(), grab.store(), grab.scheduler());
    }
}
