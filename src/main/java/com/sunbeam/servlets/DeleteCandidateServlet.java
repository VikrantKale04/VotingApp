package com.sunbeam.servlets;

import java.io.IOException;

import com.sunbeam.daos.CandidateDao;
import com.sunbeam.daos.CandidateDaoImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delcand")
public class DeleteCandidateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int candid = Integer.parseInt(req.getParameter("candid"));
		try(CandidateDao candDao = new CandidateDaoImpl()){
			candDao.deleteById(candid);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		resp.sendRedirect("result");
	}
	
}
