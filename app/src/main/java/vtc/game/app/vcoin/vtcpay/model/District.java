package vtc.game.app.vcoin.vtcpay.model;

/**
 * Created by LuanPV on 3/9/2017.
 */

public class District {
    private int districtId;
    private String districtName;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public District() {
    }

    public District(String districtName, int districtId) {
        this.districtId = districtId;
        this.districtName = districtName;
    }

    public String getLocationName() {
        return districtName;
    }

    public void setLocationName(String locationName) {
        this.districtName = locationName;
    }


    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return locationName
     */
    @Override
    public String toString() {
        return districtName;
    }


}


