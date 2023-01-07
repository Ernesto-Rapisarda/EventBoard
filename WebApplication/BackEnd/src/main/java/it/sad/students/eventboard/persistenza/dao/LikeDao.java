package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Like;

import java.sql.SQLException;
import java.util.List;

public interface LikeDao {
    List<Like> findAll();
    List<Like> findByPerson(Long person);

    Like findByPrimaryKey(Long person, Long event);

    void saveOrUpdate(Like like);

    void delete(Like like);

    boolean deleteByEvent(Long idEvent)throws SQLException;
}
