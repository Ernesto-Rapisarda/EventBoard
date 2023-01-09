package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDao {
    List<Comment> findAll();
    List<Comment> findByPerson(Long person);
    List<Comment> findByEvent(Long id);

    Comment findByPrimaryKey(Long id);

    boolean saveOrUpdate(Comment comment);

    void delete(Comment comment);

    boolean deleteByEvent(Long idEvent) throws SQLException;
}
