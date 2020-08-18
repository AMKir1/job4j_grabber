package ru.job4j.html;

import java.sql.SQLException;
import java.util.List;

public class MemStore implements Store {

    private List<Post> posts;

    public MemStore(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public void save(Post post) {
        if(post != null) {
            this.posts.add(post);
        } else {
            System.out.println("Пост не существует");
        }
    }

    @Override
    public List<Post> getAll() {
        return this.posts;
    }

    @Override
    public Post findById(String id) {
        for (Post p : this.posts) {
            if(p.getId() == Integer.parseInt(id)) {
                return p;
            }
        }
        return null;
    }
}
