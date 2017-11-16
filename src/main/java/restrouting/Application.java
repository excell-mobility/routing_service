package restrouting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import restrouting.component.RoutingService;
import restrouting.controller.RoutingController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
	    RoutingController.class,
	    RoutingService.class
	})

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket routingApi() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("excell-routing-api")
          .select()
          .apis(RequestHandlerSelectors.any()) 
          .paths(PathSelectors.regex("/v1/routing||/api/v1/routing"))
          .build()
          .genericModelSubstitutes(ResponseEntity.class)
          .protocols(Sets.newHashSet("https"))
          //.host("localhost:43434")
          //.host("141.64.5.234/excell-routing-api")
          .host("dlr-integration.minglabs.com/api/v1/service-request/routingservice")
          .securitySchemes(Lists.newArrayList(apiKey()))
          .securityContexts(Lists.newArrayList(securityContext()))
          .apiInfo(apiInfo())
          ;
    }

	private ApiKey apiKey() {
		return new ApiKey("api_key", "Authorization", "header");
	}
	
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/*.*"))
            .build();
    }

    private List<SecurityReference> defaultAuth() {
    	List<SecurityReference> ls = new ArrayList<>();
    	AuthorizationScope authorizationScope
    		= new AuthorizationScope("global", "accessEverything");
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    	authorizationScopes[0] = authorizationScope;
    	SecurityReference s = new SecurityReference("api_key", authorizationScopes);
    	ls.add(s);
    	return ls;
    }

	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Token", ApiKeyVehicle.HEADER, "Authorization", ",");
	}
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "ExCELL Routing API",
          "Diese API stellt optimales Routing für die ExCELL Testregion zur Verfügung. "
          + "Der Routing Service erwartet Hoch- und Rechtswerte für die Start- und Zielkoordinaten. "
          + "Danach wird eine Liste von GPS Koordinaten zurückgegeben, die den kürzesten Weg repräsentiert. "
          + "Zur Routenberechnung wird dabei der Dijkstra Algorithmus angewandt.",
          "Version 1.0",
          "Use only for testing",
          new Contact(
        		  "Felix Kunde, Stephan Pieper",
        		  "https://projekt.beuth-hochschule.de/magda/poeple",
        		  "fkunde@beuth-hochschule"),
          "Apache 2",
          "http://www.apache.org/licenses/LICENSE-2.0",
          new ArrayList<VendorExtension>());
        return apiInfo;
    }

}