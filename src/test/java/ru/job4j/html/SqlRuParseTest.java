package ru.job4j.html;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SqlRuParseTest {
    SqlRuParse parse = new SqlRuParse();

    @Test
    public void getPostsList() {
        List<Post> posts = parse.list("https://www.sql.ru/forum/job-offers/");
        assertThat(posts.size() > 100, is(true));
    }

    @Test
    public void getPostDetail() {
        Post post = parse.detail("https://www.sql.ru/forum/1328171/network-traffic-reverser-udalyonnaya-vakansiya-ot-3000-do-5000");
        String result = "Network Traffic Reverser (удалённая вакансия от $3000 до $5000)";
        assertThat(post.getName(), is(result));
    }
}
