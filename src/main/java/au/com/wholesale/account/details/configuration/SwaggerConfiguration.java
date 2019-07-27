package au.com.wholesale.account.details.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfiguration {

    @Value("${swagger.base.package.to.scan:au.com.wholesale.account.details.none.specified}")
    private String basePackageToScan;

    @Value("${swagger.api.info.team.name:Mayur}")
    private String teamName;

    @Value("${swagger.api.info.title:Swagger API}")
    private String title;

    @Value("${swagger.api.info.description:Swagger API}")
    private String description;

    @Value("${project.version}")
    private String version;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackageToScan))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    @Bean
    public ApiInfo apiInfo() {
        String buildVersion = version;
        return new ApiInfo(title, description, buildVersion, "",
                           apiContact(), "", "", new ArrayList<>());
    }

    @Bean
    public Contact apiContact() {
        return new Contact(teamName, "", "");
    }


    @Bean
    public UiConfiguration uiConfigFullInteraction() {
        log.info("**** Load Swagger UI with FULL interaction ****");
        return uiConfiguration();
    }

    private UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
                .defaultModelRendering(ModelRendering.MODEL)
                .validatorUrl(null)
                .docExpansion(DocExpansion.NONE)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .tagsSorter(TagsSorter.ALPHA)
                .build();
    }
}
