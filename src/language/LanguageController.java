package language;

import zextra.Session;

public class LanguageController {

    public String SHOW_REPLIES_TEXT = LanguageElements.SHOW_REPLIES_TEXT[Session.isInEnglish ? 1 : 0];
    public String HIDE_REPLIES_TEXT = LanguageElements.HIDE_REPLIES_TEXT[Session.isInEnglish ? 1 : 0];
    public String LIKE_TEXT = LanguageElements.LIKE_TEXT[Session.isInEnglish ? 1 : 0];
    public String REPLY_TEXT = LanguageElements.REPLY_TEXT[Session.isInEnglish ? 1 : 0];
    public String NO_REPLIES_TEXT = LanguageElements.NO_REPLIES_TEXT[Session.isInEnglish ? 1 : 0];
    public String ADD_COMMENT_TEXT = LanguageElements.ADD_COMMENT_TEXT[Session.isInEnglish ? 1 : 0];
    public String ADD_REPLY_TEXT = LanguageElements.ADD_REPLY_TEXT[Session.isInEnglish ? 1 : 0];
    public String ADD_POST_TEXT = LanguageElements.ADD_POST_TEXT[Session.isInEnglish ? 1 : 0];
    public String CANCEL_TEXT = LanguageElements.CANCEL_TEXT[Session.isInEnglish ? 1 : 0];

}
