package br.ufc.dc.es.meumedico.model.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by jonas on 17/06/16.
 */
public class LoginTest {

    private Login login;

    @Before
    public void setUp() throws Exception {
        login = new Login();
    }

    @After
    public void tearDown() throws Exception {
        /*  Alguma menssagem rodando o tearDown seria interessante,
        *  uma vez que o tearDown será chamado toda vez que um método
        *  terminar de ser testado, é interssante torna-lo nulo
        *  por questão de organização.*/
        login = null;
        assertNull(login);

    }

    @Test
    public void testGetId() throws Exception {
        login.setId(1);
        assertEquals(login.getId(),1);
    }

    @Test
    public void testGetEmail() throws Exception {
        login.setEmail("example@email.com");
        assertEquals(login.getEmail(),"example@email.com");
    }

    @Test
    public void testGetName() throws Exception {
        login.setName("NameTes");
        assertEquals(login.getName(),"NameTes");
    }

    @Test
    public void testGetCrypted_password() throws Exception {
        login.setCrypted_password("xKlso12Saplç9*@");
        assertEquals(login.getCrypted_password(),"xKlso12Saplç9*@");
    }


    @Test
    public void testGetSalt() throws Exception {
        login.setSalt("salt");
        assertEquals(login.getSalt(),"salt");
    }

    @Test
    public void testGetCreated_at() throws Exception {
        login.setCreated_at("April.1.1900");
        assertEquals(login.getCreated_at(),"April.1.1900");
    }

    @Test
    public void testGetUpdated_at() throws Exception {
        login.setUpdated_at("April.1.2000");
        assertEquals(login.getUpdated_at(),"April.1.2000");
    }

}