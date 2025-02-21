package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends BasePage {

	// Constructor
	public ConfirmationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h1[normalize-space()='Your order has been placed!']")
	WebElement orderConfirmationMessage;

	public boolean verifyOrderConfirmation() {
		return orderConfirmationMessage.isDisplayed();
	}

}
