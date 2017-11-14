package restrouting;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

//import com.google.common.collect.Sets;




import com.google.common.base.Predicates;

import restrouting.component.RoutingService;
import restrouting.controller.RoutingController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
//import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
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
//          .protocols(Sets.newHashSet("https"))
//          .host("localhost:43434")
          .host("141.64.5.234/excell-routing-api")
          .apiInfo(apiInfo())
          ;
    }
    
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
          "ExCELL Routing API",
          "Diese API stellt optimales Routing für die ExCELL Testregion zur Verfügung. "
          + "Der Routing Service erwartet zwei GPS Koordinaten, nämlich Start und Ziel. "
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