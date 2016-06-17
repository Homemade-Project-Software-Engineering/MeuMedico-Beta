package br.ufc.dc.es.meumedico.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.es.meumedico.model.domain.Atividade;

import static org.junit.Assert.*;

/**
 * Created by jonas on 09/06/16.
 */
public class AtividadeTest {

    private Atividade atividade;

    @Before
    public void setUp() throws Exception {

        atividade = new Atividade();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetNome() throws Exception {

        atividade.setNome("name");
        assertEquals(atividade.getNome(),"name");

    }

    @Test
    public void testGetDescricao() throws Exception {
        atividade.setDescricao("something");
        assertEquals(atividade.getDescricao(),"something");
    }

    @Test
    public void testGetData() throws Exception {
        atividade.setData("data");
        assertEquals(atividade.getData(),"data");
    }

    @Test
    public void testGetHora() throws Exception {
        atividade.setHora("hora");
        assertEquals(atividade.getHora(),"hora");
    }
    @Test
    public void testGetId() throws Exception {
        atividade.setId(12);
        assertEquals(atividade.getId(),12);
    }

    @Test
    public void testGetId_usuario() throws Exception {
        atividade.setId(11);
        assertEquals(atividade.getId(),11);
    }

}