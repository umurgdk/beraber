package org.beraber.beraber.helpers.entities;

import org.beraber.beraber.daos.Activity;

import java.util.Date;

public class ActivityEntityHelper {
    public void updateNotNull(Activity existing, Activity newOne) {
        if (existing == newOne) {
            return;
        }

        String title = newOne.getTitle();
        if (title != null) {
            existing.setTitle(title);
        }

        String description = newOne.getDescription();
        if (description != null) {
            existing.setDescription(description);
        }

        existing.setUser_id(newOne.getUser_id());
        existing.setServer_id(newOne.getServer_id());

        Date start_date = newOne.getStart_date();
        if (start_date != null) {
            existing.setStart_date(start_date);
        }
    }
}
