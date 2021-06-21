package model;

import language.LanguageController;

public class Comment {
    private Integer id;
    private Integer id_thread;
    private Integer id_course;
    private Integer id_user;
    private Integer comment_type;
    private String comment_body;
    private String user_name;

    LanguageController lang = new LanguageController();

    public Comment(Integer id_thread, Integer id_course, Integer id_user, Integer comment_type, String comment_body) {
        this.id_thread = id_thread;
        this.id_course = id_course;
        this.id_user = id_user;
        this.comment_type = comment_type;
        this.comment_body = comment_body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_thread() {
        return id_thread == null ? null : id_thread;
    }

    public void setId_thread(Integer id_thread) {
        this.id_thread = id_thread;
    }

    public Integer getId_course() {
        return id_course == null ? null : id_course;
    }

    public void setId_course(Integer id_course) {
        this.id_course = id_course;
    }

    public Integer getId_user() {
        return id_user == null ? null : id_user;
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
            throw new IllegalArgumentException(lang.LONG_COMMENT_ERROR_TEXT);
        if (comment_body.trim().length() == 0)
            throw new IllegalArgumentException(lang.EMPTY_COMMENT_ERROR_TEXT);
        this.comment_body = comment_body;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
