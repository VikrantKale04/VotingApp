package com.sunbeam.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/announce")
public class AnnouncementServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Get announcement from previous page
		String ann = req.getParameter("ann");
//		Store it in ServletContext
		ServletContext ctx = req.getServletContext();
		ctx.setAttribute("announcemet", ann);
//		Go to result page
		RequestDispatcher rd = ctx.getRequestDispatcher("/result");
		rd.forward(req, resp);
	}
	
}
