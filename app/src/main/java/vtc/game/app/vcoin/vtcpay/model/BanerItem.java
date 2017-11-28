package vtc.game.app.vcoin.vtcpay.model;

import java.io.Serializable;

/**
 * Created by ThuyChi on 9/21/2016.
 */
public class BanerItem implements Serializable {
    public String imgUrlBaner;
    public String id;
    public String urlBanner;

    public String getUrlBanner() {
        return urlBanner;
    }

    public void setUrlBanner(String urlBanner) {
        this.urlBanner = urlBanner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrlBaner() {
        return imgUrlBaner;
    }

    public void setImgUrlBaner(String imgUrlBaner) {
        this.imgUrlBaner = imgUrlBaner;
    }
}
