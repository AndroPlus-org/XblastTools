package ind.fem.black.xposed.adapters;

import android.graphics.drawable.Drawable;

public class BasicIconListItem extends BasicListItem
                               implements IIconListAdapterItem {
    private Drawable mIconLeft;
    private Drawable mIconRight;

    public BasicIconListItem(String text, String subText, Drawable iconIdLeft, Drawable iconIdRight) {
        super(text, subText);

        mIconLeft = iconIdLeft;
        mIconRight = iconIdRight;
    }

    public BasicIconListItem(String text, String subText) {
        this(text, subText, null, null);
    }

    public Drawable getIconLeft() {
        return mIconLeft;
    }

    public Drawable getIconRight() {
        return mIconRight;
    }

    public void setIconIdLeft(Drawable icon) {
        mIconLeft = icon;		
    }

    public void setIconRight(Drawable icon) {
        mIconRight = icon;
    }

    public void setIconIds(Drawable left, Drawable right) {
        mIconLeft = left;
        mIconRight = right;
    }
}