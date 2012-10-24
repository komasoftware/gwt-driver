package com.colinalworth.gwtdriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.colinalworth.gwtdriver.models.GwtWidget.ByWidget;
import com.google.gwt.user.client.ui.Widget;

public class ByGwtClass extends By {
	private final String className;
	private final WebDriver driver;
	public ByGwtClass(String className, WebDriver driver) {
		this.className = className;
		this.driver = driver;
	}
	public ByGwtClass(Class<? extends Widget> clazz, WebDriver driver) {
		this(clazz.getName(), driver);
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		List<WebElement> elts = context.findElements(new ByWidget(driver));

		List<WebElement> ret = new ArrayList<WebElement>();
		for (WebElement elt : elts) {
			String matches = (String) ModuleUtilities.executeExportedFunction("instanceofwidget", driver, className, elt);
//			String matches = (String) ((JavascriptExecutor)driver).executeAsyncScript("_simplewidgets_se.apply(this, arguments)", "instanceofwidget", className, elt);

			System.out.println("ByGwtClass  " + matches + "  " + elt);
			if ("true".equals(matches)) {
				ret.add(elt);
			}
		}
		return ret;
	}

}