package Testcomponents;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.testng.Assert;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import Utilities.ExcelReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;



public class Hydroflask_Helper {
	String datafile;
	ExcelReader excelData;
	Map<String, Map<String, String>> data = new HashMap<>();
	public Hydroflask_Helper(String datafile, String sheetname) {

		excelData = new ExcelReader(datafile, sheetname);
		data = excelData.getExcelValue();
		this.data = data;

	}
	
	@Feature("Register User Login")
	@Story("Verify the Register user login Functionality")
	@Description("Validating the Register User Login")
	public void loginWithRegisteredUser(Page page,String Dataset) {
	    try {
	        String Email = data.get(Dataset).get("Username");
	        String password = data.get(Dataset).get("Password");
	        page.locator("#email").fill(Email);
	        page.locator("#pass").fill(password);
	        page.locator("button[class*='btn btn-primary w-full mb-3 os:btn-secondary os:uppercase']").click();
	        PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Hydro Flask"));
	        Allure.step("After Signin navigated to the Hydroflask Home Page", () -> {
	        	   Allure_Screenshot.captureScreenshot(page, "Hydroflask Home page");
	        }); 
	    } catch (Exception | Error e) {
	        e.printStackTrace();
	        Allure_Screenshot.captureScreenshot(page, "Failed to land on Hydroflask home page");
	        Assert.fail("Login failed.");
	    }
	}
	
	@Feature("Register User Search")
	@Story("Verify the Register user Search Functionality")
	@Description("Validating the Register User Search page")
	public void searchProduct(Page page, String Dataset) {
		String product =data.get(Dataset).get("Product");
	    try {
	        page.locator("button[id='truste-consent-button']").click();
	        page.waitForLoadState(LoadState.LOAD);
	        page.locator("//button[@id='menu-search-icon' and @x-show='!searchOpen']").click();
	        page.locator("#autocomplete-0-input").fill(product);
	        Allure.step("When we click on the searchbar is enabled", () -> {
	        	 Allure_Screenshot.captureScreenshot(page, "Search Page");	
	        });
	        page.keyboard().press("Enter");
	    } catch (Exception | Error e) {
	        e.printStackTrace();
	        Allure_Screenshot.captureScreenshot(page, "Failed to land on SRP page");
	        Assert.fail();
	    }

}
	@Feature("SignIn Page")
	@Story("Verify the SiginIn Page")
	@Description("Validating the navigated to the SignIn")
	public void SignIn_page(Page page)
	{
		try
		{
		Allure.step("Navigate to the Home Page", () ->{
			Allure_Screenshot.captureScreenshot(page, "Home Page");	
		}); 
		page.locator("#customer-menu").click();
		page.locator("a[title='Sign In']").click();
		PlaywrightAssertions.assertThat(page.locator("//fieldset[@class='fieldset login']//legend/h2")).containsText("Sign In");
		Allure.step("Navigated to the SignIn page" , () -> {
			Allure_Screenshot.captureScreenshot(page, "SignIn Page");
		});
		}
		catch (Exception | Error e) {
	        e.printStackTrace();
	        Allure_Screenshot.captureScreenshot(page, "Failed to land on SignIN page");
	        Assert.fail();
	    }
		
	}
	
