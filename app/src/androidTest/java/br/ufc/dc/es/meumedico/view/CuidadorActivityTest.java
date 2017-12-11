package br.ufc.dc.es.meumedico.view;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

/**
 * Created by jonas on 09/07/16.
 */
public class CuidadorActivityTest extends ActivityInstrumentationTestCase2<CuidadorActivity> {

    Solo solo;

    public CuidadorActivityTest(){
        super(CuidadorActivity.class);
    }

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
    public void testFirst() throws Exception {
        solo.assertCurrentActivity("This isn't The right activity", CuidadorActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }
    @SmallTest
    public void testCuidadorActivity() throws Exception{
        solo.clickOnButton(0);
        solo.enterText(0,"Testing insert ID Caregiver");
        assertTrue(solo.searchText("Testing insert ID Caregiver"));
        solo.clickOnButton(0);
        solo.clickOnButton(1);
        solo.enterText(0,"Testing insert ID Caregiver");
        assertTrue(solo.searchText("Testing insert ID Caregiver"));
        solo.clickOnButton(0);

    }
}