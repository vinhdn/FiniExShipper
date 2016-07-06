package vn.finiex.shipperapp.adapter;

import java.util.ArrayList;
import java.util.List;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.activities.TaskDetailActivity;
import vn.finiex.shipperapp.model.Order;
import vn.finiex.shipperapp.model.Task;
import vn.finiex.shipperapp.utils.StringUtils;

public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.ViewHolder> {

    Context mContext;

    public List<Task> getMyArray() {
        return myArray;
    }

    public void setMyArray(List<Task> myArray) {
        this.myArray = myArray;
    }

    List<Task> myArray = null;

    public CurrentOrderAdapter(Context context, List<Task> myArray) {
        this.mContext = context;
        this.myArray = myArray;
    }

    @Override
    public int getItemCount() {
        if (myArray == null) return 0;
        return myArray.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int i) {
        vh.mTitleTv.setText(myArray.get(i).get_LadingName());
        vh.mDateTime.setText(myArray.get(i).get_DateCreated());
        if(!TextUtils.isEmpty(myArray.get(i).get_Notes()))
            vh.mNoteTv.setText(myArray.get(i).get_Notes());
        vh.mStatusTv.setText(getTrangThaiStr(myArray.get(i).get_Status()));
        if (myArray.get(i).getOrder() == null || myArray.get(i).getOrder().size() <= 0) {

        } else {
            if (!TextUtils.isEmpty(myArray.get(i).getOrder().get(0).getPhone()))
                vh.mPhoneNo.setText(myArray.get(i).getOrder().get(0).getPhone());
            else vh.mPhoneNo.setText("");
            if (!TextUtils.isEmpty(myArray.get(i).getOrder().get(0).getNoitra()))
                vh.mAddress.setText(myArray.get(i).getOrder().get(0).getNoitra());
            if (!TextUtils.isEmpty(myArray.get(i).getOrder().get(0).getEndDate()))
                vh.mDateTime.setText(StringUtils.dataToFeauture(myArray.get(i).getOrder().get(0).getEndDate()));
        }

        vh.mPhoneNo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "PhoneNo is selected", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Intent.ACTION_CALL);
                if (myArray.get(i).getOrder() == null || myArray.get(i).getOrder().size() <= 0) {

                } else {
                    if (!TextUtils.isEmpty(myArray.get(i).getOrder().get(0).getPhone())) {
                        intent.setData(Uri.parse("tel:" + myArray.get(i).getOrder().get(0).getPhone()));
                    } else {
                        intent = null;
                    }
                }

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (intent != null)
                    mContext.startActivity(intent);
            }
        });


        vh.mAddress.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (myArray.get(i).getOrder() == null || myArray.get(i).getOrder().size() <= 0)
                    return;
                String addr = myArray.get(i).getOrder().get(0).getNoitra();
                if(TextUtils.isEmpty(addr)) return;
                addr = addr.trim();
                addr = addr.replaceAll(" ", "+");

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=" + addr));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                mContext.startActivity(intent);
            }
        });
        vh.itemView.findViewById(R.id.card_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TaskDetailActivity.class);
                intent.putExtra("id", myArray.get(i).get_LadingID());
                intent.putExtra("name", myArray.get(i).get_LadingName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView mTaskType;
        TextView mPhoneNo;
        TextView mAddress;
        TextView mDateTime;
        TextView mTitleTv;
        TextView mNoteTv;
        TextView mStatusTv;

        //        ImageView mPhoto1;
        LinearLayout ln;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            mTitleTv = (TextView) itemView.findViewById(R.id.txt_task_title);
            mTaskType = (TextView) itemView.findViewById(R.id.txt_location);
            mStatusTv = (TextView) itemView.findViewById(R.id.trangthai_tv);
            mPhoneNo = (TextView) itemView.findViewById(R.id.txtPhone);
            mAddress = (TextView) itemView.findViewById(R.id.txtAddr);
            mDateTime = (TextView) itemView.findViewById(R.id.txtTime);
            mNoteTv = (TextView) itemView.findViewById(R.id.note_tv);
//			mPhoto1 = (ImageView)itemView.findViewById(R.id.img_multi_picture);
            ln = (LinearLayout) itemView.findViewById(R.id.ln_card_view);
        }
    }

    private String getTrangThaiStr(int type) {
        switch (type) {
            case 1:
                return "Thiết lập";
            case 2:
                return "Vận chuyển";
            case 3:
                return "Hoàn thành";
            case 4:
                return "Vận đơn lỗi";
        }
        return "Chờ cập nhật";
    }
}