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

public class TC03_AddToWishlist extends BaseClass {

    private static final Logger log = LogManager.getLogger(TC03_AddToWishlist.class);

    @Test(groups = { "regression" }, retryAnalyzer = RetryAnalyzer.class)
    public void testAddToWishList() {
        log.info("Starting test: testAddToWishList");

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

            log.debug("Adding product to the wishlist.");
            AddToCartPage atcp = new AddToCartPage(getDriver());
            atcp.addToWishList();

            log.debug("Verifying success message for adding to wishlist.");
            boolean wishListSuccess = atcp.isWishListSuccessMessageDisplayed();
            log.info("Wishlist success message status: {}", wishListSuccess);

            try {
                Assert.assertTrue(wishListSuccess, "Add to wishlist verification failed.");
                log.info("Product successfully added to wishlist.");
            } catch (AssertionError e) {
                log.error("Assertion failed: Add to wishlist verification failed", e);
                Assert.fail("Add to wishlist verification failed: " + e.getMessage());
            }

        } catch (Exception e) {
            log.error("Unexpected error occurred during test execution: {}", e.getMessage(), e);
            Assert.fail("Test encountered an unexpected error: " + e.getMessage());
        }

        log.info("Test execution completed: testAddToWishList");
    }
}