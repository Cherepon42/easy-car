package by.easycar.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
public class SpringConfig {








    @Bean
    public Validator getValidator() {
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    public OpenAPI swaggerAuthenticationConfiguration() {
        OpenAPI openAPI = new OpenAPI();
        Components components = new Components();
        components.addSecuritySchemes("User JWT", getJwtForUserScheme());
        components.addSecuritySchemes("Admin JWT", getJwtForAdminScheme());
        openAPI.addSecurityItem(new SecurityRequirement().addList("Authentication")).components(components);
        openAPI.info(new Info()
                .title("Easy car")
                .version("1.0")
                .description("On this site you can find the car for buying or you can put your own car for sell.")
                .contact(getContact())
        );
        return openAPI;
    }

    private SecurityScheme getJwtForUserScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.name("User JWT")
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return securityScheme;
    }

    private SecurityScheme getJwtForAdminScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.name("Admin JWT")
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        return securityScheme;
    }

    private Contact getContact() {
        Contact contact = new Contact();
        contact.setEmail("UladCherap@yandex.by");
        contact.setName("Vlad");
        contact.setUrl("https://github.com/Cherepon42/easy-car");
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }
}