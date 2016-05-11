package meu_medico.es.dc.ufc.com.br.meumedico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by jonas on 10/05/16.
 */
public class SecondScreen extends Activity{
    Intent secondScreenData;
    Bundle infoFromMainScreen;
    private String user;
    TextView nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        Button bt = (Button) findViewById(R.id.home);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondScreen.this, MainActivity.class));
                finish(); // dispose do java
            }
        });

        setUser("@"+getDataMainScreen());
        setNameView(getUser());
    }
    public void setNameView(String name){
        nameUser = (TextView) findViewById(R.id.nameUser);
        nameUser.setText(name);
    }

    public void setUser(String name){
        this.user = name;
    }
    public String getUser(){
        return user;
    }

    public String getDataMainScreen(){

        String logInString="User not Inform name"; /* Obviously this will never happen...*/
        secondScreenData = getIntent();
        if(secondScreenData != null) {    /* Means that i have info come from mainScreen  */
            infoFromMainScreen = secondScreenData.getExtras();
            if(infoFromMainScreen != null){ /* Means that i have info received from getExtras  */
                logInString=infoFromMainScreen.getString("userInfo");
                setUser(infoFromMainScreen.getString("userInfo")); //saving string user
            }
        }
        return  logInString;
    }
}
