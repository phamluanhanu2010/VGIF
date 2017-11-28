package vtc.game.app.vcoin.vtcpay.service;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import vtc.game.app.vcoin.vtcpay.R;
import vtc.game.app.vcoin.vtcpay.common.AppBase;
import vtc.game.app.vcoin.vtcpay.common.VtcString;

/**
 * Created by Thuy Chi on 5/17/16.
 */
public class RegistrationService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);
        try {
            String registrationToken = myID.getToken(this.getResources().getString(R.string.gcm_Sender_Id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            VtcString.DEVICE_TOKEN = registrationToken;
            AppBase.showLog("DEVICE_TOKEN" + "--------- : " + registrationToken);
        } catch (IOException e) {
            e.printStackTrace();
            //  AppCoreBase.showLog("-IOException-------- : " + e.getMessage());
        }
    }
}
