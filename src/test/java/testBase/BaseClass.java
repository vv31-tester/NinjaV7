package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseClass {

	private static final Logger log = LogManager.getLogger(BaseClass.class);
	private Properties p;
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	@BeforeClass(groups = { "smoke", "regression", "datadriven" })
	@Parameters({ "os", "browser" })
	public void openApp(String os, String br) {
		log.info("Starting Test Execution - OS: {}, Browser: {}", os, br);

		try {
			// Load properties file
			FileReader file = new FileReader(".//src//test//resources//config.properties");
			p = new Properties();
			p.load(file);
			log.debug("Successfully loaded config.properties file.");

			WebDriver localDriver = null;

			if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();

				// os
				if (os.equalsIgnoreCase("windows")) {
					capabilities.setPlatform(Platform.WIN11);
				} else if (os.equalsIgnoreCase("mac")) {
					capabilities.setPlatform(Platform.MAC);
				} else {
					System.out.println("No matching os");
					return;
				}

				String gridURL = "http://localhost:4444/wd/hub"; // Update if needed

				switch (br.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), chromeOptions.merge(capabilities));
					break;

				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), firefoxOptions.merge(capabilities));
					break;

				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					localDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), edgeOptions.merge(capabilities));
					break;

				default:
					log.error("No matching browser found: {}", br);
					return;
				}

			}

			if (p.getProperty("execution_env").equalsIgnoreCase("local")) {

				switch (br.toLowerCase()) {
				case "chrome":
					log.info("Launching Chrome Browser");
					localDriver = new ChromeDriver();
					break;
				case "edge":
					log.info("Launching Edge Browser");
					localDriver = new EdgeDriver();
					break;
				case "firefox":
					log.info("Launching Firefox Browser");
					localDriver = new FirefoxDriver();
					break;
				default:
					log.error("Invalid browser name provided: {}", br);
					throw new IllegalArgumentException("No matching browser found: " + br);
				}
			}
			driver.set(localDriver);
			log.debug("WebDriver instance set successfully.");

			// Configure WebDriver settings
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
			getDriver().manage().window().maximize();
			getDriver().get(p.getProperty("appURL"));

			log.info("Successfully launched application URL: {}", p.getProperty("appURL"));
		} catch (IOException e) {
			log.error("Error loading properties file: {}", e.getMessage(), e);
		} catch (Exception e) {
			log.error("Error initializing WebDriver: {}", e.getMessage(), e);
		}
	}

	@AfterClass(groups = { "smoke", "regression", "datadriven" })
	public void closeApp() {
		try {
			if (getDriver() != null) {
				log.info("Closing browser...");
				getDriver().quit();
				driver.remove();
				log.info("Browser closed and WebDriver instance removed.");
			}
		} catch (Exception e) {
			log.error("Error while closing the browser: {}", e.getMessage(), e);
		}
	}

	public static String captureScreen(String tname) throws IOException {
		if (getDriver() == null) {
			throw new IllegalStateException("WebDriver instance is null, cannot take screenshot.");
		}

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timeStamp + ".png";

		File sourceFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		File targetFile = new File(targetFilePath);

		Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		log.info("Screenshot captured: {}", targetFilePath);

		return targetFilePath;
	}
}