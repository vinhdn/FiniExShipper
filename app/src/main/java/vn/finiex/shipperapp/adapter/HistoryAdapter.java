package vn.finiex.shipperapp.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.model.Order;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

	Context mContext;
	ArrayList<Order> myArray = null;
	
	public HistoryAdapter(Context context, ArrayList<Order> myArray){
		this.mContext = context;
		this.myArray = myArray;
	}
	
	@Override
	public int getItemCount() {
		return myArray.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, int i) {
	  
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View  v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

	public static class ViewHolder extends RecyclerView.ViewHolder{

		CardView cv;
        TextView mLocation;
        ImageView mPhoto1;
        LinearLayout ln;
        
		public ViewHolder(View itemView) {
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.card_view);
			mLocation = (TextView)itemView.findViewById(R.id.txt_location);
			mPhoto1 = (ImageView)itemView.findViewById(R.id.img_multi_picture);
			ln = (LinearLayout)itemView.findViewById(R.id.ln_card_view);
		}				
	}
}