package model;

public class Comment {
    private Integer id;
    private Integer id_thread;
    private Integer id_course;
    private Integer id_user;
    private Integer comment_type;
    private String comment_body;
    private String user_name;
    private Integer no_of_likes;


    /**
     * Comment constructor
     * @param id_thread The main thread id
     * @param id_course Which course does the comment belong
     * @param id_user The user id that made the comment
     * @param comment_type The comment type
     * @param comment_body The comment inner body
     * @param no_of_likes Number of likes of the comment
     */
    public Comment(Integer id_thread, Integer id_course, Integer id_user, Integer comment_type, String comment_body, Integer no_of_likes) {
        this.id_thread = id_thread;
        this.id_course = id_course;
        this.id_user = id_user;
        this.comment_type = comment_type;
        this.comment_body = comment_body;
        this.no_of_likes = no_of_likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_thread() {
        return id_thread;
    }

    public void setId_thread(Integer id_thread) {
        this.id_thread = id_thread;
    }

    public Integer getId_course() {
        return id_course;
    }

    public void setId_course(Integer id_course) {
        this.id_course = id_course;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getComment_type() {
        return comment_type;
    }

    public void setComment_type(Integer comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        if (comment_body.length()>255)
            throw new IllegalArgumentException("Komenti nuk mund te jete kaq i gjate");
        this.comment_body = comment_body;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getNo_of_likes() {
        return no_of_likes;
    }

    public void setNo_of_likes(Integer no_of_likes) {
        this.no_of_likes = no_of_likes;
    }
}
