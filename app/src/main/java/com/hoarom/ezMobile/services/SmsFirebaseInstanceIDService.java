package com.hoarom.ezMobile.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class SmsFirebaseInstanceIDService extends FirebaseInstanceIdService {

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        // Get updated InstanceID token.
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.w("InstanceIDService", "onTokenRefresh: " + refreshToken);

        sendRegistrationToServer(refreshToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.w("InstanceIDService", "sendRegistrationToServer: " + token);
        // TODO: Implement this method to send token to your app server.
    }
}
