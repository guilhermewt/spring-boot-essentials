package academy.devdojo.springboot2.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class DevdojoWebMvcConfigurer implements WebMvcConfigurer{
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addArgumentResolvers(resolvers);
		
		PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
	    pageHandler.setFallbackPageable(PageRequest.of(0, 5));	
	    resolvers.add(pageHandler);
	}
}
