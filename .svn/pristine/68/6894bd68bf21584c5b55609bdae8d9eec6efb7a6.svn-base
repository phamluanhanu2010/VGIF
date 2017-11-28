package vtc.game.app.vcoin.vtcpay.model;

/**
 * Created by LuanPV on 3/9/2017.
 */

public class City {
    private int locationId;
    private String locationName;

    public City() {
    }

    public City(String locationName, int locationId) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationID() {
        return locationId;
    }

    public void setLocationID(int locationID) {
        this.locationId = locationID;
    }

    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return locationName
     */
    @Override
    public String toString() {
        return locationName;
    }


}


