package org.example.model;

public class Post {
    private long id;
    private boolean removed;
    private String content;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
        this.removed = false;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRemoved() {
        return removed;
    }
    public void setRemoved() {
       this.removed= true;
    }
}
