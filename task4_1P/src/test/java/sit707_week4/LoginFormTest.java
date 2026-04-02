package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests functions in LoginForm.
 * @author Ahsan Habib
 */
public class LoginFormTest 
{

	@Test
	public void testStudentIdentity() {
		String studentId = "225108954";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Gaurav Myana";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
    public void testFailEmptyUsernameAndEmptyPasswordAndDontCareValCode()
    {
		LoginStatus status = LoginForm.login(null, null);
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Empty Username", status.getErrorMsg());
    }
	
	@Test
	public void testFailEmptyUsernameAndCorrectPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login(null, "gaurav_pass");
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Empty Username", status.getErrorMsg());
	}

	@Test
	public void testFailCorrectUsernameAndEmptyPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("gauravmyana", null);
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Empty Password", status.getErrorMsg());
	}

	@Test
	public void testFailWrongUsernameAndWrongPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("wrong_user", "wrong_pass");
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}

	@Test
	public void testFailCorrectUsernameAndWrongPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("gauravmyana", "wrong_pass");
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}

	@Test
	public void testFailWrongUsernameAndCorrectPasswordAndDontCareValCode() {
		LoginStatus status = LoginForm.login("wrong_user", "gaurav_pass");
		Assert.assertFalse(status.isLoginSuccess());
		Assert.assertEquals("Credential mismatch", status.getErrorMsg());
	}

	@Test
	public void testSuccessCorrectUsernameAndCorrectPassword() {
		LoginStatus status = LoginForm.login("gauravmyana", "gaurav_pass");
		Assert.assertTrue(status.isLoginSuccess());
		Assert.assertEquals("123456", status.getErrorMsg());
	}

	@Test
	public void testFailValidationCodeWhenLoginIsCorrectButCodeIsEmpty() {
		LoginStatus status = LoginForm.login("gauravmyana", "gaurav_pass");
		Assert.assertTrue(status.isLoginSuccess());
		Assert.assertFalse(LoginForm.validateCode(null));
	}

	@Test
	public void testFailValidationCodeWhenLoginIsCorrectButCodeIsWrong() {
		LoginStatus status = LoginForm.login("gauravmyana", "gaurav_pass");
		Assert.assertTrue(status.isLoginSuccess());
		Assert.assertFalse(LoginForm.validateCode("abcd"));
	}

	@Test
	public void testSuccessValidationCodeWhenLoginIsCorrectAndCodeIsCorrect() {
		LoginStatus status = LoginForm.login("gauravmyana", "gaurav_pass");
		Assert.assertTrue(status.isLoginSuccess());
		Assert.assertTrue(LoginForm.validateCode("123456"));
	}
}
