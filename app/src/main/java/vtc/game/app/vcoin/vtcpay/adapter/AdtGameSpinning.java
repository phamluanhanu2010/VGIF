package vtc.game.app.vcoin.vtcpay.adapter;

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
import vtc.game.app.vcoin.vtcpay.model.GameListItem;

/**
 * Created by ThuyChi on 6/22/2016.
 */
public class AdtGameSpinning extends RecyclerView.Adapter<AdtGameSpinning.MyViewHolder> {
    private List<GameListItem> gamesList;
    private onClickListener onClickListener;
    public LinearLayout loutContentGame;
    public String typeGameTitile;
    private int clickPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView gameName;
        private RelativeLayout loutContentGame;
        private ImageView imgGame;
        private ImageView imgBorder;
        private ImageView imgTranparent;
        private ImageView imgTicked;


        public MyViewHolder(View view) {
            super(view);

            loutContentGame = (RelativeLayout) view.findViewById(R.id.lout_Game_content);
            gameName = (TextView) view.findViewById(R.id.tv_Game_Name);
            imgGame = (ImageView) view.findViewById(R.id.img_Game);
            imgBorder = (ImageView) view.findViewById(R.id.img_Border);
            imgTicked = (ImageView) view.findViewById(R.id.img_Corner_Ticked);
            AppBase.setImageWithCornerLocal(R.drawable.ic_tick_2, imgTicked);
        }
    }


    public AdtGameSpinning(List<GameListItem> gameList) {
        this.gamesList = gameList;

//        AppBase.showLog("=======================" + gameList.get(0).getGameName());

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tmp_item_game_spinning, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        AppBase.showLog("onBindViewHolder");
        final GameListItem gameItem = gamesList.get(position);
        holder.gameName.setText(gameItem.getGameName());
        if (clickPosition != -1) {
            if (position != clickPosition) {
                holder.imgBorder.setVisibility(View.GONE);
                holder.imgTicked.setVisibility(View.GONE);
                holder.imgGame.setAlpha(50);
            } else {

            }
        } else {

        }

        AppBase.setImageWithCorner(gameItem.getPictureUrl(), holder.imgGame);
        holder.loutContentGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickPosition == -1) {
                    notifyDataSetChanged();
                } else {
                    notifyItemChanged(clickPosition);
                }
                holder.imgBorder.setVisibility(View.VISIBLE);
                holder.imgTicked.setVisibility(View.VISIBLE);
                holder.imgGame.setAlpha(255);
                clickPosition = position;
                onClickListener.onClickItem(gameItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public interface onClickListener {
        void onClickItem(GameListItem item);
    }

    public AdtGameSpinning.onClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(AdtGameSpinning.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
