package testCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.GiftCertificatePage;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC06_PurchaseAGiftCertificate extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC06_PurchaseAGiftCertificate.class);

    @Test(groups = { "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testPurchaseAGiftCertificate() {
        log.info("Starting test: testPurchaseAGiftCertificate");

        try {
            if (getDriver() == null) {
                log.error("WebDriver instance is null, aborting test.");
                Assert.fail("WebDriver is null, cannot continue test.");
            }

            log.debug("Navigating to HomePage and clicking Gift Certificate link.");
            HomePage hp = new HomePage(getDriver());
            hp.clickGiftCertificateLink();

            log.debug("Filling out gift certificate details.");
            GiftCertificatePage gcp = new GiftCertificatePage(getDriver());
            gcp.enterRecipientDetails("John", "john@gmail.com");
            gcp.enterSenderDetails("Sid", "sid@cloudberry.services");
            gcp.selectGiftTheme();
            gcp.agreeToTerms();
            gcp.clickContinue();

            log.debug("Verifying purchase confirmation message.");
            boolean purchaseSuccess = gcp.isPurchaseSuccessMessageDisplayed();
            log.info("Purchase success message status: {}", purchaseSuccess);

            try {
                Assert.assertTrue(purchaseSuccess, "Gift certificate purchase verification failed.");
                log.info("Gift certificate successfully purchased.");
            } catch (AssertionError e) {
                log.error("Assertion failed: Gift certificate purchase verification failed", e);
                Assert.fail("Gift certificate purchase verification failed: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testPurchaseAGiftCertificate");
    }
}
	
