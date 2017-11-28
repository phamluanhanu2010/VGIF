package vtc.game.app.vcoin.vtcpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.model.HistoryItem;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtHistory extends ArrayAdapter<HistoryItem> {
    Context mContext;
    ArrayList<HistoryItem> mLstHistoryItem = new ArrayList<HistoryItem>();

    public AdtHistory(Context context, int resource, List<HistoryItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLstHistoryItem = new ArrayList<HistoryItem>(objects);
    }

    public void initLoadMoreData(List<HistoryItem> objects) {
        this.mLstHistoryItem.addAll(objects);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mLstHistoryItem == null) {
            return 0;
        }
        return mLstHistoryItem.size();
    }

    @Override
    public HistoryItem getItem(int position) {
        if (mLstHistoryItem == null) {
            return null;
        }
        return mLstHistoryItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflate.inflate(R.layout.tmp_item_history, null);

            viewHolder = new ViewHolder();
            viewHolder.tvDate = (TextView) rowView.findViewById(R.id.tv_History_Date);
            viewHolder.tvGame = (TextView) rowView.findViewById(R.id.tv_History_Game);
            viewHolder.tvUser = (TextView) rowView.findViewById(R.id.tv_History_User);
            viewHolder.tvReward = (TextView) rowView.findViewById(R.id.tv_History_Reward);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final HistoryItem item = mLstHistoryItem.get(position);
        viewHolder.tvDate.setText(item.getDate());
        viewHolder.tvGame.setText(item.getGame());
        viewHolder.tvUser.setText(item.getUser());
        viewHolder.tvReward.setText(item.getReward());

        return rowView;

    }

    static class ViewHolder {
        TextView tvDate;
        TextView tvGame;
        TextView tvUser;
        TextView tvReward;
    }
}
