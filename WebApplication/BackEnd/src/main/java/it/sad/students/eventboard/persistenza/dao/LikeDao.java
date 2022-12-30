package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Like;

import java.util.List;

public interface LikeDao {
    List<Like> findAll();

    Like findByPrimaryKey(Long id);

    void saveOrUpdate(Like like);

    void delete(Like like);
}
