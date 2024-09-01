package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> hashmap = new ConcurrentHashMap<Long, Post>();
    public static volatile AtomicLong POST_COUNT = new AtomicLong(1);

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
            POST_COUNT.getAndIncrement();
            while (hashmap.get(POST_COUNT) != null)
                POST_COUNT.getAndIncrement();
            post.setId(POST_COUNT.longValue());
            hashmap.put(POST_COUNT.longValue(), post);
            return hashmap.put(POST_COUNT.longValue(), post);
        } else {
            var hashmapTemp = Optional.ofNullable(hashmap.get(post.getId()));
            if (hashmapTemp.isPresent())
                hashmap.put(post.getId(), post);
            else {
                POST_COUNT.getAndSet(post.getId());
                hashmap.put(post.getId(), post);
            }
        }
        return hashmap.get(post.getId());
    }

    public void removeById(long id) {
        var hashmapTemp = Optional.ofNullable(hashmap.get(id));
        if (hashmapTemp.isPresent())
            hashmap.remove(id);
    }
}
