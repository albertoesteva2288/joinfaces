package com.github.persapiens.jsfboot.myfaces;

import com.github.persapiens.jsfboot.JsfAnnotatedClassFactory;
import static com.github.persapiens.jsfboot.myfaces.MyfacesServletContextInitializer.ANOTHER_FACES_CONFIG;
import java.util.Set;
import javax.servlet.ServletException;
import static org.assertj.core.api.Assertions.assertThat;
import org.omnifaces.component.input.Form;
import org.testng.annotations.Test;
import org.omnifaces.converter.SelectItemsIndexConverter;
import org.omnifaces.validator.RequiredCheckboxValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeSuite;

@SpringApplicationConfiguration(classes = MyfacesSpringBootAutoConfiguration.class)
@WebAppConfiguration
@Test
public class MyfacesServletContextInitializerIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private MyfacesProperties myfacesProperties;

    private Set<Class<?>> classes;
    
    @BeforeSuite
    public void setupClasses() {
        MyfacesServletContextInitializer configuration = new MyfacesServletContextInitializer(null);
        
        classes = JsfAnnotatedClassFactory.builder()
            .jsfAnnotatedClassFactoryConfiguration(configuration)
            .build().find();
	}
    
	public void testOmnifacesSelectItemsIndexConverter() {        
		assertThat(classes).contains(SelectItemsIndexConverter.class);
	}
    
	public void testOmnifacesRequiredCheckboxValidator() {        
		assertThat(classes).contains(RequiredCheckboxValidator.class);
	}
    
	public void testOmnifacesFormComponent() {        
		assertThat(classes).contains(Form.class);
	}

    public void testAnotherFacesConfig() throws ServletException
    {
        MyfacesServletContextInitializer myfacesServletContextInitializer 
            = new MyfacesServletContextInitializer(myfacesProperties);

        assertThat(myfacesServletContextInitializer.getAnotherFacesConfig()).isEqualTo(ANOTHER_FACES_CONFIG);
	}

    public void testExcludeScopedAnnotations() throws ServletException
    {
        MyfacesServletContextInitializer myfacesServletContextInitializer 
            = new MyfacesServletContextInitializer(myfacesProperties);

        assertThat(myfacesServletContextInitializer.isExcludeScopedAnnotations()).isTrue();
	}

}