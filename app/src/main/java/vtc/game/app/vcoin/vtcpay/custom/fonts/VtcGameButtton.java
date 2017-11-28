package vtc.game.app.vcoin.vtcpay.custom.fonts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by tieut on 7/5/2016.
 * @author Thuytv
 */
public class VtcGameButtton extends Button {
    public VtcGameButtton(Context context) {
        super(context);
    }

    public VtcGameButtton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }

    public VtcGameButtton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VtcGameButtton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }
}
