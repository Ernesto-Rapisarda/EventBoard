package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.PartecipationDao;
import it.sad.students.eventboard.persistenza.model.Partecipation;

import java.sql.Connection;
import java.util.List;

public class PartecipationDaoPostgress implements PartecipationDao {
    Connection conn;
    public PartecipationDaoPostgress(Connection conn) {
        this.conn = conn;
    }
    @Override
    public List<Partecipation> findAll() {
        return null;
    }

    @Override
    public Partecipation findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Partecipation partecipation) {

    }

    @Override
    public void delete(Partecipation partecipation) {

    }
}
