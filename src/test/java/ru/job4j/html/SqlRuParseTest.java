package ru.job4j.html;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SqlRuParseTest {
    SqlRuParse parseSQLRU = new SqlRuParse();
    HHRuParse parseHHRU = new HHRuParse();

    @Test
public void getPostsListSQL() {
        List<Post> posts = parseSQLRU.list("https://www.sql.ru/forum/job-offers/");
        assertThat(posts.size() > 100, is(true));
    }

    @Test
    public void getPostsListHH() {
        List<Post> posts = parseHHRU.list("https://hh.ru/search/vacancy?order_by=publication_time&clusters=true&area=1&text=Java&enable_snippets=true&search_period=7");
        assertThat(posts.size() > 49, is(true));
    }

    @Test
    public void getPostDetail() {
        Post post = parseSQLRU.detail("https://www.sql.ru/forum/1328171/network-traffic-reverser-udalyonnaya-vakansiya-ot-3000-do-5000");
        String result = "Network Traffic Reverser (удалённая вакансия от $3000 до $5000)";
        assertThat(post.getName(), is(result));
    }
}
