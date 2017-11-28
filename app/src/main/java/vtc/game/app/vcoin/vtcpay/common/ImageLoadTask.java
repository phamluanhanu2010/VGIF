package vtc.game.app.vcoin.vtcpay.common;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ThuyChi on 7/26/2016.
 */
public class ImageLoadTask extends AsyncTask<String, String, String> {
    private ImageView mImageView;
    private Context mContext;

    public ImageLoadTask(Context context, ImageView imageView) {
        mImageView = imageView;
        mContext = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (params == null && params.length > 0)
            return "";
        return params[0].toString();
    }

    @Override
    protected void onPostExecute(String result) {
        Glide.with(mContext)
                .load(result)
//                .animate(android.R.anim.slide_in_left)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .transform(new BitmapTransform(mContext, mWidth, mHeight))
//                .override(mWidth, mHeight)
                .centerCrop()
//                .error(R.drawable.sdk_no_image)// set if do not have image url
//                .placeholder(android.R.drawable.bottom_bar)
                .into(mImageView);

    }
}
