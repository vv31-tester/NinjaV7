package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddToCartPage extends BasePage {

	// Constructor to initialize the elements
	public AddToCartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-option225']")
	WebElement deliveryDateInput;

	@FindBy(xpath = "//button[@id='button-cart']")
	WebElement addToCartButton;

	@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
	WebElement successMessage;

	@FindBy(xpath = "//header//div[@class='col-sm-3']")
	WebElement cartIcon;

	@FindBy(xpath = "//strong[normalize-space()='Checkout']")
	WebElement checkoutButton;

	@FindBy(xpath = "//button[@type='button']//i[@class='fa fa-heart']")
	private WebElement wishListButton;

	@FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
	private WebElement successAlert;

	public void setDeliveryDate() {
		deliveryDateInput.clear();
		LocalDate today = LocalDate.now();
		LocalDate deliveryDate = today.plusDays(5);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDeliveryDate = deliveryDate.format(formatter);
		deliveryDateInput.sendKeys(formattedDeliveryDate);
	}

	public void clickAddToCart() {
		addToCartButton.click();
	}

	public boolean verifySuccessMessage() {
		return successMessage.getText().contains("Success");
	}

	public void goToCheckout() {
		cartIcon.click();
		checkoutButton.click();
	}

	public void addToWishList() {
		wait.until(ExpectedConditions.elementToBeClickable(wishListButton)).click();
	}

	public boolean isWishListSuccessMessageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(successAlert)).isDisplayed();
	}

}