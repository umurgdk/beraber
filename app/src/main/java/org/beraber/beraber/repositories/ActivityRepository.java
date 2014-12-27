package org.beraber.beraber.repositories;

import org.beraber.beraber.daos.Activity;
import org.beraber.beraber.daos.ActivityDao;
import org.beraber.beraber.daos.DaoSession;

import java.util.List;

public class ActivityRepository {
    DaoSession session;

    ActivityDao dao;

    public ActivityRepository(DaoSession session) {
        this.session = session;
        this.dao = this.session.getActivityDao();
    }

    public void insertOrUpdate(Activity box) {
        dao.insertOrReplace(box);
    }

    public void clear() {
        dao.deleteAll();
    }

    public void deleteById(long id) {
        dao.delete(getById(id));
    }

    public List<Activity> getAll() {
        return dao.loadAll();
    }

    public Activity getById(long id) {
        return dao.load(id);
    }
}
