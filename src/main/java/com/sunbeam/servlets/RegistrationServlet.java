package com.sunbeam.servlets;

import java.io.IOException;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("firstname");
		String lname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		String dob = req.getParameter("dob");
		String role = req.getParameter("role");
		try(UserDao userDao = new UserDaoImpl()){
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
}
