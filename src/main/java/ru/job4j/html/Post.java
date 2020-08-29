package ru.job4j.html;

import java.util.Date;

public class Post {
    private long id;
    private String link;
    private String name;
    private Date date;
    private String details;
    private Long type;

    Post(long id, String link, String name, Date date, String details, Long type){
        this.id = id;
        this.link = link;
        this.name = name;
        this.date = date;
        this.details = details;
        this.type = type;
    }

    Post(String link, String name, Date date, String details, Long type){
        this.link = link;
        this.name = name;
        this.date = date;
        this.details = details;
        this.type = type;
    }

    public long getId() {
        return id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", details='" + details + '\'' +
                ", type=" + type +
                '}';
    }
}
