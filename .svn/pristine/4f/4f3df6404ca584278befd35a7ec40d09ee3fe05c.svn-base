package vtc.game.app.vcoin.vtcpay.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.model.GameTypeModel;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtGameList extends RecyclerView.Adapter<AdtGameList.MyViewHolder> {
    private List<GameTypeModel> gamesList;
    private onClickListener onClickListener;
    public LinearLayout loutContentGame;
    public String typeGameTitile;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gameName;
        private RelativeLayout loutContentGame;
        private ImageView imgGame;
        private ImageView imgCornerNew, imgCornerHot;

        public MyViewHolder(View view) {
            super(view);

            loutContentGame = (RelativeLayout) view.findViewById(R.id.lout_Game_content);
            gameName = (TextView) view.findViewById(R.id.tv_Game_Name);
            imgGame = (ImageView) view.findViewById(R.id.img_Game);
            imgCornerNew = (ImageView) view.findViewById(R.id.img_Corner_New);
            AppBase.setImageWithCornerLocal(R.drawable.ic_corner_green_2, imgCornerNew);
            imgCornerHot = (ImageView) view.findViewById(R.id.img_Corner_Hot);
            AppBase.setImageWithCornerLocal(R.drawable.ic_corner_red_2, imgCornerHot);

        }
    }


    public AdtGameList(List<GameTypeModel> moviesList, String typeTitile) {
        this.gamesList = moviesList;
        this.typeGameTitile = typeTitile;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tmp_item_game, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GameTypeModel gameItem = gamesList.get(position);
        holder.gameName.setText(gameItem.getGameName());
        if (gameItem.getStatus() == Const.GAME_STATUS_NEW) {
            holder.imgCornerNew.setVisibility(View.VISIBLE);
            holder.imgCornerHot.setVisibility(View.GONE);
        } else if (gameItem.getStatus() == Const.GAME_STATUS_HOT) {
            holder.imgCornerNew.setVisibility(View.GONE);
            holder.imgCornerHot.setVisibility(View.VISIBLE);
        } else if (gameItem.getStatus() == Const.GAME_STATUS_NORMAL) {
            holder.imgCornerNew.setVisibility(View.GONE);
            holder.imgCornerHot.setVisibility(View.GONE);
        } else {
            holder.imgCornerNew.setVisibility(View.GONE);
            holder.imgCornerHot.setVisibility(View.GONE);
        }
        AppBase.setImageWithCorner(gameItem.getPictureGame(), holder.imgGame);


        holder.loutContentGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppBase.showLog("===========================:----" + typeGameTitile);
//                AppBase.showLog("Game Name:----" + gameItem.getGameName());
//                AppBase.showLog("Game Type:----" + gameItem.getTypeGameTitile());
//                AppBase.showLog("Game ID:----" + gameItem.getId());
//                AppBase.showLog("Game Status:----" + gameItem.getStatus());

                Bundle bundleData = new Bundle();
                bundleData.putString(ConstParam.BUNDLE_KEY_GAME_DETAIL, gameItem.getId());

                AppBase.callNewFragment(Const.UI_GAME_INFO, bundleData, true);

                onClickListener.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public interface onClickListener {
        void onClickItem(int position);
    }

    public AdtGameList.onClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(AdtGameList.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
