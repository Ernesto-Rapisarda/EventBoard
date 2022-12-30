package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.ReviewDao;
import it.sad.students.eventboard.persistenza.model.Review;

import java.util.List;

public class ReviewDaoPostgress implements ReviewDao {
    @Override
    public List<Review> findAll() {
        return null;
    }

    @Override
    public Review findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Review review) {

    }

    @Override
    public void delete(Review review) {

    }
}
