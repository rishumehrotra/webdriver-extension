package org.andidev.webdriverextension.junitrunner;

import static org.andidev.webdriverextension.Bot.open;
import org.junit.Test;
import org.andidev.webdriverextension.ThreadDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class InheritedRunnerTest extends InheritedRunner {

    @Test
    public void test() {
        System.out.println("browserName = " + ((RemoteWebDriver) ThreadDriver.getDriver()).getCapabilities().getBrowserName());
        System.out.println("browserVersion = " + ((RemoteWebDriver) ThreadDriver.getDriver()).getCapabilities().getVersion());
        System.out.println("platformName = " + ((RemoteWebDriver) ThreadDriver.getDriver()).getCapabilities().getPlatform().toString());

        open("http://www.google.com");
        System.out.println("Ran InheritedRunner successfully!");
    }
}