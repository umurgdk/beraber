package org.beraber.beraber.repositories;

import org.beraber.beraber.daos.DaoSession;
import org.beraber.beraber.daos.User;
import org.beraber.beraber.daos.UserDao;

import java.util.List;

public class UserRepository {
    DaoSession session;

    UserDao dao;

    public UserRepository(DaoSession session) {
        this.session = session;
        this.dao = this.session.getUserDao();
    }

    public void insertOrUpdate(User user) {
        dao.insertOrReplace(user);
    }

    public void clear() {
        dao.deleteAll();
    }

    public void deleteById(long id) {
        dao.delete(getById(id));
    }

    public List<User> getAll() {
        return dao.loadAll();
    }

    public User getById(long id) {
        return dao.load(id);
    }
}
