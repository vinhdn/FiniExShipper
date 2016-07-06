package vn.finiex.shipperapp.tabs;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.adapter.HistoryAdapter;
import vn.finiex.shipperapp.model.Order;

public class HistoryTab extends Fragment {
	
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
    
    private ArrayList<Order> sceneList = new ArrayList<Order>();
	private Activity activity;
    
    @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            this.activity = activity;
        }
    
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	    
        View v =inflater.inflate(R.layout.tab_list_item,container, false);
        
        mRecyclerView = (RecyclerView) v.findViewById(R.id.lv_data);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        
        
        for(int i = 0; i< 50; i++){
//        	Order tmp = new Order();
//        	tmp.setAddr("Address " + i);
//        	tmp.setDate("date " + i);
//        	tmp.setId(34);
//        	tmp.setNotes("fghjkl");
//        	tmp.setShipfee(89);
//        	tmp.setStatus(1);
//        	tmp.setTotalAmount(789);
//        	sceneList.add(tmp);
        }
        
        // 3. create an adapter 
        HistoryAdapter mAdapter = new HistoryAdapter(activity, sceneList);
        // 4. set adapter
        mRecyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

	
    
}
