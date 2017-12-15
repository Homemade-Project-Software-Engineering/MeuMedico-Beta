package br.ufc.dc.es.meumedico.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

public class ContaActivityTest extends ActivityInstrumentationTestCase2<ContaActivity> {

    private Solo solo;

    public ContaActivityTest(){
        super(ContaActivity.class);
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
        solo.assertCurrentActivity("This isn't The right activity", ContaActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }
    @SmallTest
    public void testCallCreateAccount() throws Exception {
        solo.enterText(0,"New client");
        solo.enterText(1,"new_client@email.com");
        solo.enterText(2,"12345678");

        assertTrue(solo.searchText("12345678"));
        assertTrue(solo.searchText("new_client@email.com"));
        assertTrue(solo.searchText("New client"));

        solo.clearEditText(2);
        solo.clearEditText(1);
        solo.clearEditText(0);


    }

}