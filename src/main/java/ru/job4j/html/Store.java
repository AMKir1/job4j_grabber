package ru.job4j.html;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    void save(Post post);

    List<Post> getAll();
}
