package vtc.game.app.vcoin.vtcpay.model;

import java.io.Serializable;

/**
 * Created by ThuyChi on 9/21/2016.
 */
public class GiftcodeItem implements Serializable {
    public String titileGiftcode;
    public String imgUrl;
    public String giftcodeID;

    public String getGiftcodeID() {
        return giftcodeID;
    }

    public void setGiftcodeID(String giftcodeID) {
        this.giftcodeID = giftcodeID;
    }

    public String getTitileGiftcode() {
        return titileGiftcode;
    }

    public void setTitileGiftcode(String titileGiftcode) {
        this.titileGiftcode = titileGiftcode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
