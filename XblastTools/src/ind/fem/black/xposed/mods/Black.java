package ind.fem.black.xposed.mods;

import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class Black {
	public static final String PACKAGE_NAME = Black.class.getPackage().getName();
	static Class<?> loadedClass;
	//static String STATUS_BAR_INTENT = "ClockColorMod.intent.action.SB_BGCOLOR_CHANGED";
	
	 // Device types
    private static final int DEVICE_PHONE = 0;
    private static final int DEVICE_HYBRID = 1;
    private static final int DEVICE_TABLET = 2;
    
	public static final String NOTTACH_XPOSED = "com.nottach.xposed";
	public static final String ANDROID = "android";
	public static final String SYSTEM_UI = "com.android.systemui";
	public static final String MMS = "com.android.mms";
	public static final String PHONE = "com.android.phone";
	public static final String NFC = "com.android.nfc";
	public static final String SETTINGS = "com.android.settings";
	public static final String EMAIL = "com.android.email";
	public static final String NOTI_PAGE_BUDDY = "com.android.pagebuddynotisvc";
	public static final String NOTI_PAGE_BUDDY_SEC = "com.sec.android.pagebuddynotisvc";
	public static final String S_VOICE = "com.vlingo.midas";
	public static final String WALLET = "com.google.android.apps.walletnfcrel";
	public static final String HANGOUTS = "com.google.android.talk";
	public static final String FLASH_BAR_SERVICE = "com.sec.android.app.FlashBarService";
	public static final String TOUCH_WIZ = "touchwiz";
	public static final String SYS_SCOPE = "com.sec.android.app.sysscope";
	public static final String LAUNCHER = "com.sec.android.app.launcher";
	public static final String NOVA_LAUNCHER = "com.teslacoilsw.launcher";
	public static final String ADW_LAUNCHER = "org.adw.launcher";
	public static final String BUZZ_LAUNCHER = "com.buzzpia.aqua.launcher";
	public static final String GO_LAUNCHER = "com.gau.go.launcherex";
	public static final String ACTION_LAUNCHER = "com.chrislacy.actionlauncher.pro";
	public static final String APEX_LAUNCHER = "com.anddoes.launcher";
	public static final String HOLO_LAUNCHER = "com.mobint.hololauncher";
	public static final String HOLO_LAUNCHER_HD = "com.mobint.hololauncher.hd";

	public static final String[] LAUNCHERS_LIST = new String[] { LAUNCHER,
			NOVA_LAUNCHER, ADW_LAUNCHER, BUZZ_LAUNCHER, GO_LAUNCHER,
			ACTION_LAUNCHER, APEX_LAUNCHER, HOLO_LAUNCHER, HOLO_LAUNCHER_HD };

    // Device type reference
    private static int mDeviceType = -1;
    
		public static boolean findClassInPhone(String className) {
			boolean classAvailable  = true;
			try {
				loadedClass = XposedHelpers.findClass(className, null);
			} catch (Throwable t) {
				XposedBridge.log(className + ": not found");
				classAvailable = false;
			}
			return classAvailable;
		}
	
		public static boolean findClassInPhone(String className, ClassLoader classLoader) {
			boolean classAvailable  = true;
			try {
				XposedHelpers.findClass(className, classLoader);
			} catch (Throwable t) {
				XposedBridge.log(className + ": not found");
				classAvailable = false;
			}
			return classAvailable;
		}
	
		public static void log(String tagName,String message) {
			XposedBridge.log(tagName + ": " + message);
		}
		
		public static float getTextSize(String textSize) {
			float size = 0;
			if(textSize.equalsIgnoreCase("Medium")) {
				size = 30;	
			} else if(textSize.equalsIgnoreCase("Large")) {
				size = 40;	
			}
			return size;
		}
		
		public static int getAmPMStyle(String style) {
			int AM_PM_SIZE = 2;
			if(style.equalsIgnoreCase("Small")) {
				AM_PM_SIZE = 1;	
			} else if(style.equalsIgnoreCase("Large")) {
				AM_PM_SIZE = 0;	
			}
			return AM_PM_SIZE;
		}
	
		private static int getScreenType(Context con) {
	        if (mDeviceType == -1) {
	            WindowManager wm = (WindowManager)con.getSystemService(Context.WINDOW_SERVICE);
	            DisplayMetrics outMetrics = new DisplayMetrics();
	            wm.getDefaultDisplay().getMetrics(outMetrics);
	            int shortSize = Math.min(outMetrics.heightPixels, outMetrics.widthPixels);
	            int shortSizeDp = shortSize * DisplayMetrics.DENSITY_DEFAULT / outMetrics.densityDpi;
	            if (shortSizeDp < 600) {
	                // 0-599dp: "phone" UI with a separate status & navigation bar
	                mDeviceType = DEVICE_PHONE;
	            } else if (shortSizeDp < 720) {
	                // 600-719dp: "phone" UI with modifications for larger screens
	                mDeviceType = DEVICE_HYBRID;
	            } else {
	                // 720dp: "tablet" UI with a single combined status & navigation bar
	                mDeviceType = DEVICE_TABLET;
	            }
	        }
	        return mDeviceType;
	    }

	    public static boolean isPhone(Context con) {
	        return getScreenType(con) == DEVICE_PHONE;
	    }
	
	    public static boolean isHybrid(Context con) {
	        return getScreenType(con) == DEVICE_HYBRID;
	    }
	
	    public static boolean isTablet(Context con) {
	        return getScreenType(con) == DEVICE_TABLET;
	    }
	
	    public static enum MethodState {
	        UNKNOWN,
	        METHOD_ENTERED,
	        METHOD_EXITED
	    }
	    
	    public static Typeface getSelectedFont(String fontName) {
	    	Map<CharSequence, String> fontsMap = FontManager.enumerateFonts();
			Typeface font = null;
			try {
				font = Typeface.createFromFile(fontsMap.get(fontName));
			} catch (Throwable t) {
				//font = Typeface.createFromFile(fontsMap.get("Pick a Font"));
			}
			return font;
	    }
}
