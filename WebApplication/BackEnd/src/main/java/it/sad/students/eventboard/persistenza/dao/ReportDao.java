package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Report;

import java.util.List;

public interface ReportDao {
    List<Report> findAll();

    Report findByPrimaryKey(Long id);

    boolean saveOrUpdate(Report report);

    void delete(Report report);
}
