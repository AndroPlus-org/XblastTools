package ind.fem.black.xposed.mods;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class Battery {
    public static final String PACKAGE_NAME = "com.android.systemui";
    public static final String CLASS_PHONE_STATUSBAR = "com.android.systemui.statusbar.phone.PhoneStatusBar";
    public static final String CLASS_BATTERY_CONTROLLER = "com.android.systemui.statusbar.policy.BatteryController";
    private static final String TAG = "Battery";
    
    /*public static void initResources(XSharedPreferences prefs, InitPackageResourcesParam resparam) {
        try {
            resparam.res.hookLayout(PACKAGE_NAME, "layout", "gemini_super_status_bar", new XC_LayoutInflated() {

                @Override
                public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {

                    ViewGroup vg = (ViewGroup) liparam.view.findViewById(
                            liparam.res.getIdentifier("signal_battery_cluster", "id", PACKAGE_NAME));

                    // GM2 specific - if there's already view with id "circle_battery", remove it
                    ImageView exView = (ImageView) vg.findViewById(liparam.res.getIdentifier("circle_battery", "id", PACKAGE_NAME));
                    if (exView != null) {
                        XposedBridge.log("ModBatteryStyle: circle_battery view found - removing");
                        vg.removeView(exView);
                    }

                    // inject circle battery view
                    CmCircleBattery circleBattery = new CmCircleBattery(vg.getContext());
                    circleBattery.setTag("circle_battery");
                    LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    circleBattery.setLayoutParams(lParams);
                    circleBattery.setPadding(4, 0, 0, 0);
                    vg.addView(circleBattery);
                    XposedBridge.log("ModBatteryStyle: CmCircleBattery injected");
                }
                
            });
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }*/

    public static void init(final XSharedPreferences prefs, ClassLoader classLoader) {

        XposedBridge.log(TAG + " : init");
        
        try {

            //Class<?> phoneStatusBarClass = findClass(CLASS_PHONE_STATUSBAR, classLoader);
            Class<?> batteryControllerClass = findClass(CLASS_BATTERY_CONTROLLER, classLoader);

           /* findAndHookMethod(phoneStatusBarClass, "makeStatusBarView", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Object mBatteryController = XposedHelpers.getObjectField(param.thisObject, "mBatteryController");
                    View mStatusBarView = (View) XposedHelpers.getObjectField(param.thisObject, "mStatusBarView");
                    ImageView circleBattery = (ImageView) mStatusBarView.findViewWithTag("circle_battery");
                    XposedHelpers.callMethod(mBatteryController, "addIconView", circleBattery);
                    XposedBridge.log("ModBatteryStyle: BatteryController.addIconView(circleBattery)");
                }
            });*/

           /* XposedBridge.hookAllConstructors(batteryControllerClass, new XC_MethodHook() {

                @Override
                protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                    prefs.reload();
                    int mBatteryStyle = Integer.valueOf(prefs.getString(GravityBoxSettings.PREF_KEY_BATTERY_STYLE, "1"));
                    XposedHelpers.setAdditionalInstanceField(param.thisObject, "mBatteryStyle", mBatteryStyle);
                    XposedBridge.log("ModBatteryStyle: mBatteryStyle instance field injected; value = " + mBatteryStyle);
                }
            });*/

            findAndHookMethod(batteryControllerClass, "onReceive", Context.class, Intent.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                   /* Intent intent = (Intent) param.args[1];

                    int mBatteryStyle = (Integer) XposedHelpers.getAdditionalInstanceField(param.thisObject, "mBatteryStyle");

                    if (intent.getAction().equals(GravityBoxSettings.ACTION_PREF_BATTERY_STYLE_CHANGED) && 
                            intent.hasExtra("batteryStyle")) {
                        mBatteryStyle = intent.getIntExtra("batteryStyle", 1);
                        XposedHelpers.setAdditionalInstanceField(param.thisObject, "mBatteryStyle", mBatteryStyle);
                        XposedBridge.log("ModBatteryStyle: mBatteryStyle changed to: " + mBatteryStyle);
                    }
*/
                    @SuppressWarnings("unchecked")
                    ArrayList<ImageView> mIconViews = (ArrayList<ImageView>) XposedHelpers.getObjectField(param.thisObject, "mIconViews");
                    /*@SuppressWarnings("unchecked")
                    ArrayList<TextView> mLabelViews = (ArrayList<TextView>) XposedHelpers.getObjectField(param.thisObject, "mLabelViews");*/
                    
                    //XposedBridge.log("mIconViews" + mIconViews.size());
                    try {
                    	if(mIconViews.size() > 0) {
                    		int batteryColor = prefs.getInt("battery_color", 0);
            			if (batteryColor != 0) {
            				ImageView battery = mIconViews.get(0); 
            				battery.setColorFilter(batteryColor, Mode.SRC_IN);
            				/*int dockBatId = liparam.res.getIdentifier("dock_battery", "id",
            						"com.android.systemui");
            				if (dockBatId != 0) {
            					ImageView dock_battery = (ImageView) liparam.view
            							.findViewById(liparam.res.getIdentifier(
            									"dock_battery", "id",
            									"com.android.systemui"));
            					dock_battery.setColorFilter(
            							prefs.getInt("battery_color", 0xFFffffff),
            							Mode.SRC_IN);
            				}*/
            			}
                    	}
            		} catch (Throwable t) {
            			XposedBridge.log(t);
            		}
                   /* mIconViews.get(0).setVisibility(
                            (mBatteryStyle == GravityBoxSettings.BATTERY_STYLE_STOCK ||
                             mBatteryStyle == GravityBoxSettings.BATTERY_STYLE_PERCENT_STOCK) ?
                                     View.VISIBLE : View.GONE);*/

                  /*  mIconViews.get(1).setVisibility(mBatteryStyle == GravityBoxSettings.BATTERY_STYLE_CIRCLE ?
                            View.VISIBLE : View.GONE);*/

                    /*mLabelViews.get(0).setVisibility(
                            (mBatteryStyle == GravityBoxSettings.BATTERY_STYLE_PERCENT ||
                             mBatteryStyle == GravityBoxSettings.BATTERY_STYLE_PERCENT_STOCK) ?
                            View.VISIBLE : View.GONE);*/
                }
            });
        }
        catch (Throwable e) {
            XposedBridge.log(e);
        }
    }
}