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
        user = new User("Test","Passtest");
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
    void setNameMaxLength() {
        try {
            user.setName("255characterlongStringthatshouldnotbeallowedtoset as a name");
            fail("It should trigger an exception");

        }catch (IllegalArgumentException e)
        {
            System.out.println("Caught error correctly");

        }catch (Exception e)
        {
            fail("Wrong exception thrown");
        }
    }




    @Test
    void setPassword() {
        user.setPassword("MynewPassowrd");
        String expResult = "MynewPassowrd";
        assertEquals(expResult,user.getPassword());
    }


    @Test
    void initFriendsTable() {
    }
}