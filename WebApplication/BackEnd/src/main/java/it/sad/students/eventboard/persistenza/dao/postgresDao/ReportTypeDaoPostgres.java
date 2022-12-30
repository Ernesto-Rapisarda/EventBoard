package it.sad.students.eventboard.persistenza.dao.postgresDao;

import it.sad.students.eventboard.persistenza.IdBroker;
import it.sad.students.eventboard.persistenza.dao.ReportTypeDao;
import it.sad.students.eventboard.persistenza.model.EventType;
import it.sad.students.eventboard.persistenza.model.ReportType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportTypeDaoPostgres implements ReportTypeDao {
    Connection conn;

    public ReportTypeDaoPostgres(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<ReportType> findAll() {
        ArrayList<ReportType> reportTypes = new ArrayList<>();
        String query ="select * from report_type";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                ReportType reportType = readEvent(rs);
                if(reportType!=null)
                    reportTypes.add(reportType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportTypes;
    }

    @Override
    public ReportType findByPrimaryKey(Long id) {
        String query ="select * from report_type where id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            stmt.setLong(1, id);

            if(rs.next()){
                ReportType reportType = readEvent(rs);
                if(reportType!=null)
                    return reportType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveOrUpdate(ReportType reportType) {
        String insertEventType = "INSERT INTO report_type VALUES (?, ?)";
        String updateEventType = "UPDATE report_type set name=? where id = ?";
        PreparedStatement st = null;

        try {
            if (reportType.getId() == null){
                st = conn.prepareStatement(insertEventType);
                Long newId = IdBroker.getNewReportTypeID(conn);
                reportType.setId(newId);

                st.setLong(1, reportType.getId());
                st.setString(2,reportType.getName());
            }
            else{
                st = conn.prepareStatement(updateEventType);
                st.setString(1,reportType.getName());
                st.setLong(2, reportType.getId());

            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ReportType reportType) {
        String query = "DELETE FROM report_type WHERE id = ?";
        try {
            // TODO: 30/12/2022 rimozione a cascata
            PreparedStatement st = conn.prepareStatement(query);
            st.setLong(1, reportType.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ReportType readEvent(ResultSet rs) {
        try{
            ReportType reportType = new ReportType();
            reportType.setId(rs.getLong("id"));
            reportType.setName(rs.getString("name"));

            return reportType;
        }catch (SQLException e){e.printStackTrace();}

        return null;
    }
}
