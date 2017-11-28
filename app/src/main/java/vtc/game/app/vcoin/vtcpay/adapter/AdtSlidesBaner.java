package vtc.game.app.vcoin.vtcpay.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

import vtc.game.app.vcoin.vtcpay.model.BanerItem;
import vtc.game.app.vcoin.vtcpay.view.FMSlideBanner;

/**
 * Created by Mr. Ha on 6/1/16.
 */
public class AdtSlidesBaner extends FragmentPagerAdapter implements
        IconPagerAdapter {

    private Context context;

    private List<BanerItem> lsStrings;

    public AdtSlidesBaner(FragmentManager fm, List<BanerItem> lsStrings, Context context) {
        super(fm);
        this.lsStrings = lsStrings;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new FMSlideBanner(context, lsStrings.get(position));
    }

    @Override
    public int getCount() {
        if (lsStrings == null) {
            return 0;
        }
        return lsStrings.size();
    }

    @Override
    public int getIconResId(int index) {
        return index;
    }

}