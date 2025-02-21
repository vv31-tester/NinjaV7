package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;
import utilities.RetryAnalyzer;

public class TC02_Login extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC02_Login.class);

    @Test(groups = { "smoke", "regression", "datadriven" }, 
          dataProvider = "LoginData", 
          dataProviderClass = DataProviders.class, 
          retryAnalyzer = RetryAnalyzer.class)
    public void testLogin(String email, String pwd) {
        log.info("Starting test: testLogin with Email: {} and Password: {}", email, pwd);

        try {
            if (getDriver() == null) {
                log.error("WebDriver instance is null, aborting test.");
                Assert.fail("WebDriver is null, cannot continue test.");
            }

            log.debug("Navigating to HomePage");
            HomePage hp = new HomePage(getDriver());
            hp.clickMyAccount();
            hp.goToLogin();

            log.debug("Entering login credentials.");
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(email);
            lp.setPwd(pwd);
            lp.clickLogin();

            log.debug("Verifying login success.");
            AccountPage ap = new AccountPage(getDriver());
            boolean status = ap.getMyAccountConfirmation().isDisplayed();
            log.info("Login success status: {}", status);

            try {
                Assert.assertTrue(status, "Login verification failed.");
                log.info("Login verification passed.");
                
                // Logging out after successful login
                ap.clickMyAccountDropDown();
                ap.clickLogout();
                log.info("User logged out successfully.");

            } catch (AssertionError e) {
                log.error("Assertion failed: Login verification failed", e);
                Assert.fail("Login verification failed: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testLogin");
        
    }
}