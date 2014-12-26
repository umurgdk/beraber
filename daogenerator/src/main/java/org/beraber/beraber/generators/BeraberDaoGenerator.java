package org.beraber.beraber.generators;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class BeraberDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "org.beraber.beraber.daos");

        Entity activity = schema.addEntity("Activity");
        activity.addIdProperty();
        activity.addStringProperty("title");
        activity.addStringProperty("description");
        activity.addDateProperty("start_date");

        Property user_id = activity.addLongProperty("user_id").notNull().getProperty();

        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("name");
        user.addStringProperty("bio");

        activity.addToOne(user, user_id);
        user.addToMany(activity, user_id);

        new DaoGenerator().generateAll(schema, args[0]);
    }
}
