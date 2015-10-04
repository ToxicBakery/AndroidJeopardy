package com.doctoredapps.androidjeopardy.connection;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AppIdentifier;
import com.google.android.gms.nearby.connection.AppMetadata;
import com.google.android.gms.nearby.connection.Connections;

import java.util.Collections;
import java.util.List;

@MainThread
public class NearbyConnection {

    static NearbyConnection instance;

    private final GoogleApiClient apiClient;
    private final Context context;
    private final INearbyBinder nearbyBinder;

    private boolean isHost;

    NearbyConnection(@NonNull Context context) {
        this(context, null);
    }

    NearbyConnection(@NonNull Context context,
                     @Nullable INearbyBinder binder) {

        context = context.getApplicationContext();
        this.context = context;
        nearbyBinder = binder == null ? new NearbyBinderImpl() : binder;

        apiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(nearbyBinder)
                .addOnConnectionFailedListener(nearbyBinder)
                .addApi(Nearby.CONNECTIONS_API)
                .build();
    }

    public static NearbyConnection getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (NearbyConnection.class) {
                if (instance == null) {
                    instance = new NearbyConnection(context);
                }
            }
        }

        return instance;
    }

    public boolean start(boolean asHost) {
        if (isHost != asHost) {
            isHost = asHost;

            if (isHost) {
                nearbyBinder.startAdvertising();
            } else {
                nearbyBinder.stopAdvertising();
            }
        }

        if (LanUtil.isOnLocalNetwork(context)) {
            if (!isStarted()) {
                apiClient.connect();
            }

            return true;
        }

        stop();
        return false;
    }

    public void stop() {
        isHost = false;

        if (isStarted()) {
            Nearby.Connections.stopAllEndpoints(apiClient);
            apiClient.disconnect();
        }
    }

    public boolean isStarted() {
        return apiClient.isConnecting()
                || apiClient.isConnected();
    }

    public boolean isHost() {
        return isHost;
    }

    public GoogleApiClient getNearbyApiClient() {
        return apiClient;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Send an reliable message.
     *
     * @param endpoint endpoint target
     * @param message payload
     * @return true if message passed to nearby layer
     */
    public boolean sendReliableMessage(@NonNull String endpoint,
                                       @NonNull byte[] message) {

        if (apiClient.isConnected()) {
            Nearby.Connections.sendReliableMessage(apiClient, endpoint, message);
            return true;
        }

        return false;
    }

    /**
     * Send an reliable message.
     *
     * @param endpoints list of endpoint targets
     * @param message payload
     * @return true if message passed to nearby layer
     */
    public boolean sendReliableMessage(@NonNull List<String> endpoints,
                                       @NonNull byte[] message) {

        if (apiClient.isConnected()) {
            Nearby.Connections.sendReliableMessage(apiClient, endpoints, message);
            return true;
        }

        return false;
    }

    /**
     * Send an unreliable message.
     *
     * @param endpoint endpoint target
     * @param message payload
     * @return true if message passed to nearby layer
     */
    public boolean sendUnreliableMessage(@NonNull String endpoint,
                                       @NonNull byte[] message) {

        if (apiClient.isConnected()) {
            Nearby.Connections.sendUnreliableMessage(apiClient, endpoint, message);
            return true;
        }

        return false;
    }

    /**
     * Send an unreliable message.
     *
     * @param endpoints list of endpoint targets
     * @param message payload
     * @return true if message passed to nearby layer
     */
    public boolean sendUnreliableMessage(@NonNull List<String> endpoints,
                                       @NonNull byte[] message) {

        if (apiClient.isConnected()) {
            Nearby.Connections.sendUnreliableMessage(apiClient, endpoints, message);
            return true;
        }

        return false;
    }

    public interface INearbyBinder extends GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            Connections.ConnectionRequestListener,
            Connections.MessageListener,
            Connections.EndpointDiscoveryListener {

        void startAdvertising();

        void stopAdvertising();

    }

    class NearbyBinderImpl implements INearbyBinder {

        private static final String TAG = "NearbyBinder";
        private static final long NO_TIMEOUT = 0L;

        @Override
        public void onConnected(Bundle bundle) {
            if (isHost()) {
                startAdvertising();
            }
        }

        @Override
        public void onConnectionSuspended(int cause) {
        }

        @Override
        public void onConnectionRequest(String remoteEndpointId,
                                        String remoteDeviceId,
                                        String remoteEndpointName,
                                        byte[] payload) {
        }

        @Override
        public void onEndpointFound(String endpointId,
                                    String deviceId,
                                    String serviceId,
                                    String endpointName) {
        }

        @Override
        public void onEndpointLost(String endpointId) {
        }

        @Override
        public void onMessageReceived(String endpointId,
                                      byte[] payload,
                                      boolean isReliable) {
        }

        @Override
        public void onDisconnected(String endpointId) {
        }

        @Override
        public void onConnectionFailed(ConnectionResult result) {
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public void startAdvertising() {
            if (!getNearbyApiClient().isConnected()) {
                return;
            }

            AppIdentifier appIdentifier = new AppIdentifier(getContext().getPackageName());
            List<AppIdentifier> appIdentifiers = Collections.singletonList(appIdentifier);
            AppMetadata appMetadata = new AppMetadata(appIdentifiers);

            // Name is set null allowing nearby to pick a name. This could be made configurable.
            String name = null;

            PendingResult<Connections.StartAdvertisingResult> pendingResult = Nearby.Connections.startAdvertising(
                    getNearbyApiClient(),
                    name,
                    appMetadata,
                    NO_TIMEOUT,
                    this);

            pendingResult.setResultCallback(new ResultCallback<Connections.StartAdvertisingResult>() {
                @Override
                public void onResult(Connections.StartAdvertisingResult result) {
                    // Something bad happened, tear down
                    if (!result.getStatus().isSuccess()) {
                        Log.e(TAG, "Failed to start advertising. Tearing down.");
                        stop();
                    }
                }
            });
        }

        @Override
        public void stopAdvertising() {
            if (getNearbyApiClient().isConnected()) {
                Nearby.Connections.stopAdvertising(getNearbyApiClient());
            }
        }

    }

}
