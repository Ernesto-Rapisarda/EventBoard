package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Position;
import it.sad.students.eventboard.persistenza.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> findAll();

    Review findByPrimaryKey(Long person,Long event);

    void saveOrUpdate(Review review);

    void delete(Review review);
}
