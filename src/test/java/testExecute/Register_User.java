package testExecute;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.WaitForOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class Register_User {

	
	public  void Register(String[] args) throws Exception {
		Browser browser = null;
		Page page = null;
	   try
	   {
		   
		   Dimension Screensize=Toolkit.getDefaultToolkit().getScreenSize();
		   int width=(int) Screensize.getWidth();
		   int Height=(int) Screensize.getHeight();
		   System.out.println(width + ":" + Height);
		   Playwright pw=Playwright.create();
		   
		browser=Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
	
		//maximize the window 
		BrowserContext context=browser.newContext(new Browser.NewContextOptions().setViewportSize(width,Height));
	 page=context.newPage();
		
		//navigate to the Hydroflask Home page
		page.navigate("https://www.hydroflask.com/");
		page.locator("#customer-menu").click();
		page.locator("a[title='Sign In']").click();
		PlaywrightAssertions.assertThat(page.locator("//fieldset[@class='fieldset login']//legend/h2")).containsText("Sign In");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/SignIn.png")).setFullPage(true));
		
		//login for the register user
		String Username="hmaram@helenoftroy.com";
		String password="Lotus@123";
		page.locator("#email").fill(Username);
		page.locator("#pass").fill(password);
		page.locator("(//button[contains(@class,'btn btn-primary')])[1]").click();
		PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Hydro Flask"));
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/Login.png")).setFullPage(true));
		//search the product
		page.locator("button[id='truste-consent-button']").click();
		
		//search the product 
		page.locator("//button[@id='menu-search-icon' and @x-show='!searchOpen']").click();
		String Product="Medium Bottle Boot";
		page.locator("#autocomplete-0-input").fill(Product);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/Search.png")).setFullPage(true));
		page.keyboard().press("Enter");
		
		//navigated to the SRP page 
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/SRP.png")).setFullPage(true));
		page.locator("//img[@alt='" + Product + "']").click();
		PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Medium Bottle Boot | Hydro Flask"));
		page.waitForLoadState(LoadState.LOAD);
		
		
		//Navigated to the PDP page
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/PDP.png")).setFullPage(true));
		page.locator("//button[@id='product-addtocart-button']").click();
		page.waitForLoadState(LoadState.LOAD);
		page.setDefaultNavigationTimeout(6000);
		
		
		//checkout button from the Mini cart
	page.locator("button[class*='inline-flex btn btn-primary text']").click();

	
	//Adding new address for the Register user
		String Firstname="QA";
		String Lastname="TEST";
		String Address="844 N Colony Rd";
		String City="Wallingford";
		String Region="Connecticut";
		String Zipcode="06492";
		String PhoneNumber="9898989898";
		page.locator("button[class*='btn dr:btn-secondary-checkout hf:btn-primary']").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(20000));
		page.locator("button[class*='btn dr:btn-secondary-checkout hf:btn-primary']").click();
		page.locator("form[id='shipping'] input[name='firstname']").fill(Firstname);
		page.locator("form[id='shipping'] input[name='lastname']").fill(Lastname);
		page.locator("input[name='street[0]']").fill(Address);
		page.selectOption("select[id='shipping-region']", Region);
		page.locator("input[id='shipping-city']").fill(City);
		page.locator("form[id='shipping'] input[name='postcode']").fill(Zipcode);
		page.locator("form[id='shipping'] input[name='telephone']").fill(PhoneNumber);
		page.locator("//button[contains(@class,'btn btn-primary w-full os:uppercase')]").click();
		page.waitForLoadState(LoadState.LOAD);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/Newaddress.png")).setFullPage(true));
		
		//selecting the shipping methods 
		page.locator("//span[text()='FedEx Ground']").click();
		
		
		String Name=page.locator("(//iframe[@role='presentation' and contains(@allow,'payment *; publickey-credentials')])[1]").getAttribute("name");
		System.out.println("iframe[name='" + Name + "']");
		//Entering to the Iframe
		FrameLocator frame=page.frameLocator("iframe[name='" + Name + "']");
		frame.locator("label[for='Field-numberInput']").click();
		String cardnumber="4242424242424242";
		String expmonthyear="02/45";
		String CVV="123";
		frame.locator("input[id='Field-numberInput']").fill(cardnumber);
		frame.locator("input[id='Field-expiryInput']").fill(expmonthyear);
		frame.locator("input[id='Field-cvcInput']").fill(CVV);
		page.waitForLoadState(LoadState.LOAD);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/Payment.png")).setFullPage(true));

		
	   }
	   finally
	   {
//		   page.close();
//		   browser.close();
	   }
	
	}

}
