package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(getDate(href.parent().parent().child(5).text()));
        }
    }

    public static Date getDate(String dateString) {
        Calendar c = new GregorianCalendar();
        String[] monthes = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
        String[] dateMass = dateString.replace(",", "").split(" ");
        if (dateMass[0].contains("вчера")) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
            c.set(Calendar.HOUR, Integer.parseInt(dateMass[1].split(":")[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(dateMass[1].split(":")[1]));
        } else if (dateMass[0].contains("сегодня")) {
            c.set(Calendar.HOUR, Integer.parseInt(dateMass[1].split(":")[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(dateMass[1].split(":")[1]));
        } else {
            for (int i = 0; i < monthes.length; i++) {
                if (monthes[i].equals(dateMass[1])) {
                    dateMass[1] = String.valueOf(i);
                }
            }
            c.set(
                    Integer.parseInt("20" + dateMass[2]),
                    Integer.parseInt(dateMass[1]),
                    Integer.parseInt(dateMass[0]),
                    Integer.parseInt(dateMass[3].split(":")[0]),
                    Integer.parseInt(dateMass[3].split(":")[1])
            );
        }
        return c.getTime();
    }
}
