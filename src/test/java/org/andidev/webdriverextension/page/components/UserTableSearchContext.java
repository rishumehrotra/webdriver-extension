package org.andidev.webdriverextension.page.components;

import java.util.List;
import org.andidev.webdriverextension.WebComponent;
import org.andidev.webdriverextension.annotations.ResetSearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserTableSearchContext extends WebComponent {

    @ResetSearchContext
    @FindBy(css = "#search-query")
    public WebElement searchQuery;
    @ResetSearchContext
    @FindBy(css = "#todo-list li")
    public List<WebElement> todos;
}
