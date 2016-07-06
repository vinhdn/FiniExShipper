package vn.finiex.shipperapp.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.finiex.shipperapp.R;
import vn.finiex.shipperapp.model.UserInfo;

public class DrawerLeftAdapter extends
		RecyclerView.Adapter<DrawerLeftAdapter.ViewHolder> {
	private static final int TYPE_HEADER = 0;
	private static final int TYPE_ITEM = 1;
	private String mNavTitles[];
	private int mIcons[];

	private String name;
	private String email;
	private Bitmap avatar;
	private OnClickListener listener;

	private UserInfo userInfo;
	
	public static class ViewHolder extends RecyclerView.ViewHolder {

		int holderID = 1;
		TextView textView;
		ImageView imageView;
		
		ImageView imgAvatar;
		TextView Name;
		TextView payment;
		TextView outomoney;
		TextView money;
		TextView ship;

		public ViewHolder(View itemView, int ViewType) {
			super(itemView);
			if (ViewType == TYPE_ITEM) {
				textView = (TextView) itemView.findViewById(R.id.rowText);
				imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
				holderID = 1;
			} else {
				Name = (TextView) itemView.findViewById(R.id.name);
				payment = (TextView) itemView.findViewById(R.id.txtPayment);
				imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
				outomoney = (TextView) itemView.findViewById(R.id.txtOutoMoney);
				money = (TextView) itemView.findViewById(R.id.txtMoney);
				ship = (TextView) itemView.findViewById(R.id.txtSip);
				holderID = 0;
			}
		}

	}

	public DrawerLeftAdapter(String tiles[], int icons[], String name,
			String email, Bitmap avatar, OnClickListener listener) {
		mNavTitles = tiles;
		mIcons = icons;
		this.name = name;
		this.email = email;
		this.avatar = avatar;
		this.listener = listener;
	}

	@Override
	public int getItemCount() {
		return mNavTitles.length + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (isPositionHeader(position))
			return TYPE_HEADER;
		return TYPE_ITEM;
	}

	private boolean isPositionHeader(int position) {
		return position == 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		if (holder.holderID == 1) {
			holder.textView.setText(mNavTitles[position - 1]);
			holder.imageView.setImageResource(mIcons[position - 1]);
		} else {
			if(this.userInfo!=null){
			holder.Name.setText(userInfo.getFullName());
			holder.payment.setText(userInfo.getAPayment());
			holder.outomoney.setText(userInfo.getAOutoMoney());
			holder.money.setText(userInfo.getPMoney());
			holder.ship.setText(userInfo.getPShip());
			if (avatar != null)
				holder.imgAvatar.setImageBitmap(avatar);
			}
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_ITEM) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_row, parent, false);
			ViewHolder vhItem = new ViewHolder(v, viewType);
			v.setOnClickListener(listener);
			return vhItem;
		} else if (viewType == TYPE_HEADER) {
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.header, parent, false);
			ViewHolder vhHeader = new ViewHolder(v, viewType);
			return vhHeader;
		}
		return null;
	}

	public void setUserInfo(UserInfo info){
		this.userInfo = info;
		notifyDataSetChanged();
	}
	
}
