package coop.nisc.intern2016.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import coop.nisc.intern2016.R;

public enum Explicitness {
    EXPLICIT("explicit", R.string.not_explicit),
    NOT_EXPLICIT("notExplicit", R.string.not_explicit),
    CLEANED("cleaned", R.string.cleaned);

    private final String code;
    private final
    @StringRes
    int resourceId;

    Explicitness(String code,
                 @StringRes int resourceId) {
        this.code = code;
        this.resourceId = resourceId;
    }

    @NonNull
    public String getDisplayString(@NonNull Context context) {
        return context.getString(resourceId);
    }

    @NonNull
    public static Explicitness getType(@NonNull String code) {
        for (Explicitness explicitness : Explicitness.values()) {
            if (code.equals(explicitness.code)) {
                return explicitness;
            }
        }
        return EXPLICIT;
    }
}
