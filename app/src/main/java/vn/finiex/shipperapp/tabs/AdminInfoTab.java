package vn.finiex.shipperapp.tabs;

import java.util.ArrayList;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.http.ServerConnector;
import vn.finiex.shipperapp.model.UserInfo;

public class AdminInfoTab extends Fragment {

	private Activity activity;

	TextView txtName;
	TextView txtPhone;
	TextView txtAddress;
	TextView txtEmail;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;

		ServerConnector.getInstance().getAdminInfo(new Callback<UserInfo>() {

			@Override
			public void onResponse(Call<UserInfo> arg0, Response<UserInfo> arg1) {

				UserInfo data = arg1.body();
				if (data != null)
					updateAdminInfo(data);
			}

			@Override
			public void onFailure(Call<UserInfo> arg0, Throwable arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.tab_admin_info, container, false);
		txtAddress = (TextView) v.findViewById(R.id.txtAddr);
		txtEmail = (TextView) v.findViewById(R.id.txtEmail);
		txtName = (TextView) v.findViewById(R.id.txtName);
		txtPhone = (TextView) v.findViewById(R.id.txtPhone);

		initAction(v);

		return v;
	}

	private void initAction(View parent) {
		(parent.findViewById(R.id.card_view_addr)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoAddr(v);

			}
		});
		(parent.findViewById(R.id.card_view_email)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoEmail(v);

			}
		});
		(parent.findViewById(R.id.card_view_phone)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gotoPhone(v);

			}
		});
	}

	public void gotoAddr(View v) {
		String addr = (String) txtAddress.getText();

		if (addr.equals(getResources().getString(R.string.str_no_info)))
			return;

		addr = addr.trim();
		addr = addr.replaceAll(" ", "+");

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?f=d&daddr=" + addr));
		intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
		activity.startActivity(intent);
	}

	public void gotoPhone(View v) {
//		Toast.makeText(activity, "PhoneNo is selected", Toast.LENGTH_LONG).show();

		if (txtPhone.getText().equals(getResources().getString(R.string.str_no_info)))
			return;

		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + txtPhone.getText()));
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		activity.startActivity(intent);
	}

	public void gotoEmail(View v) {

	}

	private void updateAdminInfo(final UserInfo info) {
		(new Handler()).post(new Runnable() {

			@Override
			public void run() {
				txtName.setText(info.getFullName());
				txtAddress.setText(info.getAddress());
				txtPhone.setText(info.getPhone());
				txtEmail.setText(info.getEmail());
			}
		});
	}

}
