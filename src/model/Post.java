package model;

public class Post {
    private int id;
    private int likeNumber;
    private String text;
    private int post_user;

    /**
     * Post constructor
     * @param likeNumber the like number of the current post
     * @param text The inner text of the Post
     */
    public Post(int likeNumber, String text) {
        this.likeNumber = likeNumber;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPost_user() {
        return post_user;
    }

    public void setPost_user(int post_user) {
        this.post_user = post_user;
    }
}
