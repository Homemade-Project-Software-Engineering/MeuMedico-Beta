package meu_medico.es.dc.ufc.com.br.meumedico;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * Created by jonas on 10/05/16.
 */
public class SecondScreen extends Activity{
    Intent secondScreenData;
    Bundle infoFromMainScreen;
    private String user;
    private Button home;
    private Button datePicker;
    TextView nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        fillSpinner();
        btHome();
        btDate();

        setUser("@"+getDataMainScreen());
        setNameView(getUser());
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
     }
     public void btDate(){
         datePicker = (Button) findViewById(R.id.btDatePiker);
         datePicker.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DialogFragment newFragment = new DatePickerFragment();
                 newFragment.show(getFragmentManager(),"datePicker");
             }
         });
     }

     public void btHome(){
         home = (Button) findViewById(R.id.home);
         home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondScreen.this, MainActivity.class));
                finish(); // dispose do java
            }
        });
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

    public void fillSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerOptions);
        String[] options = getResources().getStringArray(R.array.options);
        ArrayAdapter<String> adapterOptions =
                new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterOptions);
        spinner.setPrompt("Options");
    }

}
