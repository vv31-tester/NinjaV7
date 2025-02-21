package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

public class CategoryPage extends BasePage{
	public CategoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//a[normalize-space()='HP LP3065']")
	WebElement selectHpLaptop;

	public void selectProduct() {
		selectHpLaptop.click();
	}

}