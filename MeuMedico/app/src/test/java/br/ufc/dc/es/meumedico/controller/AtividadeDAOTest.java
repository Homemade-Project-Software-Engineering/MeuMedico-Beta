package br.ufc.dc.es.meumedico.controller;

import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dc.es.meumedico.model.Atividade;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jonas on 09/06/16.
 */
public class AtividadeDAOTest extends AndroidTestCase {

    private AtividadeDAO atividadeDAO;
    private Atividade atividade;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        atividadeDAO = mock(AtividadeDAO.class);
        atividade = mock(Atividade.class);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();

    }
    @Test
    public void testOnCreate() throws Exception {
        when(atividadeDAO.getDatabaseName()).thenReturn("NAME_ATIVIDADE");
        assertEquals(atividadeDAO.getDatabaseName(),"NAME_ATIVIDADE");

    }

    @Test
    public void testInsert() throws Exception {// FINALLY!!!!
        when(atividadeDAO.insert(atividade)).thenReturn((long) 100);
        atividadeDAO.insert(atividade);
        assertEquals(atividadeDAO.insert(atividade),100);
    }

    @Test
    public void testGetInformacoes() throws Exception {
        //fail();
        atividade.setId(1);
        atividade.setNome("Name_to_test");
        List<Atividade> atividades = new ArrayList<>();
        atividades.add(atividade);
        when(atividadeDAO.getListaAtividades(0)).thenReturn(atividades);
        // the users activity names should be equal, since they have same id (1)
        assertEquals(atividadeDAO.getListaAtividades(0).get(0).getNome(),atividades.get(0).getNome());
    }
    @Test
    public void testDelete() throws Exception{
        when(atividadeDAO.delete(atividade)).thenReturn((long)200);
        atividadeDAO.insert(atividade);
        //Really Unnecessary
        testInsert();
        assertEquals(atividadeDAO.delete(atividade),200);
    }

}
