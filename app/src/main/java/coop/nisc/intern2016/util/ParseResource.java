package coop.nisc.intern2016.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParseResource {

    private ParseResource() {
    }

    @NonNull
    public static String parseAsset(@NonNull Context context,
                                    @NonNull String assetName) {

        try {
            String line;

            final StringBuilder stringBuilder = new StringBuilder();
            final BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(context.getAssets().open(assetName)));

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            return stringBuilder.toString();
        } catch (IOException e) {
            Log.e("IO_EXCEPTION", "File \"" + assetName + "\" not found");
            return "";
        }
    }
}
