package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LoginPage extends BasePage  {
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txt_Email;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txt_Password;

	@FindBy(xpath = "//input[@value='Login']")
	WebElement btn_Login;

	public void setEmail(String email) {
		txt_Email.sendKeys("test312@test.com");
	}

	public void setPwd(String pwd) {
		txt_Password.sendKeys("Test123@");
	}

	public void clickLogin() {
		btn_Login.click();
	}

}
