package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PositionDao;
import it.sad.students.eventboard.persistenza.model.Position;

import java.sql.Connection;
import java.util.List;

public class PositionDaoPostgress implements PositionDao {
    Connection conn;
    public PositionDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Position> findAll() {
        return null;
    }

    @Override
    public Position findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Position position) {

    }

    @Override
    public void delete(Position position) {

    }
}
