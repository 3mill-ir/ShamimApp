package ir.hezareh.park.DataLoading;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import ir.hezareh.park.SignIn_SignUp;


public class SharedPreferencesManager {
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final boolean KEY_SHOW = false;
    private static final String KEY_SPLASH = "splash_show";

    private static final String KEY_POLL = "pollParticipated";
    private static final String KEY_DIALOG = "dialog_show";
    // Sharedpref file name
    private static final String PREF_NAME = "SharedPrefs";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SharedPreferencesManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean ParticipatedInPoll(boolean value) {
        if (!pref.getBoolean(KEY_POLL, false)) {
            editor.putBoolean(KEY_POLL, value);
            editor.commit();
            return true;
        }
        return false;
    }

    //it sets the flag that is used for showing splash screen for once each time app starts from scratch
    public void setShowSplashForOnce(boolean flag) {
        // Storing phone in pref
        editor.putBoolean(KEY_SPLASH, flag);
        // commit changes
        editor.commit();
    }

    //getting the value that is set
    public boolean showSplashForOnce() {
        return pref.getBoolean(KEY_SPLASH, true);
    }

    public void setShowDialogForOnce(boolean flag) {
        // Storing phone in pref
        editor.putBoolean(KEY_DIALOG, flag);
        // commit changes
        editor.commit();
    }

    //getting the value that is set
    public boolean showDialogForOnce() {
        return pref.getBoolean(KEY_DIALOG, true);
    }


    /**
     * Create login session
     */
    public void createLoginSession(String name, String email) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignIn_SignUp.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SignIn_SignUp.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
