package web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import web.service.LoginService;
import web.service.MathQuestionService;

@Controller
@RequestMapping("/")
public class RoutingServlet {

	private static final String INVALID_NUMBER_MESSAGE = "Please enter valid numbers and an answer.";
	private static final String WRONG_ANSWER_MESSAGE = "Wrong answer, try again.";
	private static final double ANSWER_TOLERANCE = 0.000001;

	private boolean isCorrectAnswer(Double calculatedResult, Double userResult) {
		return Math.abs(calculatedResult - userResult) < ANSWER_TOLERANCE;
	}

	@GetMapping("/")
	public String welcome() {
		System.out.println("Welcome ...");
		return "view-welcome";
	}
	

	@GetMapping("/login")
	public String loginView() {
		System.out.println("login view...");
		return "view-login";
	}
	

	@PostMapping("/login")
	public RedirectView login(
			HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		System.out.println("login form...");
		String username = request.getParameter("username");
		String password = request.getParameter("passwd");
		String dob = request.getParameter("dob");
		
		System.out.println("Username/password: " + username + ", " + password);
		
		RedirectView redirectView = null;
		if (LoginService.login(username, password, dob)) {
			redirectView = new RedirectView("/q1", true);
		} else {
			// Login failed, stay with login page.
			//
			redirectView = new RedirectView("/login", true);
			// Show error message
			//
			redirectAttributes.addFlashAttribute("message", "Incorrect credentials.");
		}
		
		return redirectView;
	}
	

	@GetMapping("/q1")
	public String q1View() {		
		System.out.println("q1 view...");
		return "view-q1";
	}

	@PostMapping("/q1")
	public RedirectView q1(
			HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		System.out.println("q1 form...");
		String number1 = request.getParameter("number1");
		String number2 = request.getParameter("number2");
		String resultUser = request.getParameter("result");
		
		Double calculatedResult = MathQuestionService.q1Addition(number1, number2);
		Double userResult = MathQuestionService.parseNumber(resultUser);
		System.out.println(
				"User result: " + resultUser + ", answer: " + calculatedResult);
		
		RedirectView redirectView = null;
		if (calculatedResult == null || userResult == null) {
			redirectView = new RedirectView("/q1", true);
			redirectAttributes.addFlashAttribute("message", INVALID_NUMBER_MESSAGE);
		} else if (isCorrectAnswer(calculatedResult, userResult)) {
			redirectView = new RedirectView("/q2", true);
		} else {
			// Q1 wrong.
			//
			redirectView = new RedirectView("/q1", true);
			// Show error message
			//
			redirectAttributes.addFlashAttribute("message", WRONG_ANSWER_MESSAGE);
		}		
		return redirectView;
	}	
	

	@GetMapping("/q2")
	public String q2View() {		
		System.out.println("q2 view...");
		return "view-q2";
	}	


	@PostMapping("/q2")
	public RedirectView q2(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		System.out.println("q1 form...");
		String number1 = request.getParameter("number1");
		String number2 = request.getParameter("number2");
		String resultUser = request.getParameter("result");
		
		Double calculatedResult = MathQuestionService.q2Subtraction(number1, number2);
		Double userResult = MathQuestionService.parseNumber(resultUser);
		System.out.println("User result: " + resultUser + ", answer: " + calculatedResult);
		
		RedirectView redirectView = null;
		if (calculatedResult == null || userResult == null) {
			redirectView = new RedirectView("/q2", true);
			redirectAttributes.addFlashAttribute("message", INVALID_NUMBER_MESSAGE);
		} else if (isCorrectAnswer(calculatedResult, userResult)) {
			redirectView = new RedirectView("/q3", true);
		} else {
			// Q1 wrong
			//
			redirectView = new RedirectView("/q2", true);
			// Show error message
			//
			redirectAttributes.addFlashAttribute("message", WRONG_ANSWER_MESSAGE);
		}		
		return redirectView;
	}	
	

	@GetMapping("/q3")
	public String q3View() {		
		System.out.println("q3 view...");
		return "view-q3";
	}		
	

	@PostMapping("/q3")
	public RedirectView q3(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		System.out.println("q3 form...");
		String number1 = request.getParameter("number1");
		String number2 = request.getParameter("number2");
		String resultUser = request.getParameter("result");
		
		Double calculatedResult = MathQuestionService.q3Multiplication(number1, number2);
		Double userResult = MathQuestionService.parseNumber(resultUser);
		System.out.println("User result: " + resultUser + ", answer: " + calculatedResult);
		
		RedirectView redirectView = null;
		if (calculatedResult == null || userResult == null) {
			redirectView = new RedirectView("/q3", true);
			redirectAttributes.addFlashAttribute("message", INVALID_NUMBER_MESSAGE);
		} else if (isCorrectAnswer(calculatedResult, userResult)) {
			redirectView = new RedirectView("/", true);
			redirectAttributes.addFlashAttribute("message", "Well done, you completed the quiz.");
		} else {
			redirectView = new RedirectView("/q3", true);
			redirectAttributes.addFlashAttribute("message", WRONG_ANSWER_MESSAGE);
		}		
		return redirectView;
	}
}
