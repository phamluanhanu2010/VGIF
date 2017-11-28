package vtc.game.app.vcoin.vtcpay.model;

import java.io.Serializable;

/**
 * Created by ThuyChi on 9/21/2016.
 */
public class NewsItem implements Serializable {
    public String newsTitile;
    public String imgUrl;
    public String newsID;
    public int viewCount;
    public String newsPublishDate;
    public String newsURL;

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getNewsPublishDate() {
        return newsPublishDate;
    }

    public void setNewsPublishDate(String newsPublishDate) {
        this.newsPublishDate = newsPublishDate;
    }

    public String getNewsTitile() {
        return newsTitile;
    }

    public void setNewsTitile(String newsTitile) {
        this.newsTitile = newsTitile;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
