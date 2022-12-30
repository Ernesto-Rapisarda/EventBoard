package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.CommentDao;
import it.sad.students.eventboard.persistenza.model.Comment;

import java.util.List;

public class CommentDaoPostgres implements CommentDao {
    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Comment comment) {

    }

    @Override
    public void delete(Comment comment) {

    }
}
