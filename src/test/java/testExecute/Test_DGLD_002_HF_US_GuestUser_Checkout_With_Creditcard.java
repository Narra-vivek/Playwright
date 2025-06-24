package testExecute;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.*;

import com.microsoft.playwright.Page;

import Testcomponents.Hydroflask_Helper;
import Utilities.AllureEnvironmentWriter;
import Utilities.ConfigReader;
import Utilities.PlaywrightFactory;
import Utilities.log;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
@Listeners({listeners.TestMethodLogger.class})
public class Test_DGLD_002_HF_US_GuestUser_Checkout_With_Creditcard {

    private PlaywrightFactory factory;
    private Page page;
    private Hydroflask_Helper Hydro = new Hydroflask_Helper(TESTDATA_PATH, SHEET_NAME);
    private static final String TESTDATA_PATH = "Hydroflask/HydroflaskTestData.xlsx";
	private static final String SHEET_NAME = "Dataset";
	
    @BeforeTest
    public void setupAll() {
        log.info("Starting test on thread: " + Thread.currentThread().getId());
        String configPath = "src/test/resources/Config/Hydroflask/config.properties";
        ConfigReader.load(configPath);
        AllureEnvironmentWriter.writeEnvironmentProperties();
    }

    @BeforeMethod
    public void setupBrowser() {
        factory = new PlaywrightFactory();
        page = factory.initBrowser(); // ✅ Browser starts per test method
    }

    @Test(description = "Guest User Checkout with CC Payment")
    @Description("This test case is to validate the guest user adding product to cart and checkout using the Credit card payment")
    @Feature("Guest user Checkout")
    
    public void Validating_GuestUser_checkout_Functionality() {
        
        try {
            Hydro.searchProduct(page, "Product_name");
//            Hydro.SRP_page(page);
//            Hydro.PDP_Page(page);
//            Hydro.minicart_Add_Address(page,Register_Address);
//            Hydro.Shippment_and_Payment(page);
        } catch (Exception | Error e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @AfterMethod
    public void tearDown(Method method) throws IOException, InterruptedException {
    	 if (page != null) {
         	factory.getVideo();
             page.close();
             factory.VideoRecord(method);
             factory.recordTrace(method);
        }
        if (factory != null) {
            factory.tearDown();
            log.info("🔚 Closed browser for: " + method.getName());
        }
    }
}
