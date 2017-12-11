package br.ufc.dc.es.meumedico.view;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

/**
 * Created by jonas on 02/07/16.
 */
public class Cad_AtividadeActivityTest extends ActivityInstrumentationTestCase2<Cad_AtividadeActivity> {

    private Solo solo;

    public Cad_AtividadeActivityTest(){super(Cad_AtividadeActivity.class);}

    @Override
    public void setUp() throws Exception {
        /**setUp() is run before a test case is started.
         This is where the solo object is created.*/
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        /**tearDown() is run after a test case has finished.
         finishOpenedActivities() will finish all the activities that
         have been opened during the test execution.*/
        solo.finishOpenedActivities();
    }

    /**This initial test is done to be called first put before the setUp
     * but should be implemented out as change to the settings in each activity,
     * would be more or less an authentication test.*/
    @SmallTest
    public void testFirst() throws Exception{
        //solo.assertCurrentActivity("This isn't the right Activity to test!",Cad_AtividadeActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }

    @SmallTest
    public void testCad_Atividade() throws Exception{
        solo.enterText(0,"AtividadeTest");
        solo.enterText(1,"Help with my medicines please!");
        solo.enterText(2,"6/6/1966");
        solo.clickOnButton(0);// canceled?
        solo.enterText(3,"23:59:59");
        solo.clickOnButton(0);// canceled?
        //tests
        assertTrue(solo.searchText("AtividadeTest"));
        assertTrue(solo.searchText("Help with my medicines please!"));
        assertTrue(solo.searchText("6/6/1966"));
        assertTrue(solo.searchText("23:59:59"));

        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.clickOnButton(0); // test button touch
    }
}