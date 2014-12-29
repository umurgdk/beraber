package org.beraber.beraber.helpers.entities;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.User;

import java.util.Date;

public class UserEntityHelper {
    public void updateNotNull(User existing, User newOne) {
        if (existing == newOne) {
            return;
        }

        String name = newOne.getName();
        if (name != null) {
            existing.setName(name);
        }

        String bio = newOne.getBio();
        if (bio != null) {
            existing.setBio(bio);
        }

        existing.setServer_id(newOne.getServer_id());
    }
}
