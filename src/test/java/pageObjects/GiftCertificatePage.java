package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class GiftCertificatePage extends BasePage {
	public GiftCertificatePage(WebDriver driver) {
		super(driver);
	}

	// Locators using @FindBy

	@FindBy(id = "input-to-name")
	private WebElement recipientName;

	@FindBy(id = "input-to-email")
	private WebElement recipientEmail;

	@FindBy(id = "input-from-name")
	private WebElement senderName;

	@FindBy(id = "input-from-email")
	private WebElement senderEmail;

	@FindBy(xpath = "//input[@value='7']")
	private WebElement giftThemeOption;

	@FindBy(name = "agree")
	private WebElement termsCheckbox;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//h1[normalize-space()='Purchase a Gift Certificate']")
	private WebElement successMessage;

	// Methods to interact with elements

	public void enterRecipientDetails(String name, String email) {
		wait.until(ExpectedConditions.visibilityOf(recipientName)).sendKeys(name);
		recipientEmail.clear();
		recipientEmail.sendKeys(email);
	}

	public void enterSenderDetails(String name, String email) {
		senderName.clear();
		senderName.sendKeys(name);
		senderEmail.clear();
		senderEmail.sendKeys(email);
	}

	public void selectGiftTheme() {
		giftThemeOption.click();
	}

	public void agreeToTerms() {
		termsCheckbox.click();
	}

	public void clickContinue() {
		continueButton.click();
	}

	public boolean isPurchaseSuccessMessageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
	}
}