	@Feature("Register user SRP Page")
	@Story("Verify the Register user SRP Page")
	@Description("Validating the navigated to the SRP Page")
	public void SRP_page(Page page) {
		String Product="Medium Bottle Boot";
	try {
		page.waitForLoadState(LoadState.LOAD);
		String text=page.locator("(//label[@for='srp-sort-by'])[1]").textContent();
		System.out.println(text);
		Allure.step("Navigated to the SRP page", () -> {
            Allure_Screenshot.captureScreenshot(page, "SRP Page"); 
        });
		page.waitForLoadState(LoadState.LOAD);
		page.locator("//img[@alt='" + Product + "']").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(50000));
		page.locator("//img[@alt='" + Product + "']").click();
		page.waitForLoadState(LoadState.LOAD);
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			Allure_Screenshot.captureScreenshot(page, "Failed to land on PDP page");
			Assert.fail();
		}
	}
	
	@Feature("Register user PDP Page")
	@Story("Verify the Register user PDP Page")
	@Description("Validating the navigated to the PDP Page")
	
	public void PDP_Page(Page page) {
		try {
			PlaywrightAssertions.assertThat(page).hasTitle(Pattern.compile("Medium Bottle Boot | Hydro Flask"));
			Allure.step("Navigate to the PDP page", () -> {
				Allure_Screenshot.captureScreenshot(page, "PDP Page");
			});
			page.locator("//button[@id='product-addtocart-button']").click();
			page.waitForLoadState(LoadState.LOAD);
				}
				catch(Exception | Error e)
				{
					e.printStackTrace();
					Allure_Screenshot.captureScreenshot(page, "Failed to land on PDP page");
					Assert.fail();
				}

	}
	
	@Feature("Register user Minicart & Add Address")
	@Story("Verify the Register user Minicart and Add Address")
	@Description("Validating the address added to the Register User")
	public void minicart_Add_Address(Page page, String Dataset) {
		try
		{
   page.locator("button[class*='inline-flex btn btn-primary text']").click();
   page.waitForLoadState(LoadState.LOAD);
	String Firstname=data.get(Dataset).get("firstname");
	String Lastname=data.get(Dataset).get("lastname");
	String Address=data.get(Dataset).get("address");
	String City=data.get(Dataset).get("city");
	String Region=data.get(Dataset).get("region");
	String Zipcode=data.get(Dataset).get("zipcode");
	String PhoneNumber=data.get(Dataset).get("phonenumber");
	page.waitForLoadState(LoadState.LOAD);
	page.locator("button[class*='btn dr:btn-secondary-checkout hf:btn-primary']").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(45000));
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
	Allure.step("Add new address for Register User", () -> {
		Allure_Screenshot.captureScreenshot(page, "New Address For Register");
	});
	}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			Allure.step("Unable to add the address in the shipping page", () -> {
		    Allure_Screenshot.captureScreenshot(page, "Failed to add the address in the Shipping page");
			});
			Assert.fail();
		}
		
	}
	
	@Feature("Register user Shipping method & Payment")
	@Story("Verify the Register user shipping method and payment")
	@Description("Validating the shipping method selection and payment details entered")
	public void Shippment_and_Payment (Page page) {
		try
		{
	page.locator("//span[text()='FedEx Ground']").click();
	page.waitForLoadState(LoadState.LOAD);
	page.locator("(//iframe[@role='presentation' and contains(@allow,'payment *; publickey-credentials')])[1]").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(25000));
	String Name=page.locator("(//iframe[@role='presentation' and contains(@allow,'payment *; publickey-credentials')])[1]").getAttribute("name");
	System.out.println("iframe[name='" + Name + "']");
	FrameLocator frame=page.frameLocator("iframe[name='" + Name + "']");
	frame.locator("label[for='Field-numberInput']").click();
	String cardnumber="4242424242424242";
	String expmonthyear="02/45";
	String CVV="123";
	frame.locator("input[id='Field-numberInput']").fill(cardnumber);
	frame.locator("input[id='Field-expiryInput']").fill(expmonthyear);
	frame.locator("input[id='Field-cvcInput']").fill(CVV);
	page.waitForLoadState(LoadState.LOAD);
	Allure.step("Added the Payemnt Details", () -> {
		Allure_Screenshot.captureScreenshot(page, "payment CC Details Page");
	});
		}
		catch(Exception | Error e)
		{
			e.printStackTrace();
			Allure_Screenshot.captureScreenshot(page, "Failed to add the CC details on Payment page ");
			Assert.fail();
		}
	}
}

