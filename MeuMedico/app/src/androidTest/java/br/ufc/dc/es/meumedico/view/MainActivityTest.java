package br.ufc.dc.es.meumedico.view;

import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by jonas on 19/06/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;
    /**Super importance....*/
    public MainActivityTest() {
        super(MainActivity.class);
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
    public void testFirst() throws Exception{
        solo.assertCurrentActivity("This isn't the right Activity to test!",MainActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }


    @SmallTest
    public void testMainActivity() throws Exception {

        testFirst();

        solo.enterText(0,"email@exampl.com");
        solo.typeText(1,"password");

        solo.searchText("email@exampl.com");
        solo.searchText("password");

        solo.clearEditText(0);
        solo.clearEditText(1);

        /** The same as touch on some button or else */
        solo.clickOnText("CADASTRE-SE");
        solo.waitForDialogToOpen(5000);
        solo.waitForDialogToClose(10000);
        solo.finishOpenedActivities();

    }
}