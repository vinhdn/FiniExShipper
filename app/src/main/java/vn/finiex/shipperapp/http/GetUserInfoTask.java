package vn.finiex.shipperapp.http;

import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import vn.finiex.shipperapp.ShipperApplication;
import vn.finiex.shipperapp.adapter.DrawerLeftAdapter;
import vn.finiex.shipperapp.model.AccessToken;
import vn.finiex.shipperapp.model.UserInfo;

public class GetUserInfoTask extends AsyncTask<AccessToken, Void, String>{

	private Context context;
	DrawerLeftAdapter adapter;
	
	public GetUserInfoTask(Context ctx, DrawerLeftAdapter _adapter) {
		context = ctx;
		adapter = _adapter;
	}
	
	@Override
	protected String doInBackground(AccessToken... params) {
		
		String uId = params[0].getUserId();
		String accessToken  = params[0].getAccessToken();
		
//		ServerConnector.getInstance().getUserInfo(uId, accessToken);		
		return HTTPUtils.GET(HTTPUtils.URL_USER_INFO + uId+"?access_token="+accessToken );
	}

	@Override
	protected void onPostExecute(String result) {
		
		super.onPostExecute(result);
//		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		UserInfo userInfo = new Gson().fromJson(result, UserInfo.class);
		ShipperApplication.get().setmUserInfo(userInfo);
		adapter.setUserInfo(userInfo);
	}
}
