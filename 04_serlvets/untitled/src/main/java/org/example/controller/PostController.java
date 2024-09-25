package org.example.controller;

import org.example.model.Post;
import org.example.service.PostService;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping("/posts")
    public List<Post> all() throws IOException {
        return  service.all();
    }
    @GetMapping("/{id}")
    public Post getById(@PathVariable long id, HttpServletResponse response) throws IOException {
        return service.getById(id);

    }
    @PostMapping("/posts")
    public Post  save(@RequestBody  Post post) throws IOException {
        return service.save(post);
    }
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
    }
}
