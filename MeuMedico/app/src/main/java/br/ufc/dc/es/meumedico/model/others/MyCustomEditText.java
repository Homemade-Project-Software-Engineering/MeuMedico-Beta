package br.ufc.dc.es.meumedico.model.others;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by jonas on 23/06/16.
 */
public class MyCustomEditText extends EditText {
    public MyCustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Caviar_Dreams_Bold.ttf"));
    }
}
