package org.example.repository;


import org.example.model.Post;
import org.springframework.stereotype.Repository;
import org.example.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private final ConcurrentHashMap<Long, Post> hashmap = new ConcurrentHashMap<Long, Post>();
    public static volatile AtomicLong POST_COUNT = new AtomicLong(1);

    public List<Post> all() {
        List<Post> list = new ArrayList<Post>(hashmap.values().stream().filter(item -> !item.isRemoved()).collect(Collectors.toList()));
        return list;
    }

    public Optional<Post> getById(long id) {
       var hashmapTemp = Optional.ofNullable(hashmap.get(id)).orElseThrow(NotFoundException::new);
        return Optional.ofNullable(hashmapTemp);
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
                if (hashmap.get(post.getId()).isRemoved())
                    throw new NotFoundException();
                else {
                    hashmap.put(post.getId(), post);
                }
            else {
                POST_COUNT.getAndSet(post.getId());
                hashmap.put(post.getId(), post);
            }
        }
        return hashmap.get(post.getId());
    }


    public void removeById(long id) {
        var hashmapTemp = Optional.ofNullable(hashmap.get(id)).orElseThrow(NotFoundException::new);
        hashmapTemp.setRemoved();
    }
}