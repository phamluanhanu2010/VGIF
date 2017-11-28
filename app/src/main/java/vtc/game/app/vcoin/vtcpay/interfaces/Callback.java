package vtc.game.app.vcoin.vtcpay.interfaces;

/**
 * Created by Thuy Chi on 9/16/16.
 */
public interface Callback {
    <T> void onHandlerCallBack(int key, T... t);
}
