package org.beraber.beraber.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.beraber.beraber.R;
import org.beraber.beraber.daos.User;


public class AuthorViewHelper {
    public Drawable getAvatarDrawable(Context context, User user) {
        Resources resources = context.getResources();

        return resources.getDrawable(R.drawable.avatar2);
    }
}
