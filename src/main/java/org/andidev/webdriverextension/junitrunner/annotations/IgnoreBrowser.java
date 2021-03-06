package org.andidev.webdriverextension.junitrunner.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IgnoreBrowser {
    public String browserName();
    public String version() default "";
    public String platform() default "";
    public BooleanOption javascriptEnabled() default BooleanOption.ANY;
    public String desiredCapabilities() default "";
}
