package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findAll();

    Comment findByPrimaryKey(Long id);

    void saveOrUpdate(Comment comment);

    void delete(Comment comment);
}
