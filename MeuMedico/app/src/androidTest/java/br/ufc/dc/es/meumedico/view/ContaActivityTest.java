package br.ufc.dc.es.meumedico.view;

import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;


/**
 * Created by jonas on 22/06/16.
 */
public class ContaActivityTest extends ActivityInstrumentationTestCase2<ContaActivity>{

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

    @SmallTest
    public void testCallCreateAccount() throws Exception {
        solo.enterText(0,"New client");
        solo.enterText(1,"new_client@email.com");
        solo.enterText(2,"12345678");
        //solo.takeScreenshot(); need a path to save the screens.
        assertTrue(solo.searchText("12345678"));
        //solo.clickOnButton(0); //creating an account
        //solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.));
        //solo.goBack();
    }

}