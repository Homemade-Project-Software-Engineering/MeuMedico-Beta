package br.ufc.dc.es.meumedico.view;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.robotium.solo.Solo;

/**
 * Created by jonas on 06/07/16.
 */
public class FullappTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    /**Super importance....*/
    public FullappTest() {
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
    @LargeTest
    public void testFullApp() throws Exception{


        solo.enterText(0,"email@example.com");
        solo.typeText(1,"password");

        solo.searchText("email@example.com");
        solo.searchText("password");

        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.clickOnText("CADASTRE-SE");

        /**Enter in ContaActivity */


        solo.enterText(0,"PatientTest");
        solo.enterText(1,"patient@email.com");
        solo.enterText(2,"12345678");
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btcriarConta));

        /**Back to Login and enter again, now with new patient*/

        solo.enterText(0,"patient@email.com");
        solo.enterText(1,"12345678");
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btSecondScreen));



        // first menu item
        //solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.itemOptionsConta));
        solo.pressMenuItem(0);
        // enter in MyCount
        // edit account
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btEditarConta));
        //check the info
        assertTrue(solo.searchText("patient@email.com"));
        assertTrue(solo.searchText("PatientTest"));
        //change the password's account....
        solo.enterText(2,"87654321");
        //create new account....
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btcriarConta));

        solo.goBack();
        solo.goBack();

        // second menu item
        solo.pressMenuItem(3);
        // register caregiver
        //solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btCadastrarCuidador));
        solo.clickOnButton(0);
        // enter id caregiver
        solo.enterText(0,"1");
        // creating new caregiver
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btn_Confirmar));


        //solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btExcluirCuidador));
        // cancel exclusion
        solo.clickOnButton(1);
        solo.clickOnView(solo.getView(br.ufc.dc.es.meumedico.R.id.btn_Cancelar));

        solo.goBack();
        //solo.goBack();

        // bt calender
        solo.clickOnButton(0);
        //solo.goBack();
        solo.goBackToActivity("MainActivity");

        // bt emergency, do not click

        //enter again in menu 1
        solo.pressMenuItem(0);
        // exclude account
        solo.clickOnButton(1);
        solo.clickOnButton(1);

        try {
            solo.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

}
