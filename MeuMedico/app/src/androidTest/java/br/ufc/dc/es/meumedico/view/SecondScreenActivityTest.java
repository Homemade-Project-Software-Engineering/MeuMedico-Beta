package br.ufc.dc.es.meumedico.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;
/**
 * Created by jonas on 24/06/16.
 */
public class SecondScreenActivityTest extends ActivityInstrumentationTestCase2<SecondScreenActivity> {

    private Solo solo;

    public SecondScreenActivityTest(){
        super(SecondScreenActivity.class);
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
        solo.assertCurrentActivity("This isn't the right Activity to test!",SecondScreenActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }
    public void testSecondScreen(){
        // numeração estranha de indices

        solo.pressMenuItem(0);
        solo.goBackToActivity("SecondScreenActivity");
        solo.pressMenuItem(3);
        //solo.goBackToActivity("SecondScreenActivity"); não implementado ainda....
        solo.pressMenuItem(6);
        solo.clickOnButton(0);
        solo.goBackToActivity("SecondScreenActivity");

        solo.clickOnText("Cadastrar Atividade");
        solo.goBackToActivity("SecondScreenActivity");
        solo.clickOnButton(0);
        solo.goBackToActivity("SecondScreenActivity");
        solo.clickOnButton(1);
        // solo.goBackToActivity("SecondScreenActivity"); botão emergencia
    }

}