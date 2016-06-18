package br.ufc.dc.es.meumedico.model.helper;

//import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import org.testng.annotations.Test;

import static org.junit.Assert.*;

/**
 * Created by jonas on 17/06/16.
 */
public class AtividadeHelperTest extends TestCase{

    AtividadeHelper atividadeHelper;

    public AtividadeHelperTest(){
        super("AtividadeHelperTest");
    }


    @org.junit.Before
    public void setUp() throws Exception {
        super.setUp();

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test  //or fail(), or this: @Test(expected=Exception.class)
    public void testPegaCamposAtividade() /*throws Exception*/ {
        try{
            fail();
        }catch (Exception e){e.printStackTrace();}
    }


    @org.junit.Test
    public void testAtividadeParaSerAlterada() throws Exception {

    }
}