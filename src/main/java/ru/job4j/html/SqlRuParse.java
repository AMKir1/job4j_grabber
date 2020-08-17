package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.*;

public class SqlRuParse implements Parse {

    private List<Post> posts;
    private int pages = 5;

    SqlRuParse () { }

    public static void main(String[] args) {
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

    public static String getDetails(String link) throws IOException {
        Element e = Jsoup.connect(link).get().select(".msgBody").get(1);
        StringBuilder sb = new StringBuilder();
        sb.append(e.text());
        sb.append(e.parent().parent().select(".msgFooter").text().substring(0, 16));
        return sb.toString();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            for (int i = 1; i < this.pages; i++) {
                Document doc = Jsoup.connect(String.format("%s/%d", link, i)).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    if (td.childNodeSize() < 6) { // пропускаем закрытые топики
                        list.add(new Post(td.child(0).attr("href"), td.child(0).text(), getDate(td.child(0).parent().parent().child(5).text()), getDetails(td.child(0).attr("href"))));
                    }
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return list;
    }

    @Override
    public Post detail(String link) {
        try {
            Document doc = Jsoup.connect(link).get();
            String name = doc.select(".messageHeader").first().text();
            return new Post(
                    link,
                    name.substring(0, name.length() - 6),
                    getDate(Jsoup.connect(link).get().select(".msgBody").get(1).parent().parent().select(".msgFooter").text().substring(0, 16)),
                    getDetails(link)
                    );
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }
}
