package it.sad.students.eventboard.persistenza;

import it.sad.students.eventboard.persistenza.dao.*;
import it.sad.students.eventboard.persistenza.dao.postgresDao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    //SINGLETON
    private static DBManager instance = null;
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    private DBManager() {
    }

    //END SINGLETON


    //PARAMETERS
    Connection conn = null;




    public Connection getConnection() {
        if (conn == null) {
            try {
                // TODO: 30/12/2022 ricordarsi di inserire la propria password locale
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/eventBoard", "postgres", "123456");
            } catch (SQLException e) {
                
            }
        }
        return conn;
    }

    public PersonDao getPersonDao() {return new PersonDaoPostgress(getConnection());}
    public PartecipationDao getPartecipationDao(){return new PartecipationDaoPostgress(getConnection());}
    public ReviewDao getReviewDao() {return new ReviewDaoPostgress(getConnection());}
    public PositionDao getPositionDao() {return new PositionDaoPostgress(getConnection());}
    //public RoleDao getRoleDao() {return new RoleDaoPostgress(getConnection());}
    public EventDao getEventDao (){return new EventDaoPostgres(getConnection());}
    public PreferenceDao getPreferenceDao(){return new PreferenceDaoPostgress(getConnection());}
    public CommentDao getCommentDao(){return new CommentDaoPostgres(getConnection());}
    public ReportDao getReportDao(){return new ReportDaoPostgres(getConnection());}
    public LikeDao getLikeDao(){return new LikeDaoPostgres(getConnection());}


}

