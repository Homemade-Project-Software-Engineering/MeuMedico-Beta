package br.ufc.dc.es.meumedico.view;

import com.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

/**
 * Created by jonas on 19/06/16.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{

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
    public void testFirst() throws Exception{
        solo.assertCurrentActivity("This isn't the right Activity to test!",LoginActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }


    @SmallTest
    public void testMainActivity() throws Exception {

        solo.enterText(0,"email@example.com");
        solo.typeText(1,"password");

        solo.searchText("email@example.com");
        solo.searchText("password");

        solo.clearEditText(0);
        solo.clearEditText(1);

        solo.clickOnButton(0); // facebook button
        solo.goBackToActivity("LoginActivity");

        /** Enter with a local database user */
        solo.enterText(0,"pedro@gmail.com");
        solo.typeText(1,"2323");

        solo.clickOnButton(1);// enter button

        solo.searchText("Pedro"); // string assure that login works fine
        solo.pressMenuItem(9,1); // menu exit
        solo.clickOnButton(1); //exiting


        /** The same as touch on some button or else */
        solo.clickOnText("CADASTRE-SE");
        solo.goBackToActivity("LoginActivity");


        /** Enter with a local database user */
        solo.enterText(0,"pedro@gmail.com");
        solo.typeText(1,"2323");
        solo.clickOnButton(1);// enter button

        // Not possible to run this two activities separately....
        solo.pressMenuItem(0);
        solo.goBackToActivity("MainActivity");
        solo.pressMenuItem(3);
        //solo.goBackToActivity("MainActivity"); não implementado ainda....

        solo.clickOnText("Cadastrar Atividade");
        solo.goBackToActivity("MainActivity");
        solo.clickOnButton(0);
        solo.goBackToActivity("MainActivity");
        solo.clickOnButton(1);
        // solo.goBackToActivity("MainActivity"); botão emergencia
        //exiting....
        solo.pressMenuItem(9);
        solo.clickOnButton(1);

    }
}