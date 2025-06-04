package com.sunbeam.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;
import com.sunbeam.entities.Candidate;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/candlist")
public class CandidateListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Candidate> list = null;
		try(CandidateDao candDao = new CandidateDaoImpl()){
			list = candDao.findAll();
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Candidates</title>");
		out.println("</head>");
		out.println("<body>");
		
//		Get app title from context-param (in web.xml)
		ServletContext app = req.getServletContext();
		String title = app.getInitParameter("apptitle");
		out.println("<h2>"+title+"</h2>");
		
		
		String uname = "";
		Cookie[] arr = req.getCookies();
		if(arr != null && arr.length > 0) {
			for(Cookie c : arr) {
				if(c.getName().equals("uname"))
					uname = c.getValue();
			}
		}
//		out.println("Hello, " + uname + "<br/>");
		out.printf("<h2>Hello, %s </h2>\n", uname);
		
//		Get announcement
		ServletContext ctx = req.getServletContext();
		String ann = (String)ctx.getAttribute("announcemet");
		if(ann != null)
			out.printf("<h3>ATTENTION :  %s </h3>\n", ann);
		
		out.println("<h3>Candidates List</h3>");
		out.println("<form method='post' action='vote'>");
		for(Candidate c : list) {
			out.printf("<input type='radio' name='candidate' value='%d'/> %s - %s <br/>\n", c.getId(), c.getName(), c.getParty());
		}
		out.println("<input type='submit' value = 'vote' />");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
	
}
