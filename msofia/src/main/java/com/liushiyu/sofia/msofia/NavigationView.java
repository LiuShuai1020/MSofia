package com.liushiyu.sofia.msofia;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class NavigationView extends View {

    private Display mDisplay;
    private DisplayMetrics mDisplayMetrics;
    private Configuration mConfiguration;
    private int mDefaultBarSize;
    private int mBarSize;

    public NavigationView(Context context) {
        this(context, null, 0);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
        mDisplayMetrics = new DisplayMetrics();
        Resources resources = getResources();
        mConfiguration = resources.getConfiguration();

        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        mDefaultBarSize = resources.getDimensionPixelSize(resourceId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isLandscape()) {
                mDisplay.getRealMetrics(mDisplayMetrics);
                mBarSize = mDisplayMetrics.widthPixels - getDisplayWidth(mDisplay);
                setMeasuredDimension(mBarSize, MeasureSpec.getSize(heightMeasureSpec));
            } else {
                mDisplay.getRealMetrics(mDisplayMetrics);
                mBarSize = mDisplayMetrics.heightPixels - getDisplayHeight(mDisplay);
                if (checkDeviceHasNavigationBar(getContext())) {
                    boolean systemUiVisible[] = isSystemUiVisible(((Activity) getContext()).getWindow());
                    boolean navigation = systemUiVisible[1];
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), navigation ? getDefaultBarSize() : 0);
                } else {
                    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getBarSize());
                }
            }
        } else {
            setMeasuredDimension(0, 0);
        }
    }

    /**
     * 是否有虚拟按键屏幕
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !hasMenuKey && !hasBackKey;
    }

    private static int getDisplayWidth(Display display) {
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    private static int getDisplayHeight(Display display) {
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    /**
     * Get the default height of navigation bar.
     */
    public int getDefaultBarSize() {
        return mDefaultBarSize;
    }

    /**
     * Get the height of navigation bar.
     */
    public int getBarSize() {
        return mBarSize;
    }

    /**
     * Whether landscape screen.
     */
    protected boolean isLandscape() {
        switch (mConfiguration.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: {
                return true;
            }
            case Configuration.ORIENTATION_UNDEFINED:
            case Configuration.ORIENTATION_PORTRAIT:
            default: {
                return false;
            }
        }
    }

    /**
     *
     * api min is 21 version
     * 0:statusbar is visible
     * 1:navigation is visible
     *
     * @return statusbar, navigation是否可见
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean[] isSystemUiVisible(Window window) {
        boolean[] result = new boolean[]{false, false};
        if (window == null) {
            return result;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (attributes != null) {
            result[0] = (attributes.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            result[1] = (((attributes.systemUiVisibility | decorView.getWindowSystemUiVisibility()) &
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) && (attributes.flags & WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS) != 0;
        }
        //
        Object decorViewObj = window.getDecorView();
        Class<?> clazz = decorViewObj.getClass();
        int mLastBottomInset = 0, mLastRightInset = 0, mLastLeftInset = 0;
        try {
            Field mLastBottomInsetField = clazz.getDeclaredField("mLastBottomInset");
            mLastBottomInsetField.setAccessible(true);
            mLastBottomInset = mLastBottomInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field mLastRightInsetField = clazz.getDeclaredField("mLastRightInset");
            mLastRightInsetField.setAccessible(true);
            mLastRightInset = mLastRightInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field mLastLeftInsetField = clazz.getDeclaredField("mLastLeftInset");
            mLastLeftInsetField.setAccessible(true);
            mLastLeftInset = mLastLeftInsetField.getInt(decorViewObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isNavBarToRightEdge = mLastBottomInset == 0 && mLastRightInset > 0;
        int size = isNavBarToRightEdge ? mLastRightInset : (mLastBottomInset == 0 && mLastLeftInset > 0 ? mLastLeftInset : mLastBottomInset);
        result[1] = result[1] && size > 0;
        return result;
    }
}