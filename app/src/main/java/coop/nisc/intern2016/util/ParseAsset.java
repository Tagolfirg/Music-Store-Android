package coop.nisc.intern2016.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ParseAsset {

    private ParseAsset() {
    }

    @NonNull
    public static String parse(@NonNull Context context,
                               @NonNull String assetName) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;

            final BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(context.getAssets().open(assetName)));

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

        } catch (IOException e) {
            Log.e("IO_EXCEPTION", "File \"" + assetName + "\" not found");
        }
        return stringBuilder.toString();
    }

}
