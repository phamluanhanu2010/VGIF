package vtc.game.app.vcoin.vtcpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.model.GiftcodeItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtGiftcode extends ArrayAdapter<GiftcodeItem> {
    Context mContext;
    ArrayList<GiftcodeItem> mLstGiftcodeItem = new ArrayList<GiftcodeItem>();
    private onClickItem onClickItem;

    public AdtGiftcode(Context context, int resource, List<GiftcodeItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLstGiftcodeItem = new ArrayList<GiftcodeItem>(objects);
    }

    @Override
    public int getCount() {
        if (mLstGiftcodeItem == null) {
            return 0;
        }
        return mLstGiftcodeItem.size();
    }

    @Override
    public GiftcodeItem getItem(int position) {
        if (mLstGiftcodeItem == null) {
            return null;
        }
        return mLstGiftcodeItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return initView(position, convertView, parent);
    }

    public View initView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        final ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflate.inflate(R.layout.tmp_item_giftcode, null);

            viewHolder = new ViewHolder();

            viewHolder.titileGiftcode = (TextView) rowView.findViewById(R.id.tv_Giftcode_Titile);
            viewHolder.imgGiftcode = (ImageView) rowView.findViewById(R.id.img_Giftcode);
            viewHolder.imgGiftcode.getLayoutParams().height = (int) (Const.DISPLAY_WIDTH * 9 / 16);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBase.showLog("Giftcode:" + position);
                getOnClickItem().onClickItem(position, getItem(position).getGiftcodeID());
            }
        });

        final GiftcodeItem item = getItem(position);

        viewHolder.titileGiftcode.setText(item.getTitileGiftcode());
        AppBase.setImageWithUrl(item.getImgUrl(), viewHolder.imgGiftcode);

        return rowView;

    }

    static class ViewHolder {
        TextView titileGiftcode;
        ImageView imgGiftcode;


    }

    public interface onClickItem {
        void onClickItem(int position, String eventId);
    }

    public AdtGiftcode.onClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(AdtGiftcode.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }
}
