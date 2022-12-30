package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.LikeDao;
import it.sad.students.eventboard.persistenza.model.Like;

import java.sql.Connection;
import java.util.List;

public class LikeDaoPostgres implements LikeDao {

    Connection conn;

    public LikeDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Like> findAll() {
        return null;
    }

    @Override
    public Like findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Like like) {

    }

    @Override
    public void delete(Like like) {

    }
}
