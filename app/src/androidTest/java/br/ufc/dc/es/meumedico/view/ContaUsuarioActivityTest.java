package br.ufc.dc.es.meumedico.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

public class ContaUsuarioActivityTest extends
        ActivityInstrumentationTestCase2<ContaUsuarioActivity> {

    private Solo solo;
    /**Super importance....*/
    public ContaUsuarioActivityTest() {
        super(ContaUsuarioActivity.class);
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
        solo.assertCurrentActivity("This isn't The right activity",ContaUsuarioActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }
    @SmallTest
    public void testContaActivity()throws Exception {
        solo.clickOnButton(0);
        solo.goBack();
        solo.clickOnButton(1);
        solo.goBack();
    }
}