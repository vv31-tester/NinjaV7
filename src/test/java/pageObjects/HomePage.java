package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {
	public HomePage(WebDriver driver) {
		super(driver);
	}

	// Locators

	@FindBy(xpath = "//a[normalize-space()='Qafox.com']")
	WebElement content_HomePage;

	// Locators
	@FindBy(xpath = "//a[@title='My Account']")
	WebElement link_MyAccount;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	WebElement link_Login;

	@FindBy(xpath = "//a[normalize-space()='Laptops & Notebooks']")
	WebElement laptopsAndNotebooksMenu;

	@FindBy(xpath = "//a[normalize-space()='Show AllLaptops & Notebooks']")
	WebElement showAllLaptopsAndNotebooks;

	@FindBy(xpath = "//a[normalize-space()='Gift Certificates']")
	private WebElement giftCertificateLink;

	// Action Methods

	public void clickMyAccount() {
		link_MyAccount.click();
	}

	public void goToLogin() {
		link_Login.click();
	}

	public String confirmHomepage() {
		return content_HomePage.getText();
	}

	public void navigateToLaptopsAndNotebooks() {
		laptopsAndNotebooksMenu.click();
		showAllLaptopsAndNotebooks.click();
	}

	public void clickGiftCertificateLink() {
		wait.until(ExpectedConditions.elementToBeClickable(giftCertificateLink)).click();
	}

}
