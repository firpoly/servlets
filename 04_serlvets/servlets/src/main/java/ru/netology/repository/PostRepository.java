package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {
    private Map<Long, Post> hashmap = new HashMap<Long, Post>();
    public static volatile Long POST_COUNT = 1L;

    public List<Post> all() {
        List<Post> list = new ArrayList<Post>(hashmap.values());
        return list;
    }

    public Optional<Post> getById(long id) {
        if (hashmap.get(id) == null)
            return Optional.empty();
        return Optional.ofNullable(hashmap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            POST_COUNT++;
            while (hashmap.get(POST_COUNT) != null)
                POST_COUNT++;
            post.setId(POST_COUNT);
            hashmap.put(POST_COUNT, post);
            return hashmap.put(POST_COUNT, post);
        } else {
            var hashmapTemp = Optional.ofNullable(hashmap.get(post.getId()));
            if (hashmapTemp.isPresent())
                hashmap.put(post.getId(), post);
            else {
                POST_COUNT = post.getId();
                hashmap.put(post.getId(), post);
            }
        }
        return hashmap.get(post.getId());
    }

    public void removeById(long id) {
        var hashmapTemp = Optional.ofNullable(id);
        if (hashmapTemp.isPresent())
            hashmap.remove(id);
    }
}
