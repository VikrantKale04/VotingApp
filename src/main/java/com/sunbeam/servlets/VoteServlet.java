package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Vote</title>");
		out.println("</head>");
		out.println("<body>");
		String uname = "";
		Cookie[] arr = req.getCookies();
		if(arr != null && arr.length > 0) {
			for(Cookie c : arr) {
				if(c.getName().equals("uname"))
					uname = c.getValue();
			}
		}
		int candId = Integer.parseInt(req.getParameter("candidate"));
		
		HttpSession session = req.getSession();
		User u = (User)session.getAttribute("user");
		
//		See if user already voted or not
		if(u.getStatus() != 0) {
			out.println("<h3>User already voted</h3>");
		}else {
			try(UserDao userDao = new UserDaoImpl()){
//				Vote to candidate also change status of voter
				u.setStatus(1);
				userDao.update(u);
			}catch(Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
//			Increment count of vote for candidate
			try(CandidateDao candDao = new CandidateDaoImpl()){
				candDao.incrVote(candId);
				
			}catch(Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
			out.println("Your vote registered.");
		}
		out.printf("<h1>Hello, %s </h1>\n", uname);
		out.println("<div><a href='logout'>Sign Out</a></div>");
		out.println("</body>");
		out.println("</html>");
		
	}
	
}
