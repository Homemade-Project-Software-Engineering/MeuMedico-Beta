package br.ufc.dc.es.meumedico.model.others;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by jonas on 22/06/16.
 */
public class MyCustomButton extends Button {

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/PoiretOne-Regular.ttf"));
    }
}
