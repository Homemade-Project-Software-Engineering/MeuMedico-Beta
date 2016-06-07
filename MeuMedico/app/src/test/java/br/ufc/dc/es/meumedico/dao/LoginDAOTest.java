package br.ufc.dc.es.meumedico.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import org.junit.Test;
import br.ufc.dc.es.dao.LoginDAO;
import br.ufc.dc.es.model.MyAlternateLogVersion;

/**
 * Created by jonas on 06/06/16.
 */

/*
*       STILL FAILING, BUT JUST A SKELETON PREVIEW....
*
* */

public class LoginDAOTest extends AndroidTestCase {

    Context context;

    public void setUp() throws Exception {
        super.setUp();
        context = new MockContext();
        setContext(context);
        assertNotNull(context);
    }
    @Test
    public void testOnCreate(){
        LoginDAO loginDao = new LoginDAO(context);
        SQLiteDatabase db = loginDao.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
        MyAlternateLogVersion.Log("test onCreate has been passed!");
    }

}
