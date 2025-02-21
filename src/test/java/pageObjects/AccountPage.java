package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage {
	public AccountPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement confirmationText_MyAccount;
	
	@FindBy(xpath = "//span[@class='caret']")
	WebElement dropDown_MyAccount;

	@FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']")
	WebElement link_Logout;

	public WebElement getMyAccountConfirmation() {
		return confirmationText_MyAccount;
		
	}
	public void clickMyAccountDropDown() {
		dropDown_MyAccount.click();
	}

	public void clickLogout() {
		link_Logout.click();
	}}