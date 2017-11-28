package vtc.game.app.vcoin.vtcpay.custom.fonts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

/**
 * Created by LuanPV on 7/5/2016.
 */
public class VtcGameTextView extends android.widget.TextView {
    public VtcGameTextView(Context context) {
        super(context);
    }

    public VtcGameTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }

    public VtcGameTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VtcGameTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setTypeface(FontLoader.readTypeFace(context, attrs));
    }

}
