package Testcomponents;

import com.microsoft.playwright.Page;

public class Dem {
Page page;

public Dem(Page page) {
	this.page=page;
}

public String HomePageTitle() {
	return page.title();
}

	public String HomePageURL() {
		return page.url();
}
}
