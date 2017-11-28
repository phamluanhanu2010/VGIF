package vtc.game.app.vcoin.vtcpay.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import vtc.game.app.vcoin.vtcpay.R;

/**
 * Created by ThuyChi on 9/23/2016.
 */
public class DlMedia extends Dialog {
    private TextView btn_Library;
    private TextView btn_Capture;
    private TextView btn_Cancel;

    private IActionDialogPhoto onClickDialogPhoto;

    public DlMedia(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dl_media);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setCancelable(false);
        initUI();
    }


    private void initUI() {

        btn_Library = (TextView) this.findViewById(R.id.btn_library);
        btn_Capture = (TextView) this.findViewById(R.id.btn_capture);
        btn_Cancel = (TextView) this.findViewById(R.id.btn_cancel);
        setEvent();
    }

    private void setEvent() {

        btn_Library.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getOnClickDialogPhoto().onClickGetPictureLibrary();
                dismiss();
            }
        });


        btn_Capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getOnClickDialogPhoto().onClickGetCamera();
                dismiss();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public interface IActionDialogPhoto {
        void onClickGetPictureLibrary();

        void onClickGetCamera();
    }

    public IActionDialogPhoto getOnClickDialogPhoto() {
        return onClickDialogPhoto;
    }

    public void setOnClickDialogPhoto(IActionDialogPhoto onClickDialogPhoto) {
        this.onClickDialogPhoto = onClickDialogPhoto;
    }
}
