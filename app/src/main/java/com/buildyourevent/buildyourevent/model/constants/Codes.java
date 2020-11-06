package com.buildyourevent.buildyourevent.model.constants;

import android.graphics.Bitmap;

public abstract class Codes
{
    public static final String APP_TAGS = "ALL_APP_TAGS";

   // public static final String AUTH_BASE_URL = "http://www.vretech.com/api/";
    public static final String AUTH_BASE_URL = "https://zozstores.com/api/";

    public static final String CATEGORY_ID =  "category_id";
    public static final Integer ADDRESS_REQ_CODE = 11;
    public static final Integer ADDRESS_SAVED = 12;
    public static final int GPS_SETTINGS_REQ_CODE = 555;
    public static  String USER_ADDRESS = "";
    public static Bitmap BITMAP_PHOTO ;
    public static Bitmap CROP_PHOTO ;

    public static int categoryId =  1;

    public static final String VERIFY_CODE_INTENT = "verify_code_intent";
    public static final String RECOVERY_EMAIL = "recovery_email";

    // Contstants for shared preferences
    public static final String SHARED_PREF_FILE_NAME = "events_on_time";
    public static final String SHARED_PREF_USER_EMAIL = "user_name";
    public static final String SHARED_PREF_USER_PASSWORD = "user_password";
    public static final String SHARED_PREF_LANGUAGE = "user_language";
    public static final String SHARED_PREF_USER_CARTS =  "user_cards";
    public static final String SHARED_PREF_LOGIN_KEY = "login_key";
    public static final String SHARED_PREF_USER_DATA = "user_data";
    public static final String PRODUCT_ID = "product_id";
    public static final String ORDER_REQUEST = "order_request";
    public static final String SHARED_PREF_SUBCATEGORY_DATA = "subcategory_data";
    public static final String SHARED_PREF_IS_SELECTED = "is_selected";
    public static final String PREF_SMALL = "PREF_SMALL";
    public static final String SHARED_PREF_USER_LATLNG = "user_candidates";
    public static final String COUNTRY_ID = "country_id";
    public static final String CARTS_COUNT = "carts_count";

}
