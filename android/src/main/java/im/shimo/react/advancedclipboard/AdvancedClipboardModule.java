package im.shimo.react.advancedclipboard;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private static final String CHANGE_COUNT_KEY = "changeCountKey";

    private ClipboardManager mClipboardManager = null;
    private SharedPreferences mSharedPreferences = null;

    @Nullable
    private ClipboardManager.OnPrimaryClipChangedListener mClipChangedListener = null;

    public AdvancedClipboardModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "AdvancedClipboard";
    }

    @Override
    public void initialize() {
        Thread thread = new Thread(() -> changeCount = getSharedPreferences().getInt(CHANGE_COUNT_KEY, 0));
        thread.start();
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        if (mClipChangedListener != null) {
            getClipboardManager().removePrimaryClipChangedListener(mClipChangedListener);
        }
    }

    @ReactMethod
    public void init(Promise promise) {
        if (mClipChangedListener == null) {
            mClipChangedListener = () -> {
                changeCount++;
                Thread thread = new Thread(() -> {
                    SharedPreferences.Editor editor = getSharedPreferences().edit();
                    editor.putInt(CHANGE_COUNT_KEY, changeCount);
                    editor.apply();
                });
                thread.start();
            };
            getClipboardManager().addPrimaryClipChangedListener(mClipChangedListener);
        }
        promise.resolve(null);
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

    private SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = getContext().getApplicationContext().getSharedPreferences("react-native-advanced-clipboard", Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    private ClipboardManager getClipboardManager() {
        if (mClipboardManager == null) {
            mClipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        }
        return mClipboardManager;
    }
}
