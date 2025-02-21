package testCases;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AddToCartPage;
import pageObjects.CategoryPage;
import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC05_AddToCart extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC05_AddToCart.class);

    @Test(groups = { "smoke", "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testAddToCart() {
        log.info("Starting test: testAddToCart");

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
            AddToCartPage atcp = new AddToCartPage(getDriver());
            atcp.setDeliveryDate();
            atcp.clickAddToCart();

            log.debug("Validating success message.");
            boolean successMessageDisplayed = atcp.verifySuccessMessage();
            log.info("Success message status: {}", successMessageDisplayed);

            try {
                Assert.assertTrue(successMessageDisplayed, "Add to cart verification failed.");
                log.info("Product successfully added to cart.");
            } catch (AssertionError e) {
                log.error("Assertion failed: Add to cart verification failed", e);
                Assert.fail("Add to cart verification failed: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testAddToCart");
    }
}