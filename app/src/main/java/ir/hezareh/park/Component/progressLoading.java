package ir.hezareh.park.Component;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import ir.hezareh.park.R;
import ir.hezareh.park.Util.Utils;


public class progressLoading extends Dialog {

    private Context _context;

    public progressLoading(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this._context = context;
        this.setCancelable(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_loading_layout);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView message = findViewById(R.id.loadingText);
        message.setText("لطفا صبر کنید...");

        TextView title = findViewById(R.id.alertTitle);
        title.setText("دریافت اطلاعات");

        new Utils(_context).overrideFonts(findViewById(R.id.progress_layout), "BHoma");

    }

}

