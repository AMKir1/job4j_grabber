package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class HHRuParse implements Parse {

    private static final int vacancies = 100;

    HHRuParse() {
    }

    public static Date getDate(String dateString) {
        Calendar c = new GregorianCalendar();
        String[] monthes = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        String[] dateMass = dateString.replace(",", "").split(" ");
        if (dateMass[0].contains("сегодня")) {
            c.set(Calendar.HOUR, Integer.parseInt(dateMass[1].split(":")[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(dateMass[1].split(":")[1]));
        } else {
            for (int i = 0; i < monthes.length; i++) {
                if (monthes[i].equals(dateMass[1])) {
                    dateMass[1] = String.valueOf(i);
                }
            }
            c.set(
                    Integer.parseInt("2020"),
                    Integer.parseInt(dateMass[1]),
                    Integer.parseInt(dateMass[0])
            );
        }
        return c.getTime();
    }

    public static String getDetails(String link) throws IOException {
        Element e = Jsoup.connect(link).get().select(".msgBody").get(1);
        return e.text() + e.parent().parent().select(".msgFooter").text().substring(0, 16);
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(String.format("%s", link)).get();
            Elements row = doc.select(".vacancy-serp-item");
            for (Element elem : row) {
                list.add(new Post(
                        elem.select(".resume-search-item__name").select(".bloko-link").attr("href"),
                        elem.select(".resume-search-item__name").text(),
                        getDate(elem.select(".vacancy-serp-item__publication-date").text()),
                        elem.select(".vacancy-serp-item__row").select(".vacancy-serp-item__info").eq(1).text(),
                        1L));
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return list;
    }

    @Override
    public Post detail(String link) {
        return null;
    }
}
