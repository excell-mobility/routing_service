package restrouting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;

import restrouting.component.RoutingService;
import restrouting.controller.RoutingController;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = { RoutingController.class, RoutingService.class })

public class Application
{

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Docket routingApi()
  {
    return new Docket(DocumentationType.SWAGGER_2)
    		.groupName("excell-routing-api-demo")
    		.select()
    		//.apis(RequestHandlerSelectors.any())
    		//.paths(PathSelectors.any())
	        .build()
	        .genericModelSubstitutes(ResponseEntity.class)
	        //.protocols(Sets.newHashSet("https"))
	        .host("dbl43.beuth-hochschule.de/excell-routing-api-demo")
	        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo()
  {
    ApiInfo apiInfo = new ApiInfo(
    		"ExCELL Routing API", 
    		"This API provides an optimized routing for the ExCELL test area (Dresden).",
    		"Version 1.0", 
    		"Use only for testing", 
    		"fkunde@beuth-hochschule", 
    		"Apache 2", 
    		"http://www.apache.org/licenses/LICENSE-2.0");
    return apiInfo;
  }

}