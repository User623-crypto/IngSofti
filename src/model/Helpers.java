package model;

/**
 * Helper class with static members
 */
public class Helpers {
    /**
     * It will be used to indicate the comment type as an integer
     * Example: Helpers.CommentType.BASIC_COMMENT.ordinal();
     */
    public static enum CommentType {
        BASIC_COMMENT,
        REPLY,
        ANNOUNCEMENT,
        POST_UPDATE
    }
    public static String ADD_COMMENT = "Add comment";
    public static String ADD_REPLY = "Add reply";
    public static String ADD_POST = "Add post";
}
