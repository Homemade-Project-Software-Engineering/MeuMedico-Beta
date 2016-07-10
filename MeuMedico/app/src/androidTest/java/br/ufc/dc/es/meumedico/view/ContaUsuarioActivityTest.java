package br.ufc.dc.es.meumedico.view;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

/**
 * Created by jonas on 27/06/16.
 */
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
    @SmallTest
    public void testContaActivity(){
        solo.clickOnButton(0);
        solo.goBack();
        solo.clickOnButton(1);
        solo.goBack();
    }
}