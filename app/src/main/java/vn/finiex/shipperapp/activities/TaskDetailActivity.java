package vn.finiex.shipperapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.ShipperApplication;
import vn.finiex.shipperapp.TrackerService;
import vn.finiex.shipperapp.adapter.OrderAdapter;
import vn.finiex.shipperapp.model.Order;
import vn.finiex.shipperapp.model.Task;

public class TaskDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private int landingId;
    private String landingName;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private List<Order> listOrder = new ArrayList<>();
    private OrderAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        landingId = getIntent().getIntExtra("id", -1);
        landingName = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(landingName)) {
            landingName = "Đơn hàng";
        }
        if (landingId < 0) {
            finish();
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setNavigationIcon(R.drawable.icon_titlebar_backarrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (landingName != null) {
            mToolbar.setTitle(landingName);
            setTitle(landingName);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(TrackerService.UPDATE_TASK)) {
                    getOrder();
                }
            }
        };
        mAdapter = new OrderAdapter(this, listOrder);
        mRecyclerView.setAdapter(mAdapter);
        getOrder();
    }

    private void getOrder() {
        if (ShipperApplication.mService != null)
            if (ShipperApplication.mService.getListTask() != null) {
                for (Task task : ShipperApplication.mService.getListTask()) {
                    if (task.get_LadingID() == landingId) {
                        listOrder = task.getOrder();
                        mAdapter.setMyArray(listOrder);
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void registerReceiver() {
        registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(TrackerService.UPDATE_TASK));
    }
}
