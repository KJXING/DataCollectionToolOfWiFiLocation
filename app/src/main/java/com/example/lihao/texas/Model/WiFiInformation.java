package com.example.lihao.texas.Model;

import io.realm.RealmObject;

public class WiFiInformation extends RealmObject {

    long timeStamp;

    String macAddress;
    int rrsi;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getRrsi() {
        return rrsi;
    }

    public void setRrsi(int rrsi) {
        this.rrsi = rrsi;
    }

}
