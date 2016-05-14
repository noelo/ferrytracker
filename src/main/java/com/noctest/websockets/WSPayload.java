package com.noctest.websockets;

import com.google.transit.realtime.GtfsRealtime;

/**
 * Created by admin on 8/05/2016.
 */
public class WSPayload {

    private String vehiclelName;
    private float latitude;
    private float longtitude;
    private float bearing;
    private float speedms;


    public WSPayload(String vehiclelName, float latitude, float longtitude, float speedms, float bearing) {
        this.vehiclelName = vehiclelName;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.speedms = speedms;
        this.bearing = bearing;
    }

    public String getVehiclelName() {
        return vehiclelName;
    }


    public float getLatitude() {
        return latitude;
    }


    public float getLongtitude() {
        return longtitude;
    }


    public float getBearing() {
        return bearing;
    }


    public float getSpeedms() {
        return speedms;
    }

    @Override
    public String toString() {
        return "WSPayload{" +
                "bearing=" + bearing +
                ", vehiclelName='" + vehiclelName + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longtitude +
                ", speedms=" + speedms +
                '}';
    }
}
