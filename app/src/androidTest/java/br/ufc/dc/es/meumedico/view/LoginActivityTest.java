package br.ufc.dc.es.meumedico.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    /**Super importance....*/
    public LoginActivityTest() {
        super(LoginActivity.class);
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
        solo.assertCurrentActivity("This isn't The right activity",LoginActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }
    @SmallTest
    public void testMainActivity() throws Exception {

        solo.enterText(0,"email@gmail.com");
        solo.enterText(1,"12345678");

        assertTrue(solo.searchText("email@gmail.com"));
        assertTrue(solo.searchText("12345678"));

        solo.clearEditText(0);
        solo.clearEditText(1);

        solo.clickOnButton(0);
        solo.goBack();
        solo.clickOnButton(1);
        solo.clickOnText("CADASTRE-SE");
        solo.goBack();
    }

}
