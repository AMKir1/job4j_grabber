package ru.job4j.html;

import java.util.Date;

public class Post {
    private String link;
    private String name;
    private Date date;

    Post( String link, String name, Date date){
        this.link = link;
        this.name = name;
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
