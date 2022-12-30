package it.sad.students.eventboard.persistenza.dao;

import it.sad.students.eventboard.persistenza.model.Event;
import it.sad.students.eventboard.persistenza.model.Report;

import java.util.List;

public interface ReportDao {
    List<Report> findAll();

    Report findByPrimaryKey(Long id);

    void saveOrUpdate(Report report);

    void delete(Report report);
}
