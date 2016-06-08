package br.ufc.dc.es.meumedico.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.AndroidTestRunner;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.es.dao.LoginDAO;
import br.ufc.dc.es.model.Login;
import br.ufc.dc.es.model.MyAlternateLogVersion;


/**
 * Created by jonas on 07/06/16.
 */

public class LoginDAOTest extends AndroidTestCase{

    private LoginDAO loginDAO;
    SQLiteDatabase db;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginDAO = new LoginDAO(getContext());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();

    }
    @Test
    public void testOnCreate() throws Exception {

        //fail();

        db = loginDAO.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
        MyAlternateLogVersion.Log("This test has been pass!");

    }

     @Test
    public void testOnUpgrade() throws Exception {
         fail();
    }

    @Test
    public void testInsert() throws Exception {

        //loginDAO = new LoginDAO(getContext());
        //Login login = new Login();
        //login.setName("LoginName");
        //assertEquals(loginDAO.getInformacoes(login).getName(),"LoginName");
        assertNotNull(loginDAO);

    }

    @Test
    public void testFazerLogin() throws Exception {
        fail();
    }

    @Test
    public void testGetInformacoes() throws Exception {
        fail();
    }
}