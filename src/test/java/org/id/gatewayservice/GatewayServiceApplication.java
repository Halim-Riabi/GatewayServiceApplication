package org.id.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	 @Bean
	 DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}

	@Bean
	RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route("countries",r->r
						.path("/restcountries/**")
						.filters(f->f
								.addRequestHeader("x-rapidapi-host","restcountries-v1.p.rapidapi.com")
								.addRequestHeader("x-rapidapi-key", "fe5e774996msh4eb6e863d457420p1d2ffbjsnee0617ac5078")
								.rewritePath("/restcountries/(?<segment>.*)","/${segment}")
								. circuitBreaker(c->c.setName("rest-countries")
								.setFallbackUri("forward:/restCountriesFallback"))

						)
						.uri("https://restcountries-v1.p.rapidapi.com")

						)
//						.route(r->r.path("/muslimsalat/**")
//										.filters(f->f
//												.addRequestHeader("x-rapidapi-host","muslimsalat.p.rapidapi.com")
//												.addRequestHeader("x-rapidapi-key", "fe5e774996msh4eb6e863d457420p1d2ffbjsnee0617a5078")
//														.rewritePath("/muslimsalat/(?<segment>.*)","/${segment}")
//												)
//												.uri("https://muslimsalat.p.rapidapi.com").id("countries1")
//												)
												.build();
							}




}
