package vn.finiex.shipperapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.finiex.shipperapp.http.HTTPUtils;
import vn.finiex.shipperapp.model.AccessToken;

public class SigninActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    private EditText txtUserName;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (HTTPUtils.isConnected(this)) {

        }

    }

    public void login(View v) {

        if (txtUserName.getText().toString().trim().length() < 1
                || txtPassword.getText().toString().trim().length() < 1) {
            Toast.makeText(this, "Please input phone number and password", Toast.LENGTH_LONG).show();
            return;
        }
        (new LoginTask(this)).execute(txtUserName.getText().toString(), txtPassword.getText().toString());


    }

    private class LoginTask extends AsyncTask<String, Integer, String> {
        private String url = "http://api.finiex.vn/api/login?";
        String userName;
        String pass;

        ProgressDialog dialog;

        public LoginTask(Context context) {
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
//			phone=01229559552&password=1
            userName = params[0];
            pass = params[1];
            return HTTPUtils.GET(url + "phone=" + userName + "&password=" + pass);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            System.out.println("Login --" + result);

            if (dialog.isShowing())
                dialog.dismiss();
            if (result != null) {
                //[8,"882fe51893bde626e4932b17090c5c6e"]
                try {
                    result = result.substring(1, result.length() - 2);

                    String data[] = result.split(",\"");

                    System.out.println(data[0] + " -- " + data[1]);

                    ((ShipperApplication) getApplication()).login(userName, pass, new AccessToken(data[1], data[0]));
                    finish();
                } catch (Exception e) {

                    Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }


            return;
        }
    }
}
