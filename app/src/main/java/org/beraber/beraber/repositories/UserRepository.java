package org.beraber.beraber.repositories;

import org.beraber.beraber.daos.DaoSession;
import org.beraber.beraber.daos.User;
import org.beraber.beraber.daos.UserDao;

import java.util.Collection;
import java.util.List;

public class UserRepository {
    DaoSession session;

    UserDao dao;

    public UserRepository(DaoSession session) {
        this.session = session;
        this.dao = this.session.getUserDao();
    }

    public long insertOrUpdate(User user) {
        return dao.insertOrReplace(user);
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

    public User getByServerId(long id) {
        return dao.queryBuilder().where(UserDao.Properties.Server_id.eq(id)).unique();
    }

    public List<User> getUsersById(List<Long> ids) {
        return dao.queryBuilder().where(UserDao.Properties.Id.in(ids)).list();
    }

    public List<User> getUsersByServerId(Collection<Long> ids) {
        return dao.queryBuilder().where(UserDao.Properties.Server_id.in(ids)).list();
    }
}
