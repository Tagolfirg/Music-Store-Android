package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;
import android.util.Log;

public final class SearchUrl {

    private static final String TAG = "SearchUrl";

    private static final String basicSearchFormat = "https://itunes.apple.com/search?term=";
    private static final String entityFormat = "&entity=album";
    private static final String limitFormat = "&limit=25";

    private static final String illegalCharacterRegex = "[\\\\]{2,}|[#&+,{};:\"\'|]*";
    private static final String illegalCharacterReplacement = "";

    private static final String commaWhiteSpaceRegex = ", *";
    private static final String whiteSpaceRegex = " +";

    private final String searchTerms;

    public SearchUrl(@NonNull String query) {
        this.searchTerms = encodeStringToUrl(query);
    }

    @NonNull
    private static String encodeStringToUrl(@NonNull String query) {
        String encodedString = "";
        int length;
        query = removeSpecialCharacters(query);
        for (String term : query.split(commaWhiteSpaceRegex)) {
            for (String word : term.split(whiteSpaceRegex)) {
                encodedString += word + "+";
            }

            if ((length = encodedString.length()) > 0) {
                encodedString = encodedString.substring(0, length - 1) + ",";
            }
        }
        if ((length = encodedString.length()) > 0) {
            return encodedString.substring(0, length - 1);
        }
        return "";
    }

    private static String removeSpecialCharacters(@NonNull String query) {
        return query.replaceAll(illegalCharacterRegex, illegalCharacterReplacement);
    }

    @NonNull
    public String getFullAddress() {
        Log.d(TAG, basicSearchFormat + searchTerms + entityFormat);
        return basicSearchFormat + searchTerms + entityFormat + limitFormat;
    }

}
