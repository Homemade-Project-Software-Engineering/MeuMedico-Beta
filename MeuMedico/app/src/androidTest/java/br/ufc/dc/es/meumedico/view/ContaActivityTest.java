package br.ufc.dc.es.meumedico.view;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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

    @BeforeMethod
    public void setUp() throws Exception {
        /**setUp() is run before a test case is started.
         This is where the solo object is created.*/
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @AfterMethod
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
        solo.assertCurrentActivity("This isn't the right Activity to test!",ContaActivity.class);
        solo.assertMemoryNotLow();
        solo.clearLog();
    }

    @Test
    public void testCallCreateAccount() throws Exception {
        testFirst();
        solo.sleep(2000);

    }
}