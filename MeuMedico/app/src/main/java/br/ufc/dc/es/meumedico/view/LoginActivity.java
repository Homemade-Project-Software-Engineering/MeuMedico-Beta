package br.ufc.dc.es.meumedico.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dc.es.meumedico.controller.helper.ContaHelper;
import br.ufc.dc.es.meumedico.R;
import br.ufc.dc.es.meumedico.model.LoginDAO;
import br.ufc.dc.es.meumedico.controller.domain.Login;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity{

    Button next;
    TextView conta;
    private Intent contaActivity;
    private ContaHelper helper;
    private Login login;
    private CallbackManager callbackManager;
    private TextInputLayout userTIL, passwordTIL;
    private EditText userET, passwordET;
    private Bundle bFacebookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //inicializado facebook sdk para logar
        //obs: essa chamada precisa vir primeiro que o setContentView
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        callSecondScreen();
        callContaActivity();

        if(AccessToken.getCurrentAccessToken()!=null){

            processLoginFacebook(AccessToken.getCurrentAccessToken());
        }

        userET = (EditText) findViewById(R.id.etGuestName);
        passwordET = (EditText) findViewById(R.id.passwordTextEdit);
        userTIL = (TextInputLayout) findViewById(R.id.inputLayoutUsername);
        passwordTIL = (TextInputLayout) findViewById(R.id.inputLayoutPassword);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_face);
        /*como é somente um único item na lista, e você precisa enviar uma lista,
        melhor usar o Collections.singletonList ao inves de Arrays.asList*/
        loginButton.setReadPermissions(Collections.singletonList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                processLoginFacebook(loginResult);
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("onError");
                Log.v("LoginActivity", error.getCause().toString());
            }
        });
    }

    public void callSecondScreen(){
        next  = (Button) findViewById(R.id.btSecondScreen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper = new ContaHelper(LoginActivity.this);

                login = helper.pegaDadosLogin();

                LoginDAO dao = new LoginDAO(LoginActivity.this);
                if(validateLogin() && validatePassword()) {
                    if (dao.fazerLogin(login)) {

                        Login informacoes = dao.getInformacoes(login);
                        Intent irParaATelaPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                        irParaATelaPrincipal.putExtra("informacoes", informacoes);
                        startActivity(irParaATelaPrincipal);
                        dao.close();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Usuário ou login inválido", Toast.LENGTH_SHORT).show();
                    }
                }
                dao.close();
            }
        });

    }

    private boolean validateLogin() {

        String email = userET.getText().toString();

        if(email.isEmpty()){
            userTIL.setError("Email não pode ser vazio");
            return false;
        }else{
            userTIL.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {

        String password = passwordET.getText().toString();
        if(password.isEmpty()){
            passwordTIL.setError("Senha não pode ser vazia");
            return false;
        }else{
            passwordTIL.setErrorEnabled(false);
            return true;
        }
    }

    public void callContaActivity(){

        conta = (TextView) findViewById(R.id.textViewButton);
        conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contaActivity = new Intent(LoginActivity.this, ContaActivity.class);
                startActivity(contaActivity);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {

        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name")) {
                bundle.putString("first_name", object.getString("first_name"));
            }if (object.has("last_name")) {
                bundle.putString("last_name", object.getString("last_name"));
            }if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
            }if (object.has("gender")) {
                bundle.putString("gender", object.getString("gender"));
            }if (object.has("birthday")) {
                bundle.putString("birthday", object.getString("birthday"));
            }if (object.has("location")) {
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return bundle;
    }

    private void processLoginFacebook(LoginResult loginResult){

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Processando dados...");
        progressDialog.show();

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Get facebook data from login
                bFacebookData = getFacebookData(object);

                LoginDAO dao = new LoginDAO(LoginActivity.this);
                Login login = new Login();

                String nome = bFacebookData.getString("first_name");
                String email = bFacebookData.getString("email");
                login.setName(nome);
                login.setEmail(email);

                if(dao.verificaCadastroFacebookBanco(email)){
                    Log.i("Status","Perfil do facebook já cadastrado no banco");
                }else{
                    dao.cadastraPerfilFacebookBanco(login);
                }

                dao.close();

                progressDialog.dismiss();
                Intent irParaATelaPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                irParaATelaPrincipal.putExtra("infosFacebook", bFacebookData);
                startActivity(irParaATelaPrincipal);
                finish();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parâmetros que pedimos ao facebook
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void processLoginFacebook(AccessToken token){

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                // Get facebook data from login
                bFacebookData = getFacebookData(object);
                Intent irParaATelaPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                irParaATelaPrincipal.putExtra("infosFacebook", bFacebookData);
                startActivity(irParaATelaPrincipal);
                finish();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parâmetros que pedimos ao facebook
        request.setParameters(parameters);
        request.executeAsync();
    }
}
