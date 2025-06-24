package testExecute;

import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.lang.reflect.Method;
import com.microsoft.playwright.Page;

import Testcomponents.Hydroflask_Helper;
import Utilities.*;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
@Listeners({listeners.TestMethodLogger.class})
public class Test_DGLD_001_HF_US_RegisterUser_Checkout_With_Creditcard {

    private PlaywrightFactory factory;
    private Page page;

    private static final String TESTDATA_PATH = "Hydroflask/HydroflaskTestData.xlsx";
    private static final String SHEET_NAME = "Dataset";
    private final Hydroflask_Helper hydro = new Hydroflask_Helper(TESTDATA_PATH, SHEET_NAME);

    @BeforeTest
    public void setupAll() {
        log.info("Thread: " + Thread.currentThread().getId() + " - Loading config & env");
        ConfigReader.load("src/test/resources/Config/Hydroflask/config.properties");
        AllureEnvironmentWriter.writeEnvironmentProperties();
    }

    @BeforeMethod
    public void setupBrowser(Method method) {
        factory = new PlaywrightFactory();
        page = factory.initBrowser();
    }

    @Test(description = "Register User Checkout with CC Payment")
    

    @Description("Validate register user adds product to cart and completes checkout using credit card.")
    @Feature("Register user Checkout")
    public void Validating_RegisterUser_checkout_Functionality() {
        try {
            hydro.SignIn_page(page);
            hydro.loginWithRegisteredUser(page, "LogInDetails");
            hydro.searchProduct(page, "Product_name");
            hydro.SRP_page(page);
            hydro.PDP_Page(page);
            hydro.minicart_Add_Address(page, "Register_Address");
            hydro.Shippment_and_Payment(page);
        } catch (Exception e) {
            log.error("❌ Test failed due to exception:", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown(Method method) throws IOException, InterruptedException {
    	/** if needed enable this 
    	System.out.println("✅ Test completed. Press Enter to close browser...");	    
    	System.in.read();
    	page.pause(); 
    	*/
    	if (page != null) {
            factory.getVideo();
            page.close();
            factory.VideoRecord(method);
            factory.recordTrace(method);
        }
        if (factory != null) factory.tearDown();
        log.info("🔚 Closed browser for: " + method.getName());
    }
}
