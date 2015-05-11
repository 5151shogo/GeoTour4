package geotouer4.yoslab.net.myapplication.model;

public class Spot_participant {
    private String mName;
    private float mLatitude;
    private float mLongitude;

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public float setLatitude(float latitude) {
        this.mLatitude = latitude;
        return mLatitude;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public float setLongitude(float longitude) {
        this.mLongitude = longitude;
        return mLongitude;
    }

    public float getLongitude(){
        return mLongitude;
    }
}

