package com.example.lihao.texas;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * Created by lihao on 25/10/17.
 */

public class WifiFragment extends Fragment {
    private ListView lv;
    private Button buttonScan;
    private EditText numOfAP;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
    private WifiManager wifi;
    private List<ScanResult> results;
    private int size = 0;
    private int apCount = 0;
    private int numOfAPToShow = 0;
    private boolean continueToShow = true;
    private BroadcastReceiver mBroadcastReceiver;
    private static final int REQUEST_ACCESS_LOCATION = 101;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        populateAutoComplete();
        return inflater.inflate(R.layout.frg_wifi, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View myView = this.getView();
        final Context myContext = this.getContext();

        buttonScan = (Button) myView.findViewById(R.id.buttonScan);
        numOfAP = (EditText) myView.findViewById(R.id.numOfAP);
        lv = (ListView) myView.findViewById(R.id.list);

        adapter = new SimpleAdapter(myContext, arraylist, R.layout.row, new String[] { "wifiName",  "capability", "level" }, new int[] { R.id.wifi_name, R.id.wifi_capability, R.id.wifi_level });
        lv.setAdapter(this.adapter);

        wifi = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(myContext, "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }


        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                results = wifi.getScanResults();
                size = results.size();
                Toast.makeText(myContext, "wifi result size "+size, Toast.LENGTH_LONG).show();

                try {
                    apCount = 0;
                    continueToShow = true;
                    numOfAPToShow = Integer.parseInt(numOfAP.getText().toString());
                    List<ScanResult> sortedWifiList = new ArrayList<ScanResult>(results);

                    Comparator<ScanResult> comparator =
                            new Comparator<ScanResult>() {

                                @Override
                                public int compare(ScanResult lhs, ScanResult rhs) {
                                    return (lhs.level < rhs.level ? -1 : (lhs.level == rhs.level ? 0 : 1));
                                }
                            };

                    // Apply Comparator and sort
                    Collections.sort(sortedWifiList, comparator);

                    arraylist.clear();
                    size = size - 1;
                    while (size >= 0 && continueToShow) {

                        HashMap<String, Object> item = new HashMap<String, Object>();
                        item.put("wifiName", sortedWifiList.get(size).SSID + "  " + sortedWifiList.get(size).BSSID);
                        item.put("capability", "Capabilities: " + sortedWifiList.get(size).capabilities);
                        item.put("level", "RSSI: " + sortedWifiList.get(size).level);

                        if (numOfAPToShow > 0 && apCount++ == (numOfAPToShow - 1)) {
                            continueToShow = false;
                        }

                        arraylist.add(item);
                        size--;
                        adapter.notifyDataSetChanged();


                    }

                } catch (Exception e) {
                }
            }
        };
        myContext.registerReceiver(mBroadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wifi.startScan();
                Toast.makeText(myContext, "Scanning....", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void populateAutoComplete() {
        if (!mayRequestLocation()) {
            return;
        }

    }

    private boolean mayRequestLocation() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (this.getContext().checkSelfPermission(ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        if (this.getActivity().
                shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION)) {
            this.getActivity().
                    requestPermissions(new String[]{
                                    ACCESS_COARSE_LOCATION},
                            REQUEST_ACCESS_LOCATION);
        } else {
            this.getActivity().
                    requestPermissions(new String[]{
                                    ACCESS_COARSE_LOCATION},
                            REQUEST_ACCESS_LOCATION);
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_ACCESS_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

}