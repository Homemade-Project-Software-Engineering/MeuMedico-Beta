package br.ufc.dc.es.meumedico.controller.domain.model;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.es.meumedico.controller.domain.Login;
import br.ufc.dc.es.meumedico.model.LoginDAO;

import static org.mockito.Mockito.*;

public class LoginDAOTest extends AndroidTestCase {

    private LoginDAO loginDAO;
    private Login login;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginDAO =  mock(LoginDAO.class);
        login = mock(Login.class);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        /*  Alguma menssagem rodando o tearDown seria interessante,
        *  uma vez que o tearDown será chamado toda vez que um método
        *  terminar de ser testado, é interssante torna-lo nulo
        *  por questão de organização.*/
        login = null;
        loginDAO = null;
        assertNull(login);
        assertNull(loginDAO);

    }
    @Test
    public void testOnCreate() throws Exception {

        assertNotNull(loginDAO); //not null
        when(loginDAO.getDatabaseName()).thenReturn("NAME_BANK");
        assertEquals(loginDAO.getDatabaseName(),"NAME_BANK");

    }

    @Test
    public void testInsert() throws Exception {
        when(loginDAO.insert(login)).thenReturn((long) 100);
        loginDAO.insert(login);
        assertEquals(loginDAO.insert(login),100);

    }

    @Test
    public void testFazerLogin() throws Exception {
        when(loginDAO.fazerLogin(login)).thenReturn(true);
        assertEquals(loginDAO.fazerLogin(login),true);
    }

    @Test
    public void testGetInformacoes() throws Exception {
        when(loginDAO.getInformacoes(login)).thenReturn(login);
        assertEquals(loginDAO.getInformacoes(login),login);
    }
}