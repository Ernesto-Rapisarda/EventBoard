package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.ReportType;

import java.util.List;

public interface ReportTypeDao {
    List<ReportType> findAll();

    ReportType findByPrimaryKey(Long id);

    void saveOrUpdate(ReportType reportType);

    void delete(ReportType reportType);
}
