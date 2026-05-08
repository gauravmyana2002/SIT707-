package web.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.LoginService;


/**
 * HTTP end-point to handle login service.
 */
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {	
		System.out.println("[LoginServlet] GET");
		
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {		
		System.out.println("[LoginServlet] POST");
		
		String username = req.getParameter("username");
		String password = req.getParameter("passwd");
		String dob = req.getParameter("dob");
		
		System.out.println("Username/password/dob: " + username + ", " + password + ", " + dob);
		
		String loginStatus = "fail";
		
		if (LoginService.login(username, password, dob)) {
			loginStatus = "success";
		}		
		
		resp.setContentType("text/html;charset=UTF-8");

		String htmlResponse = "<html>";
		htmlResponse += "<head><title>"+ loginStatus + "</title></head>";
		htmlResponse += "<body>";
		htmlResponse += "<h2 id=\"status\">Login status: " + loginStatus + "</h2>";
		htmlResponse += "<p id=\"message\">" + getMessage(loginStatus) + "</p>";
		htmlResponse += "</body>";
		htmlResponse += "</html>";
		
		PrintWriter writer = resp.getWriter();
		writer.println(htmlResponse);
	}

	private String getMessage(String loginStatus) {
		if ("success".equals(loginStatus)) {
			return "Authentication successful.";
		}
		return "Authentication failed.";
	}
	
}
