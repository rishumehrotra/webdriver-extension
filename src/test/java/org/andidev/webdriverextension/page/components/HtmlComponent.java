package org.andidev.webdriverextension.page.components;

import org.andidev.webdriverextension.WebComponent;
import org.openqa.selenium.support.FindBy;

public class HtmlComponent extends WebComponent {

    @FindBy(css = ".btn-group")
    public Menu menu;
}
