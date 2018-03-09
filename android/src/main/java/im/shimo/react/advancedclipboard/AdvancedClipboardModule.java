package im.shimo.react.advancedclipboard;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by bell on 2018/3/8.
 */

public class AdvancedClipboardModule extends ContextBaseJavaModule {
    public static int changeCount = 0;
    private static String CHANGE_COUNT_KEY = "changeCountKey";

    private ClipboardManager mClipboardManager = null;
    private SharedPreferences mSharedPreferences = null;

    private ClipboardManager.OnPrimaryClipChangedListener mClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            changeCount++;
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(CHANGE_COUNT_KEY, changeCount);
            editor.apply();
        }
    };

    public AdvancedClipboardModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "AdvancedClipboard";
    }

    @Override
    public void initialize() {
        mSharedPreferences = getContext().getApplicationContext().getSharedPreferences("react-native-advanced-clipboard", Context.MODE_PRIVATE);
        changeCount = mSharedPreferences.getInt(CHANGE_COUNT_KEY, 0);

        getClipboardManager().addPrimaryClipChangedListener(mClipChangedListener);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();

        getClipboardManager().removePrimaryClipChangedListener(mClipChangedListener);
    }

    @ReactMethod
    public void getContent(Promise promise) {
        try {
            ClipboardManager clipboard = getClipboardManager();
            ClipData clipData = clipboard.getPrimaryClip();
            WritableMap result = Arguments.createMap();
            result.putInt("changeCount", changeCount);
            result.putDouble("timestamp", 0);
            if (clipData == null) {
                promise.resolve(result);
            } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    ClipDescription clipDescription = clipData.getDescription();
                    if (clipDescription != null) {
                        result.putDouble("timestamp", clipDescription.getTimestamp());
                    }
                }
                if (clipData.getItemCount() >= 1) {
                    ClipData.Item firstItem = clipboard.getPrimaryClip().getItemAt(0);
                    result.putString("text", "" + firstItem.getText());
                    promise.resolve(result);
                } else {
                    promise.resolve(result);
                }
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void getString(Promise promise) {
        try {
            ClipboardManager clipboard = getClipboardManager();
            ClipData clipData = clipboard.getPrimaryClip();
            if (clipData == null) {
                promise.resolve("");
            } else if (clipData.getItemCount() >= 1) {
                ClipData.Item firstItem = clipboard.getPrimaryClip().getItemAt(0);
                promise.resolve("" + firstItem.getText());
            } else {
                promise.resolve("");
            }
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void setString(String text) {
        ClipboardManager clipboard = getClipboardManager();
        ClipData clipdata = ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clipdata);
    }

    private ClipboardManager getClipboardManager() {
        if (mClipboardManager == null) {
            mClipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        }
        return mClipboardManager;
    }

}
