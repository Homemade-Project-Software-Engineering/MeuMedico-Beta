package meu_medico.es.dc.ufc.com.br.meumedico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    Button next;
    Intent secondScreen;
    Bundle sendInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callSecondScreen();

    }

    public String getGuest(){

        final EditText  logInString = (EditText) findViewById(R.id.etGuestName);
        return logInString.getText().toString();
    }



    public void callSecondScreen(){
        next  = (Button) findViewById(R.id.btSecondScreen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                secondScreen = new Intent(MainActivity.this,SecondScreen.class);
                sendInformation = new Bundle();

                /*  Send a String captured in this activity */

                sendInformation.putString("userInfo",getGuest());
                secondScreen.putExtras(sendInformation);

                /*  Send now through this bundle mainScreen, the string login
                *   typed by the user to the second activity.
                * */
                startActivity(secondScreen);
                finish();

            }
        });

    }
}
