package vtc.game.app.vcoin.vtcpay.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thuy Chi on 7/7/16.
 */
public enum TypeActionConnection {

    TYPE_ACTION(0),

    TYPE_ACTION_GET_LIST_GAME(1),
    TYPE_ACTION_GET_GAME_INFO(2),
    TYPE_ACTION_GET_GAME_LIST_NEWS(3),
    TYPE_ACTION_GET_GAME_LIST_GIFTCODE(4),
    TYPE_ACTION_GET_EVENT_INFO_DETAIL(5),
    TYPE_ACTION_GET_LIST_NOTI(6),
    TYPE_ACTION_FIND_EVENT_BY_TITILE(7),
    TYPE_ACTION_FIND_GAME_BY_NAME(8),
    TYPE_ACTION_CREATE_ACCOUNT(9),
    TYPE_ACTION_GET_REWARD_ALL(10),
    TYPE_ACTION_GET_REWARD_ACCOUNT(11),
    TYPE_ACTION_READ_NOTI(12),
    TYPE_ACTION_DELETE_NOTI(13),
    TYPE_ACTION_GET_SPINNING_INFO(14),
    TYPE_ACTION_GET_CHOOSE_GAME(15),
    TYPE_ACTION_GET_SPINNING_RESURL(16),
    TYPE_ACTION_SHARE_MISION(17),
    TYPE_ACTION_DOWNLOAD_MISION(18),
    TYPE_ACTION_GET_LIST_ADVERTISING(19),
    TYPE_ACTION_SIGNOUT(20),
    TYPE_ACTION_GET_DOWNLOAD_BTN_STT(21),
    TYPE_ACTION_GET_ACCOUNT_DETAIL(22),
    TYPE_ACTION_GET_SECRET_QUESTION(23),
    TYPE_ACTION_GET_CITY(24),
    TYPE_ACTION_GET_DISTRICT(25),
    TYPE_ACTION_GET_WARD(26),
    TYPE_ACTION_POST_UPDATE_EMAIL(27),
    TYPE_ACTION_UPDATE_PROFILE(28),
    TYPE_ACTION_INSERT_PROFILE(29),
    TYPE_ACTION_REMOVE_PHONE(30),
    TYPE_ACTION_VERIFY_PHONE_FIRST_STEP(31),
    TYPE_ACTION_VERIFY_PHONE_SECOND_STEP(32),
    TYPE_ACTION_EDIT_PHONE_FIRST_STEP(33),
    TYPE_ACTION_EDIT_PHONE_SECOND_STEP(34),
    TYPE_ACTION_UPDATE_PASSWORD(35),
    TYPE_ACTION_UPDATE_QUESTION_BY_OTP(36),
    TYPE_ACTION_GET_SMS_PLUS_INFO(37),
    TYPE_ACTION_UPDATE_SMS_PLUS(38),
    TYPE_ACTION_GET_LOGIN_SECURITY_INFO(39),
    TYPE_ACTION_ON_LOGIN_SECURITY_INFO(40),
    TYPE_ACTION_OFF_LOGIN_SECURITY_INFO(41),
    TYPE_ACTION_GET_FREEZE_INFO(42),
    TYPE_ACTION_FREEZE(43),
    TYPE_ACTION_UNFREEZE(44);

    private static final Map<Integer, TypeActionConnection> typesByValue = new HashMap<>();

    private final int valuesConnectionType;

    TypeActionConnection(int value) {
        this.valuesConnectionType = value;
    }

    public int getValuesTypeDialog() {
        return valuesConnectionType;
    }

    static {
        for (TypeActionConnection type : TypeActionConnection.values()) {
            typesByValue.put(type.valuesConnectionType, type);
        }
    }

    public static TypeActionConnection forValue(int value) {
        TypeActionConnection type = typesByValue.get(value);
        if (type == null)
            return TypeActionConnection.TYPE_ACTION;
        return typesByValue.get(value);
    }
}
