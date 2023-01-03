package it.sad.students.eventboard.controller;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

@WebServlet("/doLogin")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ciao");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		/*UtenteDao udao = DBManager.getInstance().getUtenteDao();
		Utente utente = udao.findByPrimaryKey(username);
		boolean logged;
		if (utente == null) {
			logged = false;
		}else {
			if (password.equals(utente.getPassword())) {
				logged = true;
				HttpSession session = req.getSession();
				session.setAttribute("user", utente);
				session.setAttribute("sessionId", session.getId());
				
				req.getServletContext().setAttribute(session.getId(), session);
			}else {
				logged = false;
			}
		}*/
		boolean logged = true;
		if (logged) {
//			RequestDispatcher dispacher = req.getRequestDispatcher("views/index.html");
//			dispacher.forward(req, resp);

			resp.sendRedirect("http://localhost:8080/");
		}else {
			resp.sendRedirect("/notAuthorized.html");
		}

		//resp.encodeRedirectURL("http://localhost:8080/");
	}
}
