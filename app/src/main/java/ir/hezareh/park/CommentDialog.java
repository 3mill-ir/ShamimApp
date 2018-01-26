package ir.hezareh.park;

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


public class CommentDialog extends Dialog implements android.view.View.OnClickListener {

    Activity _activity;
    //public Dialog d;
    Button yes, no;
    EditText comment;
    EditText name;
    int _postID;

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
        yes = findViewById(R.id.confirm);
        no = findViewById(R.id.cancel);

        new Utils(_activity).overrideFonts(findViewById(R.id.comments_layout), "BHoma");

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (input_validation()) {
                    new networking().postComment(_postID, comment.getText().toString(), new networking.PostCommentResponseListener() {
                        @Override
                        public void requestStarted() {

                        }

                        @Override
                        public void requestCompleted(String response) {
                            Log.d("response", response + "");
                        }

                        @Override
                        public void requestEndedWithError(VolleyError error) {

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

        if (_name.length() < 3) {
            name.requestFocus();
            valid = false;
        }
        if (_comment.length() < 8) {
            comment.requestFocus();
            valid = false;
        }
        return valid;
    }
}

