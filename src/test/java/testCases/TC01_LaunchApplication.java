package testCases;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApplication extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC01_LaunchApplication.class);

    @Test(groups = { "smoke", "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testLaunchApplication() {
        log.info("Starting test: testLaunchApplication");

        try {
            if (getDriver() == null) {
                log.error("WebDriver instance is null, aborting test.");
                Assert.fail("WebDriver is null, cannot continue test.");
            }

            log.debug("Navigating to HomePage");
            HomePage hp = new HomePage(getDriver());

            log.debug("Verifying homepage confirmation message.");
            String confirmation = hp.confirmHomepage();
            log.info("Received homepage confirmation message: {}", confirmation);

            try {
                Assert.assertEquals(confirmation, "Qafox.com");
                log.info("Homepage confirmation message matched expected value.");
            } catch (AssertionError e) {
                log.error("Assertion failed: Expected 'Qafox.com' but found '{}'", confirmation, e);
                Assert.fail("Homepage confirmation message mismatch: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testLaunchApplication");
    }
}