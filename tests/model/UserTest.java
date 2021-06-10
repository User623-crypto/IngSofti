package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Ina","InaFti.edu.al");
        user.setId(1);
    }

    @Test
    void copyUser() {
        User expResult = user.copyUser();
        if (!expResult.getName().equals(user.getName()))
            fail("Name parameter is not the same");
        if (!expResult.getPassword().equals(user.getPassword()))
            fail("Password parameter is not the same");
        if (user == expResult)
            fail("Did not create a deep copy");

    }



    @Test
    void saveImageFileLocally() {
        String imageName = user.getImageFile().getName();
        try {
            boolean isSaved=false;
            File directory = new File("./src/res/");
            File fileList[] = directory.listFiles();
            user.saveImageFileLocally();
            for (File file :
                    fileList) {
                if (file.getName().endsWith(imageName)) isSaved=true;
            }
            if (!isSaved) fail("The doc is not saved");

        } catch (IOException e) {
           fail("It should find the directory where it must save the file");
        }
    }


    @Test
    void setNameMaxLength() {
        try {
            user.setName("A255characterlongStringthatshouldnotbealloedtoset as a name");
            fail("It should tirgger an exception");

        }catch (IllegalArgumentException e)
        {
            System.out.println("Cought error correctly");

        }catch (Exception e)
        {
            fail("Wrong exception thrown");
        }
    }




    @Test
    void setPassword() {
        user.setPassword("MynewPassowrd");
        String expResult = "MynewPassowrd";
        assertEquals(expResult,user.getName());
    }


    @Test
    void initFriendsTable() {
    }
}