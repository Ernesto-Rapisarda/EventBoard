package it.sad.students.eventboard.controller;

import it.sad.students.eventboard.persistenza.DBManager;
import it.sad.students.eventboard.persistenza.model.EventsStats;
import it.sad.students.eventboard.persistenza.model.GlobalStats;
import it.sad.students.eventboard.service.EventService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@WebServlet("/getstats")
public class StatsServlet extends HttpServlet {

    private final EventService eventService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GlobalStats globalStats = new GlobalStats();
            globalStats.setNEvents(DBManager.getInstance().getEventDao().findAll().size());
            globalStats.setNComments(DBManager.getInstance().getCommentDao().findAll().size());
            globalStats.setNReviews(DBManager.getInstance().getReviewDao().findAll().size());
            globalStats.setNLike(DBManager.getInstance().getLikeDao().findAll().size());
            globalStats.setNPartecipation(DBManager.getInstance().getPartecipationDao().findAll().size());
            req.setAttribute("global", globalStats);

            List<EventsStats> eventsStatsRating = eventService.getTopFiveRating();
            List<EventsStats> eventsStatsLikes = eventService.getTopFiveLikes();

            req.setAttribute("topFive", eventsStatsRating);
            req.setAttribute("topLiked", eventsStatsLikes);
            RequestDispatcher dispacher = req.getRequestDispatcher("views/stats.html");
            dispacher.forward(req, resp);
        }catch(Exception e){
            
        }
    }
}
