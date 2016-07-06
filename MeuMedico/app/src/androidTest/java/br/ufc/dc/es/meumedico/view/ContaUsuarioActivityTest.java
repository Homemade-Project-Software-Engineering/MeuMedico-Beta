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
        //Press hard key back button
        assertTrue(solo.searchText("NameExampleTes"));
        assertTrue(solo.searchText("email@gmail.com"));
        solo.goBack();
        solo.clickOnButton(1);
        solo.clickOnButton(0); //canceled?

        solo.clickOnButton(1);
        solo.clickOnButton(1); //deleted?
        /*
        //Press hard key back button
        solo.goBack();
        //solo.goBackToActivity("ContaUsuarioActivity");
        solo.clickOnButton(2);
        solo.clickOnButton(0); //canceled?
        //Press hard key back button
        solo.goBack();
        //solo.goBackToActivity("ContaUsuarioActivity");
        solo.clickOnButton(3);
        solo.clickOnButton(0); //canceled?
        //Press hard key back button
        solo.goBack();
        //solo.goBackToActivity("ContaUsuarioActivity");
        */
    }
}