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
import vtc.game.app.vcoin.vtcpay.model.GameTypeModel;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtGame extends ArrayAdapter<GameTypeModel> {
    Context mContext;
    ArrayList<GameTypeModel> mLstGameItem = new ArrayList<GameTypeModel>();
    private onClickItem onClickItem;

    public AdtGame(Context context, int resource, List<GameTypeModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLstGameItem = new ArrayList<GameTypeModel>(objects);
    }

    @Override
    public int getCount() {
        if (mLstGameItem == null) {
            return 0;
        }
        return mLstGameItem.size();
    }

    @Override
    public GameTypeModel getItem(int position) {
        if (mLstGameItem == null) {
            return null;
        }
        return mLstGameItem.get(position);
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
            rowView = inflate.inflate(R.layout.tmp_item_game_search, null);

            viewHolder = new ViewHolder();

            viewHolder.gameTitile = (TextView) rowView.findViewById(R.id.tv_Game_Name);
            viewHolder.imgGameAvatar = (ImageView) rowView.findViewById(R.id.img_Game);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBase.showLog("Giftcode:" + position);
                getOnClickItem().onClickItem(position, getItem(position).getId());
            }
        });
        final GameTypeModel item = getItem(position);

        viewHolder.gameTitile.setText(item.getGameName());
        AppBase.setImageWithCorner(item.getPictureGame(), viewHolder.imgGameAvatar);
        return rowView;

    }

    static class ViewHolder {
        TextView gameTitile;
        ImageView imgGameAvatar;


    }

    public interface onClickItem {
        void onClickItem(int position, String eventId);
    }

    public AdtGame.onClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(AdtGame.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }
}
