package geotouer4.yoslab.net.myapplication.model;

public class Spot_lati {
    private int mId;
    private float mLatitude;
    private float mLongitude;

    public void setId(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
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

