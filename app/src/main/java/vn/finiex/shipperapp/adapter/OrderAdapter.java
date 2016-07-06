package vn.finiex.shipperapp.adapter;

/**
 * Created by vinh on 7/6/16.
 */

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.ShipperApplication;
import vn.finiex.shipperapp.activities.TaskDetailActivity;
import vn.finiex.shipperapp.dialog.AddNoteDialog;
import vn.finiex.shipperapp.http.ServerConnector;
import vn.finiex.shipperapp.model.Order;
import vn.finiex.shipperapp.model.StatusOrder;
import vn.finiex.shipperapp.model.Task;
import vn.finiex.shipperapp.utils.StringUtils;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context mContext;

    public List<Order> getMyArray() {
        return myArray;
    }

    public void setMyArray(List<Order> myArray) {
        this.myArray = myArray;
    }

    List<Order> myArray = null;

    public OrderAdapter(Context context, List<Order> myArray) {
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

        final SwipeLayout swipeLayout = (SwipeLayout) vh.itemView.findViewById(R.id.swipe_layout);

//set show mode.
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

//add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, vh.itemView.findViewById(R.id.bottom_wrapper));

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        Order order = myArray.get(i);
        if (order.getOrderName() != null)
            vh.mTitleTv.setText(order.getOrderName());
        if (order.get_DateCreated() != null)
            vh.mDateTime.setText(myArray.get(i).get_DateCreated());
        if (!TextUtils.isEmpty(myArray.get(i).getPhone()))
            vh.mPhoneNo.setText(myArray.get(i).getPhone());
        else vh.mPhoneNo.setText("");
        if (!TextUtils.isEmpty(myArray.get(i).getNoitra()))
            vh.mAddress.setText(myArray.get(i).getNoitra());
        if (!TextUtils.isEmpty(order.getNotes()))
            vh.mAddress.setText(order.getNotes());
        if (!TextUtils.isEmpty(myArray.get(i).getNoilay()))
            vh.mAddressLay.setText(myArray.get(i).getNoilay());
        if (!TextUtils.isEmpty(myArray.get(i).getEndDate()))
            vh.mDateTime.setText(StringUtils.dataToFeauture(myArray.get(i).getEndDate()));
        vh.mStatusTv.setText(getTrangThaiStr(order.getStatus()));
        if(order.getStatus() < 4){
            vh.mHoanthanhBtn.setText(getTrangThaiStr(order.getStatus() + 1));
            vh.mHoanthanhBtn.setEnabled(true);
        }else {
            vh.mHoanthanhBtn.setEnabled(false);
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("VND"));
        try {
            vh.mFreeShipTv.setText(format.format(order.getPriceShip()));
        } catch (Exception ex) {
        }
        try {
            vh.mFreeTv.setText(format.format(order.getPrices()));
        } catch (Exception ex) {
        }
        vh.mPhoneNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "PhoneNo is selected", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Intent.ACTION_CALL);
                if (!TextUtils.isEmpty(myArray.get(i).getPhone())) {
                    intent.setData(Uri.parse("tel:" + myArray.get(i).getPhone()));
                } else {
                    intent = null;
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


        vh.mAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String addr = myArray.get(i).getNoitra();
                if (TextUtils.isEmpty(addr)) return;
                addr = addr.trim();
                addr = addr.replaceAll(" ", "+");

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=" + addr));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                mContext.startActivity(intent);
            }
        });
        vh.mAddressLay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String addr = myArray.get(i).getNoilay();
                if (TextUtils.isEmpty(addr)) return;
                addr = addr.trim();
                addr = addr.replaceAll(" ", "+");

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?f=d&daddr=" + addr));
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                mContext.startActivity(intent);
            }
        });
        vh.itemView.findViewById(R.id.note_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteDialog dialog = AddNoteDialog.getInstance(myArray.get(i).getOrderID(), mContext);
                dialog.show(((TaskDetailActivity)mContext).getSupportFragmentManager(), AddNoteDialog.class.getName());
                swipeLayout.close(true);
            }
        });

        vh.itemView.findViewById(R.id.hoanthanh_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeLayout.close(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Object ob = ServerConnector.getInstance().updateOrder(myArray.get(i).getOrderID() + "", new StatusOrder((myArray.get(i).getStatus() + 1), "Hoàn thành"));
                        if (ob != null) {
                            Log.d("OBJECT", ob.toString());
                            if(ShipperApplication.mService != null){
                                ShipperApplication.mService.requestTask();
                            }
                        }
                    }
                }).start();

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
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
        TextView mAddressLay;
        TextView mDateTime;
        TextView mTitleTv;
        TextView mStatusTv;
        TextView mFreeTv;
        TextView mFreeShipTv;
        TextView mNoteTv;
        Button mHoanthanhBtn;

        //        ImageView mPhoto1;
        LinearLayout ln;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            mHoanthanhBtn = (Button) itemView.findViewById(R.id.hoanthanh_btn);
            mTitleTv = (TextView) itemView.findViewById(R.id.txt_task_title);
            mFreeTv = (TextView) itemView.findViewById(R.id.prices_tv);
            mFreeShipTv = (TextView) itemView.findViewById(R.id.fee_ship_tv);
            mStatusTv = (TextView) itemView.findViewById(R.id.trangthai_tv);
            mTaskType = (TextView) itemView.findViewById(R.id.txt_location);
            mPhoneNo = (TextView) itemView.findViewById(R.id.txtPhone);
            mAddress = (TextView) itemView.findViewById(R.id.txtAddr);
            mAddressLay = (TextView) itemView.findViewById(R.id.addr_lay_tv);
            mDateTime = (TextView) itemView.findViewById(R.id.txtTime);
            mNoteTv = (TextView) itemView.findViewById(R.id.note_tv);

//			mPhoto1 = (ImageView)itemView.findViewById(R.id.img_multi_picture);
            ln = (LinearLayout) itemView.findViewById(R.id.ln_card_view);
        }
    }

    private String getTrangThaiStr(int type) {
        switch (type) {
            case 1:
                return "Đang chờ";
            case 2:
                return "Đã nhận";
            case 3:
                return "Đã chuyển đơn";
            case 4:
                return "Đã hoàn thành";
            case 5:
                return "Lỗi";
        }
        return "Chờ cập nhật";
    }
}