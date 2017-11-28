package vtc.game.app.vcoin.vtcpay.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.model.BanerItem;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.utils.ConstParam;

/**
 * Created by Thuy Chi on 6/1/16.
 */
@SuppressLint("ValidFragment")
public final class FMSlideBanner extends Fragment {
    private BanerItem itemBanner;
    private Context context;

    public FMSlideBanner(Context context, BanerItem item) {
        this.itemBanner = item;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.tmp_sliding_image_item, container, false);

        ImageView imageBanner = (ImageView) viewRoot.findViewById(R.id.img_Baner);
        imageBanner.getLayoutParams().height = (int) (Const.DISPLAY_WIDTH * 9 / 16);
        AppBase.setImageWithUrl(itemBanner.getImgUrlBaner(), imageBanner);

        imageBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBase.showLog("-----------Banner ID:" + itemBanner.getId());
                AppBase.showLog("-----------Banner URL:" + itemBanner.getImgUrlBaner());

                String urlBanner = itemBanner.getUrlBanner();
                if ((urlBanner != null) && (!urlBanner.equals("")) && (!urlBanner.equals("null")) ){
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstParam.BUNDLE_KEY_NEWS_DETAIL, urlBanner);
                    AppBase.callNewFragment(Const.UI_NEWS_DETAIL, bundle, true);
                }
            }
        });


        return viewRoot;
    }
}
