package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 5; i++) {
            Document doc = Jsoup.connect(String.format("https://www.sql.ru/forum/job-offers/%d", i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                if (td.childNodeSize() < 6) { // пропускаем закрытые топики
                    System.out.println(td.child(0).attr("href"));
                    System.out.println(td.child(0).text());
                    getDate(td.child(0).parent().parent().child(5).text());
                    getDetails(td.child(0).attr("href"));
                }
            }
        }
    }

    public static void getDate(String dateString) {
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
        System.out.println(c.getTime());
    }

    public static void getDetails(String link) throws IOException {
        Element e = Jsoup.connect(link).get().select(".msgBody").get(1);
        System.out.println(e.text());
        System.out.println(e.parent().parent().select(".msgFooter").text().substring(0, 16));
    }
}
