package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.dao.ReportTypeDao;
import it.sad.students.eventboard.persistenza.model.ReportType;

import java.util.List;

public class ReportTypeDaoPostgres implements ReportTypeDao {
    @Override
    public List<ReportType> findAll() {
        return null;
    }

    @Override
    public ReportType findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(ReportType reportType) {

    }

    @Override
    public void delete(ReportType reportType) {

    }
}
