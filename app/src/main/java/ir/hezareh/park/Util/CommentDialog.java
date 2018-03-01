package ir.hezareh.park.Util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;

import ir.hezareh.park.DataLoading.networking;
import ir.hezareh.park.R;


public class CommentDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity _activity;
    //public Dialog d;
    private EditText comment;
    private EditText name;
    private int _postID;

    public CommentDialog(Activity activity, int postID) {
        super(activity);
        // TODO Auto-generated constructor stub
        this._activity = activity;
        this._postID = postID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comment_dialog);


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        comment = findViewById(R.id.comment_text);
        name = findViewById(R.id.name);
        Button confirm = findViewById(R.id.confirm);
        Button cancel = findViewById(R.id.cancel);

        new Utils(_activity).overrideFonts(findViewById(R.id.comments_layout), "BHoma");

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (input_validation()) {
                    dismiss();
                    new networking(getContext()).postComment(_postID, comment.getText().toString(), new networking.PostCommentResponseListener() {
                        @Override
                        public void requestStarted() {

                        }

                        @Override
                        public void requestCompleted(String response) {
                            Log.d("response", response + "");
                            new Utils(_activity).showToast("confirmation", _activity);
                        }

                        @Override
                        public void requestEndedWithError(VolleyError error) {
                            new Utils(_activity).showToast("server_error", _activity);

                        }
                    });
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    private boolean input_validation() {
        boolean valid = true;
        String _comment = comment.getText().toString();
        String _name = name.getText().toString();

        if (_name.length() < 1) {
            name.requestFocus();
            valid = false;
        }
        if (_comment.length() < 1) {
            comment.requestFocus();
            valid = false;
        }
        return valid;
    }
}

