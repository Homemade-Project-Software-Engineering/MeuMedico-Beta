package br.ufc.dc.es.meumedico.controller.others;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyCustomEditText extends EditText {
    public MyCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Caviar_Dreams_Bold.ttf"));
    }
}
