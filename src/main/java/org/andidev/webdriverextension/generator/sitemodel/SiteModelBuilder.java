package org.andidev.webdriverextension.generator.sitemodel;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.CodeWriter;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import java.io.IOException;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import static org.andidev.annotationprocessorutils.ElementUtils.*;
import org.andidev.annotationprocessorutils.ProcessingEnvironmentCodeWriter;
import org.andidev.webdriverextension.utils.GeneratorUtils;
import org.andidev.webdriverextension.WebSite;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;

public class SiteModelBuilder implements Builder<Boolean> {

    // Input Elements
    private ProcessingEnvironment processingEnv;
    private TypeElement siteObjectElement;
    private Set<TypeElement> pageObjectElements;
    private JCodeModel codeModel;
    // JClasses
    private JDefinedClass siteModelClass;
    private JClass webSiteClass;
    private JClass siteObjectClass;
    private Set<JClass> pageObjectClasses;

    public SiteModelBuilder(ProcessingEnvironment processingEnv,
            TypeElement siteObjectElement, Set<TypeElement> pageObjectElements) {
        this.processingEnv = processingEnv;
        this.siteObjectElement = siteObjectElement;
        this.pageObjectElements = pageObjectElements;
    }

    @Override
    public Boolean build() {
        try {
            init();
            fields();
            constructors();
            methods();
            generate();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SiteModelBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (JClassAlreadyExistsException ex) {
            Logger.getLogger(SiteModelBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }


    }

    private void init() throws JClassAlreadyExistsException {
        codeModel = new JCodeModel();
        siteModelClass = codeModel._class(JMod.PUBLIC | JMod.ABSTRACT, getPackageName(siteObjectElement) + "." + StringUtils.capitalize(GeneratorUtils.getName(siteObjectElement)) + "Model", ClassType.CLASS);
        siteModelClass._extends(codeModel.ref(WebSite.class));
        siteObjectClass = codeModel.ref(siteObjectElement.getQualifiedName().toString());
        pageObjectClasses = getCodeModelRefs(pageObjectElements);
    }

    private void fields() {
        // Declare PageObjects
        for (JClass pageObjectClass : pageObjectClasses) {
            siteModelClass.field(JMod.PUBLIC, pageObjectClass, getPageObjectFieldName(pageObjectClass));
        }
    }

    private void constructors() {
    }

    private void methods() {
    }

    private void generate() throws IOException {
        CodeWriter codeWriter = new ProcessingEnvironmentCodeWriter(processingEnv);
        codeModel.build(codeWriter);
    }

    private Set<JClass> getCodeModelRefs(Set<TypeElement> elements) {
        Set<JClass> codeModeModelRefs = new LinkedHashSet<JClass>();
        for (TypeElement pageObjectElement : pageObjectElements) {
            codeModeModelRefs.add(codeModel.ref(pageObjectElement.getQualifiedName().toString()));
        }
        return codeModeModelRefs;
    }

    private void newPageObjects() {
        JMethod method = siteModelClass.method(JMod.PRIVATE, void.class, "newPageObjects");
        for (JClass pageObjectClass : pageObjectClasses) {
            method.body().assign(JExpr.ref(getPageObjectFieldName(pageObjectClass)), JExpr._new(pageObjectClass));
        }
    }

    private String getSiteObjectFieldName() {
        return GeneratorUtils.getName(siteObjectElement);
    }

    private String getPageObjectFieldName(JClass pageObjectClass) {
        for (TypeElement pageObjectElement : pageObjectElements) {
            if (pageObjectElement.getQualifiedName().toString().equals(pageObjectClass.fullName())) {
                return GeneratorUtils.getName(pageObjectElement);
            }
        }
        return null;
    }
}