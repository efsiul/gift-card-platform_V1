package com.pt.lfc.gatewaymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayMicroserviceApplication.class, args);
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator(ReactiveDiscoveryClient reactiveDiscoveryClient,
																				DiscoveryLocatorProperties discoveryLocatorProperties){
		return new DiscoveryClientRouteDefinitionLocator(reactiveDiscoveryClient, discoveryLocatorProperties);
	}

}
