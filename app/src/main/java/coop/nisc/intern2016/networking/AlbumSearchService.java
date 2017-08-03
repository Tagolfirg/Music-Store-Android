package coop.nisc.intern2016.networking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class AlbumSearchService {

    private static final String TAG = "AlbumSearchService";

    private AlbumSearchService() {

    }

    @NonNull
    public static String downloadUrl(@NonNull String address) throws AlbumSearchException {

        InputStream inputStream = null;
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int respondCode = connection.getResponseCode();
            Log.d(TAG, "downloadUrl: " + respondCode);

            inputStream = connection.getInputStream();
        } catch (MalformedURLException e) {
            Log.e(TAG, "getAlbumSearchJson: Poorly formed URL", e);
        } catch (Exception e) {
            throw new AlbumSearchException("Something went wrong.\nPlease check your settings and trying again.");
        }
        return readStream(inputStream);
    }

    @NonNull
    private static String readStream(@Nullable InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static final class AlbumSearchException extends IOException {

        private String localizedMessage;

        private AlbumSearchException(String localizedMessage) {
            this.localizedMessage = localizedMessage;
        }

        @Override
        public String getLocalizedMessage() {
            return localizedMessage;
        }
    }

}
