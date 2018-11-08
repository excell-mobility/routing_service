package restrouting;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import restrouting.component.RoutingService;
import restrouting.connector.TravelTimesConnector;
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
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
	    RoutingController.class,
	    RoutingService.class,
	    TravelTimesConnector.class
	})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Docket routingApi(ServletContext servletContext) { 
        return new Docket(DocumentationType.SWAGGER_2)
          .groupName("excell-routing-api")
          .select()
          .apis(RequestHandlerSelectors.any()) 
          .paths(PathSelectors.regex("/v1/routing"))
          .build()
          .genericModelSubstitutes(ResponseEntity.class)
          .protocols(Sets.newHashSet("https"))
          .host("excell-mobility.de")
          .securitySchemes(Lists.newArrayList(apiKey()))
          .securityContexts(Lists.newArrayList(securityContext()))
          .apiInfo(apiInfo())
          .pathProvider(new RelativePathProvider(servletContext) {
                @Override
                public String getApplicationBasePath() {
                    return "/integration/api/v1/service-request/routingservice";
                }
            });
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
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[0];
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
          "Der Routing Service erwartet Hoch- bzw. Rechtswerte für die Start- und Zielkoordinaten."
          + " Danach wird eine Liste von GPS Koordinaten zurückgegeben, die den kürzesten Weg repräsentiert."
          + " Zur Routenberechnung wird dabei der Dijkstra Algorithmus angewandt.\n\n"
          + "This routing service requires a latitude and longitude input for two locations to calculate the shortest route in between."
          + " The routing is based on the Dijkstra algorithm.\n",
          "1.0",
          "Use only for testing",
          new Contact(
        		  "Beuth Hochschule für Technik Berlin - Labor für Rechner- und Informationssysteme - MAGDa Gruppe",
        		  "https://projekt.beuth-hochschule.de/magda/poeple",
        		  "fkunde@beuth-hochschule"),
          "Link to source code",
          "https://github.com/excell-mobility/routing_service",
          new ArrayList<VendorExtension>());
        return apiInfo;
    }

}