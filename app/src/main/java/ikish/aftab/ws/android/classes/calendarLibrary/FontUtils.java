package ikish.aftab.ws.android.classes.calendarLibrary;




import android.content.Context;
import android.graphics.Typeface;

import androidx.collection.SimpleArrayMap;

public class FontUtils {
    private static String FONT_NAME = "IranYekan";

    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface Default(Context context) {
        synchronized (cache) {
            if (!cache.containsKey(FONT_NAME)) {
                Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/" + "IRANSans" + ".ttf");
                cache.put(FONT_NAME, t);
                return t;
            }
            return cache.get(FONT_NAME);
        }
    }
}
