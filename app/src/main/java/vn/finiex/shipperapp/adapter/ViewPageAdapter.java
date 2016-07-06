package vn.finiex.shipperapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.finiex.shipperapp.tabs.AdminInfoTab;
import vn.finiex.shipperapp.tabs.CurrentOrderTab;
import vn.finiex.shipperapp.tabs.HistoryTab;

public class ViewPageAdapter extends FragmentStatePagerAdapter{

	CharSequence Titles[];
    int NumbOfTabs; 

    public ViewPageAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }


	@Override
	public Fragment getItem(int position) {
		 if(position == 0) // if the position is 0 we are returning the First tab
	        {
	            CurrentOrderTab tabmap = new CurrentOrderTab();
	            return tabmap;
	        }
	        else if(position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
	        {
	            HistoryTab tabCollectPicture = new HistoryTab();
	            return tabCollectPicture;
	        }else /*if (position == 2)*/{
	        	AdminInfoTab adminInfoTab = new AdminInfoTab();
	            return adminInfoTab;
	        }
	}

	@Override
	public int getCount() {
		return NumbOfTabs;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

}
