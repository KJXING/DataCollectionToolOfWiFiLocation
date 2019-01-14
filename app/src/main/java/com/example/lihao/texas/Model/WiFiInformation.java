package com.example.lihao.texas.Model;

import io.realm.RealmObject;

public class WiFiInformation extends RealmObject {

    long timeStamp;

    int location_x;
    int location_y;

    String wifiInformation;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLocation_x() {
        return location_x;
    }

    public void setLocation_x(int location_x) {
        this.location_x = location_x;
    }

    public int getLocation_y() {
        return location_y;
    }

    public void setLocation_y(int location_y) {
        this.location_y = location_y;
    }

    public String getWifiInformation() {
        return wifiInformation;
    }

    public void setWifiInformation(String wifiInformation) {
        this.wifiInformation = wifiInformation;
    }
}
