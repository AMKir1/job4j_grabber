package ru.job4j.html;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class PsqlStore implements Store, AutoCloseable {

    private static final String SQL_ADD_POST = "INSERT INTO post(name, text, link, created) VALUES (?, ?, ?, ?)";
    private static final String SQL_GET_ALL_POSTS = "SELECT id, name, text, link, created FROM post";
    private static final String SQL_GET_POST_BY_ID = "SELECT id, name, text, link, created FROM post WHERE id = ?";

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps =  ConnectionRollback.create(this.cnn).prepareStatement(SQL_ADD_POST, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDetails());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, new java.sql.Timestamp(post.getDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement ps = ConnectionRollback.create(this.cnn).prepareStatement(SQL_GET_ALL_POSTS)) {
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   posts.add(new Post(
                           rs.getLong("id"),
                           rs.getString("link"),
                           rs.getString("name"),
                           rs.getDate("created"),
                           rs.getString("text")
                           ));
               }
           }
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post post = null;
        try (PreparedStatement ps = ConnectionRollback.create(this.cnn).prepareStatement(SQL_GET_POST_BY_ID)) {
            ps.setLong(1, Long.parseLong(id));
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                   post = new Post(
                            rs.getLong("id"),
                            rs.getString("link"),
                            rs.getString("name"),
                            rs.getDate("created"),
                            rs.getString("text")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
