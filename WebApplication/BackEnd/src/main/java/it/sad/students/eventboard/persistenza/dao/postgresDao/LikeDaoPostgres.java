package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.LikeDao;
import it.sad.students.eventboard.persistenza.model.Like;

import java.util.List;

public class LikeDaoPostgres implements LikeDao {
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
