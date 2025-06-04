package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		
		try(UserDao userDao = new UserDaoImpl()){
			User u = userDao.findByEmail(email);
			if(u != null && u.getPassword().equals(passwd)) {
				if(u.getRole().equals("admin"))
					resp.sendRedirect("result");
				else
					resp.sendRedirect("candlist");
			}
			else {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Failed</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("Invalid email or password.");
				out.println("<br/>");
				out.println("<a href='index.html'>Login again</a>");
				out.println("</body>");
				out.println("</html>");	
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}
	
}
