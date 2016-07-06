package vn.finiex.shipperapp.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import vn.finiex.shipperapp.R;

/**
 * Created by vinh on 7/7/16.
 */

public class DialogNotifi extends AlertDialog{

    public DialogNotifi(Context context) {
        super(context);
        setContentView(R.layout.layout_add_note_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }
}
