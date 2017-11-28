package vtc.game.app.vcoin.vtcpay.interfaces;


import vtc.game.app.vcoin.vtcpay.enums.TypeActionConnection;

/**
 * Created by Thuy Chi on 7/7/16.
 */
public interface RequestListener {

//    void onPostExecuteStart(TypeActionConnection keyType, String sApi);

    void onPostExecuteError(TypeActionConnection keyType, String errorcode, String msg, String info, String reponseFullData);

    void onPostExecuteSuccess(TypeActionConnection keyType, String message, String info, String reponseFullData);

    String API_CONNECTION_GET_LIST_GAME = "/vgift/game/listGame";
    String API_CONNECTION_GET_INFO_GAME = "/vgift/game/getGame";
    String API_CONNECTION_CREATE_ACCOUNT = "/vgift/user/createuser";
    String API_CONNECTION_GET_LIST_NOTI = "/vgift/noti/getListNoti";
    String API_CONNECTION_GET_LIST_REWARD_ALL = "/vgift/reward/historyall";
    String API_CONNECTION_GET_LIST_REWARD_ACCOUNT = "/vgift/reward/historyaccount";
    String API_CONNECTION_FIND_EVENT = "/vgift/event/findListEvents";
    String API_CONNECTION_FIND_GAME = "/vgift/game/findGame";
    String API_CONNECTION_GET_EVENT_DETAIL = "/vgift/event/getEvent";
    String API_CONNECTION_GET_LIST_GIFTCODE = "/vgift/event/listEvent";
    String API_CONNECTION_READ_NOTI = "/vgift/noti/readNotification";
    String API_CONNECTION_DELETE_NOTI = "/vgift/noti/deleteNotification";
    String API_CONNECTION_GET_SPINNING_INFO = "/vgift/rotation/getRotation";
    String API_CONNECTION_GET_CHOOSE_GAME = "/vgift/rotation/chooseGame";
    String API_CONNECTION_SHARE_MISSION = "/vgift/event/processshare";
    String API_CONNECTION_DOWNLOAD_MISSION = "/vgift/event/processdown";
    String API_CONNECTION_GET_SPINNING_RESULT = "/vgift/rotation/processrotate";
    String API_CONNECTION_GET_ADS_LIST = "/vgift/adsbanner/listadvertisement";
    //    String API_CONNECTION_GET_DOWNLOAD_BTN_STT = "/vgift/game/hidebuttondown";
    String API_CONNECTION_GET_DOWNLOAD_BTN_STT = "/vgift/game/hiddenbyversion";
    //    String API_CONNECTION_GET_LIST_NEWS = "https://vtcgame.vn/Handler/news/?cateId=6&page=1&pageSize=6";
    String API_CONNECTION_GET_LIST_NEWS = "https://vtcgame.vn/Handler/news/?cateId=6&page=";
    String API_CONNECTION_SIGN_OUT = "/vgift/user/logout";
    String API_CONNECTION_GET_DETAIL = "/vgift/user/detail";
    String API_CONNECTION_GET_SECRET_QUESTION = "/vgift/utils/questions";
    String API_CONNECTION_GET_CITY = "/vgift/utils/locations";
    String API_CONNECTION_GET_DISTRICT = "/vgift/utils/districts";
    String API_CONNECTION_GET_WARD = "/vgift/utils/wards";
    String API_CONNECTION_UPDATE_EMAIL = "/vgift/email/previousupdate";
    String API_CONNECTION_UPDATE_PROFILE = "/vgift/update/profile";
    String API_CONNECTION_INSERT_PROFILE = "/vgift/profile/previousinsert";
    String API_CONNECTION_REMOVE_PHONE = "/vgift/unverify/mobile";
    String API_CONNECTION_VERIFY_PHONE_FIRST_STEP = "/vgift/mobile/previousverify";
    String API_CONNECTION_VERIFY_PHONE_SECOND_STEP = "/vgift/mobile/verify";
    String API_CONNECTION_EDIT_PHONE_FIRST_STEP = "/vgift/mobile/previousupdate";
    String API_CONNECTION_EDIT_PHONE_SECOND_STEP = "/vgift/mobile/update";
    String API_CONNECTION_UPDATE_PASSWORD = "/vgift/update/password";
    String API_CONNECTION_UPDATE_QUESTION_BY_OTP = "/vgift/update/questionbymobile";
    String API_CONNECTION_GET_SMS_PLUS_INFO = "/vgift/smsplus/list";
    String API_CONNECTION_UPDATE_SMS_PLUS = "/vgift/smsplus/update";
    String API_CONNECTION_GET_LOGIN_SECURITY_INFO = "/vgift/secure/info";
    String API_CONNECTION_ON_LOGIN_SECURITY_INFO = "/vgift/secure/authen";
    String API_CONNECTION_GET_FREEZE_INFO = "/vgift/balance/all";
    String API_CONNECTION_FREEZE = "/vgift/vcoin/freeze";
    String API_CONNECTION_UNFREEZE = "/vgift/vcoin/unfreeze";


}
