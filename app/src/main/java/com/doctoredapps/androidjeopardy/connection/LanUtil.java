package com.doctoredapps.androidjeopardy.connection;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

class LanUtil {

    private static int[] NETWORK_TYPES = {
            ConnectivityManager.TYPE_WIFI,
            ConnectivityManager.TYPE_ETHERNET
    };

    /**
     * Determine if the device is connected to a WiFi or Ethernet network.
     *
     * @param context application context
     * @return true if device is connected to a local network
     */
    static boolean isOnLocalNetwork(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return isOnLocalNetwork(connectivityManager);
        } else {
            return isOnLocalNetworkCompat(connectivityManager);
        }
    }

    /**
     * Determine if the device is connected to a WiFi or Ethernet network.
     *
     * @param connectivityManager system connectivity manager instance
     * @return true if device is connected to a local network
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static boolean isOnLocalNetwork(@NonNull ConnectivityManager connectivityManager) {
        Network[] allNetworks = connectivityManager.getAllNetworks();
        for (Network network : allNetworks) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            for (int type : NETWORK_TYPES) {
                if (networkInfo.getType() == type
                        && networkInfo.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determine if the device is connected to a WiFi or Ethernet network.
     *
     * @param connectivityManager system connectivity manager instance
     * @return true if device is connected to a local network
     */
    @SuppressWarnings("deprecation")
    static boolean isOnLocalNetworkCompat(@NonNull ConnectivityManager connectivityManager) {
        for (int networkType : NETWORK_TYPES) {
            NetworkInfo info = connectivityManager.getNetworkInfo(networkType);
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
        }

        return false;
    }

}
