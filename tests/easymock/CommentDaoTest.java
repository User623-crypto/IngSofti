package easymock;

import model.Comment;
import model.dao.CommentDao;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentDaoTest {

    @Test
    public void testGetComments() throws Exception {
        Comment c = new Comment(1, null, 1, 1, "Test!!!");
        List<Comment> commentList = new ArrayList<>();
        commentList.add(c);
        CommentDao commentDao = EasyMock.createMock(CommentDao.class);
        EasyMock.expect(commentDao.getComments(EasyMock.isA(Integer.class), EasyMock.isA(Integer.class), EasyMock.isA(Integer.class), EasyMock.isA(Integer.class))).andReturn(commentList);
        EasyMock.replay(commentDao);
        List<Comment> testList = commentDao.getComments(1, 0, 1, 1);
        assertEquals(commentList, testList);
    }

    @Test
    public void testInsertComment() throws Exception {
        Comment c = new Comment(1, null, 1, 1, "Test!!!");
        c.setId(1);
        CommentDao commentDao = EasyMock.createMock(CommentDao.class);
        EasyMock.expect(commentDao.insertIntoDB(EasyMock.anyObject(Comment.class))).andReturn(c);
        EasyMock.replay(commentDao);
        assertEquals("Test!!!", c.getComment_body());
    }

    @Test
    public void testAddLikeToComment() throws Exception {
        Comment c = new Comment(1, null, 1, 1, "Test!!!");
        c.setId(1);
        CommentDao commentDao = EasyMock.createMock(CommentDao.class);
        EasyMock.expect(commentDao.insertIntoDB(EasyMock.anyObject(Comment.class))).andReturn(c);
        commentDao.addLikeToComment(c.getId(), 1);
        commentDao.addLikeToComment(c.getId(), 2);
        commentDao.addLikeToComment(c.getId(), 3);

        int nrLikes = 3;
        EasyMock.expect(commentDao.getLikes(c.getId())).andReturn(nrLikes).once();
        EasyMock.replay(commentDao);
        assertEquals(3, nrLikes);
    }

}
