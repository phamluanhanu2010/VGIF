package vtc.game.app.vcoin.vtcpay.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.model.GameTypeModel;
import vtc.game.app.vcoin.vtcpay.utils.Const;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtGameType extends ArrayAdapter<String> {
    Context mContext;
    GameTypeModel gameTypeModel;
    private AdtGameList mAdapter;

    public AdtGameType(Context context, int resource, GameTypeModel gameTypeModel) {
        super(context, resource, gameTypeModel.getArrLstGameType());
        this.mContext = context;
        this.gameTypeModel = gameTypeModel;
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
        final String item = getItem(position);
        if (rowView == null) {
            LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflate.inflate(R.layout.tmp_item_game_type, null);


            viewHolder = new ViewHolder();
            viewHolder.tvGameTypeTitle = (TextView) rowView.findViewById(R.id.tv_Game_Type_Title);

            viewHolder.recyclerViewGameItem = (RecyclerView) rowView.findViewById(R.id.rclView_Item);

            viewHolder.recyclerViewGameItem.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });

            viewHolder.imgLeftArrow = (ImageView) rowView.findViewById(R.id.img_Left_Arrow);
            viewHolder.imgLeftArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.recyclerViewGameItem.smoothScrollBy(-Const.RECYCLERVIEW_SMOOTH, 0);
                }
            });

            viewHolder.imgRightArrow = (ImageView) rowView.findViewById(R.id.img_Right_Arrow);
            viewHolder.imgRightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.recyclerViewGameItem.smoothScrollBy(Const.RECYCLERVIEW_SMOOTH, 0);
                }
            });
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            viewHolder.recyclerViewGameItem.setLayoutManager(layoutManager);
            viewHolder.recyclerViewGameItem.setItemAnimator(new DefaultItemAnimator());
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final List<GameTypeModel> gameList = gameTypeModel.getHmGameType().get(item);
        if (gameList != null) {
            mAdapter = new AdtGameList(gameList, item);
            mAdapter.setOnClickListener(new AdtGameList.onClickListener() {
                @Override
                public void onClickItem(int position) {
                    AppBase.showLog("callback positon:-----" + position);
                }
            });
        }
        viewHolder.recyclerViewGameItem.setAdapter(mAdapter);
        viewHolder.tvGameTypeTitle.setText(item);
        return rowView;
    }

    static class ViewHolder {
        TextView tvGameTypeTitle;
        RecyclerView recyclerViewGameItem;
        ImageView imgRightArrow;
        ImageView imgLeftArrow;
    }
}
