package testExecute;
 
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.regex.Pattern;
 
 
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
 
public class Register_User_Checkout_With_CC_3 {
 
	@Test
	public void Validating_Register_Checkout_with_CC_3 () throws Exception {
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
		Allure_Screenshot.captureScreenshot(page, "Home Page");
		page.locator("#customer-menu").click();
		page.locator("a[title='Sign In']").click();
		PlaywrightAssertions.assertThat(page.locator("//fieldset[@class='fieldset login']//legend/h2")).containsText("Sign In");
		Allure_Screenshot.captureScreenshot(page, "SignIn Page");
		//login for the register user
		String Username="hmaram@helenoftroy.com";
		String password="Lotus@123";
		page.locator("#email").fill(Username);
		page.locator("#pass").fill(password);
		page.locator("button[class*='btn btn-primary w-full mb-3 os:btn-secondary os:uppercase']").click();
		PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Hydro Flask"));
		Allure_Screenshot.captureScreenshot(page, "Hydroflask Home page");
		
		//search the product
		page.locator("button[id='truste-consent-button']").click();
		page.waitForLoadState(LoadState.LOAD);
		//search the product
		page.locator("//button[@id='menu-search-icon' and @x-show='!searchOpen']").click();
		String Product="Medium Bottle Boot";
		page.locator("#autocomplete-0-input").fill(Product);
		Allure_Screenshot.captureScreenshot(page, "Search Page");
		page.keyboard().press("Enter");
		
		//navigated to the SRP page
		page.waitForLoadState(LoadState.LOAD);
		Allure_Screenshot.captureScreenshot(page, "SRP Page");
		page.locator("//img[@alt='" + Product + "']").click();
		page.waitForLoadState(LoadState.LOAD);
		PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Medium Bottle Boot | Hydro Flask"));
		
		
		
		//Navigated to the PDP page
		Allure_Screenshot.captureScreenshot(page, "PDP Page");
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
		page.waitForLoadState(LoadState.LOAD);
		page.locator("button[class*='btn dr:btn-secondary-checkout hf:btn-primary']").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(25000));
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
		Allure_Screenshot.captureScreenshot(page, "New Address For Register");
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
		Allure_Screenshot.captureScreenshot(page, "payment Cc Details Page");
		
	  }
	  finally
	  {
//		  page.close();
//		  browser.close();
	  }
	
	}
 
}