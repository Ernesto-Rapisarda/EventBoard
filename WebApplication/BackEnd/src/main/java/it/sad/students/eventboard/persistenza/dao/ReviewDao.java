package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Review;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDao {
    List<Review> findAll();
    List<Review> findByPerson(Long person);

    Review findByPrimaryKey(Long person,Long event);

    void saveOrUpdate(Review review);

    void delete(Review review);

    boolean deleteByEvent(Long idEvent)throws SQLException;

    List<Review> findByEvent(Long id);
}
