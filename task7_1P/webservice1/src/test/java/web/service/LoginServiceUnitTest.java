package web.service;

import org.junit.Assert;
import org.junit.Test;

public class LoginServiceUnitTest {

	@Test
	public void testValidLoginWithAllRequiredFields() {
		Assert.assertTrue(LoginService.login("ahsan", "ahsan_pass", "1990-01-01"));
	}

	@Test
	public void testLoginFailsForIncorrectUsername() {
		Assert.assertFalse(LoginService.login("wrong_user", "ahsan_pass", "1990-01-01"));
	}

	@Test
	public void testLoginFailsForIncorrectPassword() {
		Assert.assertFalse(LoginService.login("ahsan", "wrong_pass", "1990-01-01"));
	}

	@Test
	public void testLoginFailsForIncorrectDob() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-01-02"));
	}

	@Test
	public void testLoginFailsForMissingUsername() {
		Assert.assertFalse(LoginService.login("", "ahsan_pass", "1990-01-01"));
		Assert.assertFalse(LoginService.login(null, "ahsan_pass", "1990-01-01"));
	}

	@Test
	public void testLoginFailsForMissingPassword() {
		Assert.assertFalse(LoginService.login("ahsan", "", "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", null, "1990-01-01"));
	}

	@Test
	public void testLoginFailsForMissingDob() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", ""));
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", null));
	}

	@Test
	public void testLoginFailsForInvalidDobFormat() {
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "01-01-1990"));
		Assert.assertFalse(LoginService.login("ahsan", "ahsan_pass", "1990-13-01"));
	}

	@Test
	public void testLoginIsCaseSensitive() {
		Assert.assertFalse(LoginService.login("Ahsan", "ahsan_pass", "1990-01-01"));
		Assert.assertFalse(LoginService.login("ahsan", "AHSAN_PASS", "1990-01-01"));
	}
}
