package vtc.game.app.vcoin.vtcpay.common;


import android.support.v4.app.Fragment;

import vtc.game.app.vcoin.vtcpay.interfaces.Callback;
import vtc.game.app.vcoin.vtcpay.utils.Const;
import vtc.game.app.vcoin.vtcpay.view.account.FMAccountSecurity;
import vtc.game.app.vcoin.vtcpay.view.account.FMChangeAndVerify;
import vtc.game.app.vcoin.vtcpay.view.FMChangePassword;
import vtc.game.app.vcoin.vtcpay.view.FMDetailNews;
import vtc.game.app.vcoin.vtcpay.view.FMEditProfile;
import vtc.game.app.vcoin.vtcpay.view.FMGameInfo;
import vtc.game.app.vcoin.vtcpay.view.FMGiftcode;
import vtc.game.app.vcoin.vtcpay.view.FMGuide;
import vtc.game.app.vcoin.vtcpay.view.FMNews;
import vtc.game.app.vcoin.vtcpay.view.FMNotification;
import vtc.game.app.vcoin.vtcpay.view.FMGameList;
import vtc.game.app.vcoin.vtcpay.view.FMHistory;
import vtc.game.app.vcoin.vtcpay.view.account.FMProfile;
import vtc.game.app.vcoin.vtcpay.view.FMGiftcodeDetail;
import vtc.game.app.vcoin.vtcpay.view.account.FMProfileAccount;
import vtc.game.app.vcoin.vtcpay.view.FMSearch;
import vtc.game.app.vcoin.vtcpay.view.account.FMTabChangePassword;
import vtc.game.app.vcoin.vtcpay.view.account.FMTabFreezeVcoin;
import vtc.game.app.vcoin.vtcpay.view.account.FMTabLoginSecurity;
import vtc.game.app.vcoin.vtcpay.view.account.FMTabSecretQuestion;
import vtc.game.app.vcoin.vtcpay.view.account.FMTabSmsPlus;

/**
 * Created by ThuyChi on 9/16/2016.
 */
public class FragmentFactory {
    public static Fragment getFragmentByKey(int key) {
        switch (key) {
            case Const.UI_GAMELIST:
                return new FMGameList();
            case Const.UI_HISTORY:
                return new FMHistory();
            case Const.UI_NOTI:
                return new FMNotification();
            case Const.UI_GIFTCODE:
                return new FMGiftcode();
            case Const.UI_PROFILE:
                return new FMProfile();
            case Const.UI_NEWS:
                return new FMNews();
            case Const.UI_GAME_INFO:
                return new FMGameInfo();
            case Const.UI_NEWS_DETAIL:
                return new FMDetailNews();
            case Const.UI_SHARE_GIFTCODE:
                return new FMGiftcodeDetail();
            case Const.UI_EDIT_PROFILE:
                return new FMEditProfile();
            case Const.UI_EDIT_PROFILE_CHANGE_PASSWORD:
                return new FMChangePassword();
            case Const.UI_SEARCH:
                return new FMSearch();
            case Const.UI_GUIDE:
                return new FMGuide();
            case Const.UI_PROFILE_ACCOUNT:
                return new FMProfileAccount();
            case Const.UI_ACCOUNT_VERIFY:
                return new FMChangeAndVerify();
            case Const.UI_ACCOUNT_SECURITY:
                return new FMAccountSecurity();
            case Const.UI_TAB_SMS_PLUS:
                return new FMTabSmsPlus();
            case Const.UI_TAB_LOGIN_SECURITY:
                return new FMTabLoginSecurity();
            case Const.UI_TAB_CHANGE_PASSWORD:
                return new FMTabChangePassword();
            case Const.UI_TAB_FREEZE_VCOIN:
                return new FMTabFreezeVcoin();
            case Const.UI_TAB_SECRET_QUESTION:
                return new FMTabSecretQuestion();
            default:
                return null;
        }
    }

    public static Fragment getFragmentCallBackByKey(int key, Callback callback) {
        switch (key) {
            case Const.UI_GAMELIST:
                return new FMGameList(callback);
            case Const.UI_NOTI:
                return new FMNotification(callback);
            case Const.UI_PROFILE:
                return new FMProfile(callback);
            case Const.UI_SHARE_GIFTCODE:
                return new FMGiftcodeDetail(callback);
            case Const.UI_SEARCH:
                return new FMSearch(callback);
            default:
                return null;
        }
    }
}
