package com.mg.clog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.resource.WebJarsResourceResolver;

import static org.springdoc.core.Constants.CLASSPATH_RESOURCE_LOCATION;

@Configuration
public class WebConfig implements WebFluxConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**")
      .addResourceLocations(CLASSPATH_RESOURCE_LOCATION + "/webjars/")
      .resourceChain(true)
      .addResolver(new WebJarsResourceResolver());
  }
}
