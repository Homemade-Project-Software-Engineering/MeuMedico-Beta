package br.ufc.dc.es.meumedico.model;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.meumedico.controller.domain.Atividade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jonas on 09/06/16.
 */
public class MeuMedicoDAOTest extends AndroidTestCase {

    private MeuMedicoDAO meuMedicoDAO;
    private Atividade atividade;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        meuMedicoDAO = mock(MeuMedicoDAO.class);
        atividade = mock(Atividade.class);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        /*  Alguma menssagem rodando o tearDown seria interessante,
        *  uma vez que o tearDown será chamado toda vez que um método
        *  terminar de ser testado, é interssante torna-lo nulo
        *  por questão de organização.*/
        atividade = null;
        meuMedicoDAO = null;
        assertNull(atividade);
        assertNull(meuMedicoDAO);

    }
    @Test
    public void testOnCreate() throws Exception {
        when(meuMedicoDAO.getDatabaseName()).thenReturn("NAME_ATIVIDADE");
        assertEquals(meuMedicoDAO.getDatabaseName(),"NAME_ATIVIDADE");

    }

    @Test
    public void testInsert() throws Exception {// FINALLY!!!!
        when(meuMedicoDAO.insert(atividade)).thenReturn((long) 100);
        meuMedicoDAO.insert(atividade);
        assertEquals(meuMedicoDAO.insert(atividade),100);
    }

    @Test
    public void testGetInformacoes() throws Exception {
        //fail();
        atividade.setId(1);
        atividade.setNome("Name_to_test");
        List<Atividade> atividades = new ArrayList<>();
        atividades.add(atividade);
        when(meuMedicoDAO.getListaAtividades(0, "data atual")).thenReturn(atividades);
        // the users activity names should be equal, since they have same id (1)
        assertEquals(meuMedicoDAO.getListaAtividades(0, "data atual").get(0).getNome(),atividades.get(0).getNome());
    }
    @Test
    public void testDelete() throws Exception{
        when(meuMedicoDAO.delete(atividade)).thenReturn((long)200);
        meuMedicoDAO.insert(atividade);
        //Really Unnecessary
        testInsert();
        assertEquals(meuMedicoDAO.delete(atividade),200);
    }

}
