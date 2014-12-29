package org.beraber.beraber.events;

import org.beraber.beraber.daos.User;

import java.util.Collection;
import java.util.HashMap;

public class UserEntityEvents {
    public static class FetchedBatch {
        private HashMap<Long, User> users = new HashMap<Long, User>();

        public FetchedBatch(Collection<User> users) {
            for (User user : users) {
                this.users.put(user.getId(), user);
            }
        }

        public User getUserById(long id) {
            return users.get(id);
        }
    }
}
