package meu_medico.es.dc.ufc.com.br.meumedico;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jonas on 26/05/16.
 */
public class MyMainTextView extends TextView {

    public MyMainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/PoiretOne-Regular.ttf"));
    }
}
