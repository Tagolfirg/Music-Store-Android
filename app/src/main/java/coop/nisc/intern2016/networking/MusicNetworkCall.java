package coop.nisc.intern2016.networking;

import android.support.annotation.NonNull;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class MusicNetworkCall {

    private static final String TAG = "MusicNetworkCall";

    private MusicNetworkCall() {

    }

    @NonNull
    public static String downloadUrl(@NonNull String address) throws unsuccessfulResponseException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                //body is non-null if the response is successful
                //noinspection ConstantConditions
                String responseString = response.body().string();
                Log.v(TAG, "Network call being sent to " + address + " was successful");
                return responseString;
            } else {
                Log.e(TAG, "Network call being sent to " + address + " was unsuccessful");
                throw new unsuccessfulResponseException("Got response code " + response.code());
            }
        } catch (IOException e) {
            throw new unsuccessfulResponseException("Unable to download URL");
        }
    }

    public static final class unsuccessfulResponseException extends RuntimeException {

        private final String localizedMessage;

        unsuccessfulResponseException(String localizedMessage) {
            this.localizedMessage = localizedMessage;
        }

        @Override
        public String getLocalizedMessage() {
            return localizedMessage;
        }
    }

}
