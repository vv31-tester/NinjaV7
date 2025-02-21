package testCases;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AddToCartPage;
import pageObjects.CategoryPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC04_CompleteAPurchase extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC04_CompleteAPurchase.class);

    @Test(groups = { "smoke", "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testCompletePurchase() {
        log.info("Starting test: testCompletePurchase");

        try {
            if (getDriver() == null) {
                log.error("WebDriver instance is null, aborting test.");
                Assert.fail("WebDriver is null, cannot continue test.");
            }

            log.debug("Navigating to HomePage and selecting Laptops & Notebooks category.");
            HomePage hp = new HomePage(getDriver());
            hp.navigateToLaptopsAndNotebooks();

            log.debug("Selecting a product from the category.");
            CategoryPage cp = new CategoryPage(getDriver());
            cp.selectProduct();

            log.debug("Setting up delivery date and adding product to cart.");
            AddToCartPage addToCartPage = new AddToCartPage(getDriver());
            addToCartPage.setDeliveryDate();
            addToCartPage.clickAddToCart();

            log.debug("Proceeding to checkout.");
            addToCartPage.goToCheckout();

            log.debug("Logging in to complete purchase.");
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail("sid@cloudberry.services");
            lp.setPwd("Test123");
            lp.clickLogin();

            log.debug("Completing checkout process.");
            CheckoutPage checkoutPage = new CheckoutPage(getDriver());
            checkoutPage.completeCheckout();

            log.debug("Verifying order confirmation.");
            ConfirmationPage confp = new ConfirmationPage(getDriver());
            boolean orderConfirmed = confp.verifyOrderConfirmation();
            log.info("Order confirmation status: {}", orderConfirmed);

            try {
                Assert.assertTrue(orderConfirmed, "Order confirmation failed.");
                log.info("Order successfully confirmed.");
            } catch (AssertionError e) {
                log.error("Assertion failed: Order confirmation failed", e);
                Assert.fail("Order confirmation failed: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testCompletePurchase");
    }
}