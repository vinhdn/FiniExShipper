package vn.finiex.shipperapp;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.finiex.shipperapp.activities.BaseActivity;
import vn.finiex.shipperapp.adapter.DrawerLeftAdapter;
import vn.finiex.shipperapp.adapter.ViewPageAdapter;
import vn.finiex.shipperapp.config.Constant;
import vn.finiex.shipperapp.http.GetUserInfoTask;
import vn.finiex.shipperapp.http.ServerConnector;
import vn.finiex.shipperapp.models.ImageResponse;
import vn.finiex.shipperapp.models.ImageUpload;
import vn.finiex.shipperapp.service.RegistrationIntentService;
import vn.finiex.shipperapp.tabs.SlidingTabLayout;
import vn.finiex.shipperapp.utils.StringUtils;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    private final String LOGIN_KEY = "isLogin";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mDrawerLeft;
    private RecyclerView.LayoutManager mLayoutLeftManager;
    private DrawerLeftAdapter mDrawerLeftAdapter;

    private ViewPager pager;
    private ViewPageAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence TabTitles[] = {"Map", "Pictures"};
    private int Numboftabs = 3;

    private String TITLES[] = {"Nhiệm vụ", "History", "Contact Admin", "Logout",};
    private int ICONS[] = {R.drawable.ic_action_event,
            R.drawable.ic_action_history, R.drawable.ic_action_group, R.drawable.ic_action_logout};
    private String NAME = "ABC";
    private String EMAIL = "abc.123@make_sence.com";
    private Bitmap AVATAR;


    @Override
    public void onAttachedToWindow() {
        if (mToolbar != null)
            mToolbar.setTitle(TITLES[0]);
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!((ShipperApplication) getApplicationContext()).isLogined()) {
            startActivity(new Intent(this, SigninActivity.class));
            finish();
            stopService(new Intent(this, TrackerService.class));
            return;
        }
        startService(new Intent(this, TrackerService.class));
        setContentView(R.layout.activity_main);
        getDataIntent();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };


        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mDrawerLeft = (RecyclerView) findViewById(R.id.left_layout);
        mDrawerLeft.setHasFixedSize(true);
        mLayoutLeftManager = new LinearLayoutManager(this);
        mDrawerLeft.setLayoutManager(mLayoutLeftManager);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set
        // to the Drawer toggle
        mDrawerToggle.syncState();

        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawerLeft.getChildPosition(v) != mDrawerLeft.getChildCount() - 1) {
                    if (mDrawerLeft.getChildPosition(v) == 2) {
                        Toast.makeText(MainActivity.this, "Comming soon...", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (mDrawerLeft.getChildPosition(v) == 1) {
                        pager.setCurrentItem(0);
                    } else
                        pager.setCurrentItem(mDrawerLeft.getChildPosition(v) - 1);
                } else {
                    //TODO : log out
                    ((ShipperApplication) getApplication()).logout();
                    startActivity(new Intent(MainActivity.this, SigninActivity.class));
                    finish();
                    stopService(new Intent(MainActivity.this, TrackerService.class));
                    return;
                }
                mDrawerLayout.closeDrawers();
//				switch (mDrawerLeft.getChildPosition(v)) {}
            }

        };
        mDrawerLeftAdapter = new DrawerLeftAdapter(TITLES, ICONS, NAME, EMAIL,
                AVATAR, listener);
        mDrawerLeft.setAdapter(mDrawerLeftAdapter);

        adapter = new ViewPageAdapter(getSupportFragmentManager(), TITLES,
                Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(TITLES[position]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(0);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        tabs.setViewPager(pager);
        (new GetUserInfoTask(this, mDrawerLeftAdapter)).execute(((ShipperApplication) getApplicationContext()).getAccessToken());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String url = "";
            if(data == null){
//                Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_LONG).show();
//                return;
                url = file;
            }else
                url = data.getDataString();
            if(TextUtils.isEmpty(url))
                url = file;
            if(TextUtils.isEmpty(url)){
                Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_LONG).show();
                return;
            }
            galleryAddPic(url);
            url = url.replace("file://", "");
            if (url != null) {
                showNotification();
                ServerConnector.getInstance(true).uploadImage(url, new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        if(response.body() != null){
                            String customer = "";
                            if(ShipperApplication.get().getmUserInfo() != null){
                                customer = ShipperApplication.get().getmUserInfo().getFullName();
                            }
                            Object ob = ServerConnector.getInstance().uploadImageFini(new ImageUpload(response.body().data.link, ShipperApplication.get().getAccessToken().getUserId(), customer,
                                    StringUtils.getStringFromDate(StringUtils.DATE_FORMAT_SERVER_02, Calendar.getInstance().getTime())), new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    cancelNotifi();
                                    if(response.body() != null){
                                        Toast.makeText(MainActivity.this, "Upload ảnh thành công", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();;
                                    }
                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();;
                                }
                            });
                        }else {
                            Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        cancelNotifi();
                        Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_SHORT);
                    }
                });
            }else{
                Toast.makeText(MainActivity.this, "Upload ảnh lỗi vui lòng thử lại", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText("Đang upload...");
        builder.setContentTitle("Tải ảnh");
        builder.setSmallIcon(R.drawable.ic_launcher);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(10, builder.build());
    }

    private void cancelNotifi(){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(10);
    }

    private void galleryAddPic(String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPermisstion();
        if(!isGPS(this)){
            Toast.makeText(this, "Bạn cần bật GPS để cập nhật vị trí", Toast.LENGTH_LONG);
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void getDataIntent() {
        try {
            Intent intent = getIntent();
            NAME = intent.getStringExtra("username");
            EMAIL = intent.getStringExtra("email");
            AVATAR = (Bitmap) intent.getParcelableExtra("avatar");
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    private void registerReceiver() {
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(Constant.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(this, TrackerService.class));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermisstion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        69);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        70);
            }
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE},
                        71);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        72);
            }
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = getOutputMediaFile();
            // Error occurred while creating the File
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, MainActivity.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public String file = "";

    public File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "FiniexShipper");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        file = mediaFile.getAbsolutePath();
        return mediaFile;
    }


    // check GPS
    public boolean isGPS(Context cont) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cont.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                checkPermisstion();
                return true;
            }
        }
        LocationManager locationManager = (LocationManager) cont
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
