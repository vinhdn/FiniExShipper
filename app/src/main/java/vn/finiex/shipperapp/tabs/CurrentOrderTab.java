package vn.finiex.shipperapp.tabs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import vn.finiex.shipperapp.MainActivity;
import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.ShipperApplication;
import vn.finiex.shipperapp.TrackerService;
import vn.finiex.shipperapp.adapter.CurrentOrderAdapter;
import vn.finiex.shipperapp.config.Constant;
import vn.finiex.shipperapp.model.Order;
import vn.finiex.shipperapp.model.Task;

public class CurrentOrderTab extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private List<Task> sceneList = new ArrayList<Task>();
    private Activity activity;
    CurrentOrderAdapter mAdapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if (mAdapter != null && ShipperApplication.mService != null) {
            mAdapter.setMyArray(ShipperApplication.mService.getListTask());
            mAdapter.notifyDataSetChanged();
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TrackerService.UPDATE_TASK)) {
                    if (mAdapter != null && ShipperApplication.mService != null) {
                        mAdapter.setMyArray(ShipperApplication.mService.getListTask());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_list_item, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lv_data);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        for (int i = 0; i < 100; i++) {
//        	Order tmp = new Order();
//        	tmp.setAddr("1 Pham Van Bach, Cau Giay, Ha Noi");
//        	tmp.setDate("date " + i);
//        	tmp.setPhoneNo("265 589 2364");;
//        	tmp.setId(34);
//        	tmp.setNotes("fghjkl");
//        	tmp.setShipfee(89);
//        	tmp.setStatus(1);
//        	tmp.setTotalAmount(789);
//        	sceneList.add(tmp);
        }

        if (ShipperApplication.mService != null)
            sceneList = ShipperApplication.mService.getListTask();
        // 3. create an adapter 
        mAdapter = new CurrentOrderAdapter(activity, sceneList);
        if (ShipperApplication.mService != null)
            mAdapter.setMyArray(ShipperApplication.mService.getListTask());
        // 4. set adapter
        mRecyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ImageButton btn = (ImageButton) v.findViewById(R.id.btnTakePic);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter != null && ShipperApplication.mService != null) {
            mAdapter.setMyArray(ShipperApplication.mService.getListTask());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void dispatchTakePictureIntent() {
        ((MainActivity)getActivity()).dispatchTakePictureIntent();
    }

    private static File getOutputMediaFile() {
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

        return mediaFile;
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
        Calendar cal = Calendar.getInstance();
        File image = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), (cal.getTimeInMillis() + ".jpg"));
        if (!image.exists()) {
            try {
                image.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            image.delete();
            try {
                image.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:/" + image.getAbsolutePath();
        return image;
    }

    private boolean isReceiverRegistered = false;

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    private void registerReceiver() {
        getActivity().registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(TrackerService.UPDATE_TASK));
    }
}
