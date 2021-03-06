package org.andidev.webdriverextension.generator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import org.andidev.webdriverextension.internal.generator.AbstractGeneratorProcessor;
import org.andidev.webdriverextension.internal.generator.SiteAwareBuilder;

@SupportedAnnotationTypes({"org.andidev.webdriverextension.generator.annotations.Generate"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class SiteAwareGeneratorProcessor extends AbstractGeneratorProcessor {

    @Override
    public void generateClasses() {
        for (TypeElement siteClass : getAnnotatedSiteClasses()) {
            generateSiteAwareClass(siteClass);
        }
    }

    private void generateSiteAwareClass(TypeElement siteClass) {
        debug("Generating SiteAware class with Site Classes: " + siteClass.getSimpleName() + " and Page Classes: " + getAnnotatedPageClasses(siteClass));
        SiteAwareBuilder builder = new SiteAwareBuilder(processingEnv,
                siteClass,
                getAnnotatedSiteClasses(),
                getAnnotatedPageClasses(siteClass));
        builder.build();
    }
}