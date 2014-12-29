package org.beraber.beraber.events;

import org.beraber.beraber.daos.Activity;

public class ActivityEntityEvents {

    public static class Posting {
        public final Activity activity;
        public final long     local_id;

        public Posting(Activity activity, long local_id) {
            this.activity = activity;
            this.local_id = local_id;
        }
    }

    public static class Posted {
        public final Activity activity;
        public final long     local_id;

        public Posted(Activity activity, long local_id) {
            this.activity = activity;
            this.local_id = local_id;
        }
    }

    public static class Deleted {
        public final long local_id;

        public Deleted(long local_id) {
            this.local_id = local_id;
        }
    }

    public static class FailedCreate {

    }
}
