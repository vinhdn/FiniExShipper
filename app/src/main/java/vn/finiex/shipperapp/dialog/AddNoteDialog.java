package vn.finiex.shipperapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.ShipperApplication;
import vn.finiex.shipperapp.http.ServerConnector;
import vn.finiex.shipperapp.model.StatusOrder;
import vn.finiex.shipperapp.utils.StringUtils;

/**
 * Created by vinh on 7/7/16.
 */

public class AddNoteDialog extends DialogFragment{

    private int orderId;
    private int status;
    private String oldNote;

    public static AddNoteDialog getInstance(int orderId, int status, String note, Context context){
        AddNoteDialog dialog = new AddNoteDialog();
        dialog.orderId = orderId;
        dialog.status = status;
        dialog.oldNote = note;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext(), R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_add_note_dialog, null);
        final EditText mNoteEdt = (EditText) view.findViewById(R.id.note_edt);
        view.findViewById(R.id.update_note_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String note1 = mNoteEdt.getText().toString();
                if(TextUtils.isEmpty(note1)) return;
                if(oldNote == null) oldNote = "";
                if(!TextUtils.isEmpty(oldNote))
                    oldNote = " / " + oldNote;
                final String note = note1 + oldNote;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Object ob = ServerConnector.getInstance().updateOrder(orderId + "", new StatusOrder(status, note));
                        if (ob != null) {
                            Log.d("OBJECT", ob.toString());
                            if(ShipperApplication.mService != null){
                                ShipperApplication.mService.requestTask();
                                dismiss();
                            }
                        }
                    }
                }).start();

            }
        });
        dialog.setContentView(view);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.horizontalMargin = 20;
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
