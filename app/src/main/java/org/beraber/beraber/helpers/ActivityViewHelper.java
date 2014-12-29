package org.beraber.beraber.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.beraber.beraber.R;
import org.beraber.beraber.daos.Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityViewHelper {
    final public static String DATE_FORMAT = "dd MMM yyyy";

    public String formatDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public Drawable getImageDrawable(Context context, Activity activity) {
        Resources resources = context.getResources();
        return resources.getDrawable(R.drawable.activity_image);
    }
}
