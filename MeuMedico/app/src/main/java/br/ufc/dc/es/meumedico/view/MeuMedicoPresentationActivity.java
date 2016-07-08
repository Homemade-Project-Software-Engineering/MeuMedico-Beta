package br.ufc.dc.es.meumedico.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import br.ufc.dc.es.meumedico.R;

public class MeuMedicoPresentationActivity extends AppCompatActivity implements Runnable{

    RelativeLayout logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_medico_presentation);

        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.logo_move);

        logo = (RelativeLayout) findViewById(R.id.logo);
        logo.startAnimation(animation);

    }

    public void run() {

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
