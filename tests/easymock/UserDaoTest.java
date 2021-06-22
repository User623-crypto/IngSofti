package easymock;

import model.User;
import model.dao.UserDao;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {

    @Test
    public void testReadUserByName() throws Exception {
        User u = new User("testname", "testpass");
        u.setId(1);
        UserDao userDao = EasyMock.createMock(UserDao.class);
        EasyMock.expect(userDao.readUserByName(EasyMock.isA(String.class))).andReturn(u);
        EasyMock.replay(userDao);

        assertEquals("testname", u.getName());
    }

    @Test
    public void testReadUserById() throws Exception {
        User u = new User("testname", "testpass");
        u.setId(1);
        UserDao userDao = EasyMock.createMock(UserDao.class);
        EasyMock.expect(userDao.readUserById(u.getId())).andReturn(u);
        EasyMock.replay(userDao);

        assertEquals("testname", u.getName());
    }
}
