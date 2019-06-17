package com.demo;

import org.springframework.boot.SpringApplication;
import com.demo.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.filters.CorsFilter;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.demo.controller.HelloController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Dome;
import com.model.Dome2;
import com.model.List;
import com.model.ListData;
import com.model.ListItem;
import com.model.Login;

import com.model.Token;


@ComponentScan(basePackages = {"com.demo.controller"}) 

@Controller

@RestController
@RequestMapping("/")
@EnableAutoConfiguration
@CrossOrigin(origins = "http://172.16.7.21:9527", maxAge = 3600)
public class DemoApplication {
	public Integer currentindex;
	public Integer currentindexend;
	List listest= new List();

ListData item = new ListData ();

//	  @RequestMapping("/test/{test}")
//	  @ResponseBody
//	    public String sayHello(@PathVariable("test") String test) {
//		   System.out.print("test");
//	        return   new HelloController().sayHello("hello");
//	    }

	  @RequestMapping(value = "/hello", method = RequestMethod.GET)
	  public ModelAndView hello(Map<String,Object> map){
	  map.put("key","value");
	  ModelAndView modelAndView = new ModelAndView();
	  modelAndView.setViewName("web");
	  modelAndView.addObject(map);
	  return modelAndView;
	  }
	  
	public static void main(String[] args) throws Exception  {
		SpringApplication.run(DemoApplication.class, args);
	
	}

	@RequestMapping("/add")
	public String addSession(HttpServletRequest httpServletRequest,
							@RequestParam("username")String username) {
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("username",username);
		session.setMaxInactiveInterval(10000);
		return "添加成功";
	}
	
	@RequestMapping("/show")
	// 跨網域
	public Object showSession(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		Object object = session.getAttribute("username");
		return object;
	}
//
//		@Configuration
//	   private CorsConfiguration buildConfig() {
//	        CorsConfiguration corsConfiguration = new CorsConfiguration();
//	        corsConfiguration.addAllowedOrigin("*"); // 1
//	        corsConfiguration.addAllowedHeader("*"); // 2
//	        corsConfiguration.addAllowedMethod("*"); // 3
//	        return corsConfiguration;
//	    }
//	 
//	    @Bean
//	    public CorsFilter corsFilter() {
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/**", buildConfig()); // 4
//	        return new CorsFilter();
//	    }
//	//    跨網域
	  @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/httpMethod/**")
	                        .allowedOrigins("*");//允许域名访问，如果*，代表所有域名
	                //.allowedOrigins("http://localhost:9527");//允许域名访问，如果*，代表所有域名
	                registry.addMapping("/httpMethod2**")
	                .allowedMethods("GET")
                    .allowedOrigins("*");//允许域名访问，如果*，代表所有域名
	            }
	        };
	    }


	Login memberAccount;
	@CrossOrigin
	@PostMapping(value = "/httpMethod", produces = "application/json")
	@ResponseBody
	public Login  httpMethod(@RequestBody Map<String, Object> params) throws JsonProcessingException{
	System.out.println("sent name is "+  params.get("name").toString());
	System.out.println("sent pwd is "+  params.get("pwd").toString());
	if( params.get("name").equals("admin")   &&  params.get("pwd").equals("111111" ) ) {
		Login memberAccount = new Login();
	    
		memberAccount.setCode(20000);
		Token test = new Token();
		memberAccount.setData(test);
		ObjectMapper objectMapper = new ObjectMapper();
	
		String userJsonStr = objectMapper.writeValueAsString(memberAccount);
		System.out.print(userJsonStr);
		return memberAccount;
	}
	return memberAccount;
	}

	@CrossOrigin
    @GetMapping("/httpMethod2")
	@ResponseBody

	public List httpMethod2(@RequestParam String id) throws JsonProcessingException{
	System.out.print(id);

	listest.setCode(20000);
	
	 Random ran = new Random();
	for ( int i = 0 ; i < 20; i ++)
	{
		ListItem items = new ListItem ();
		items.setid(item.getTotal());
		  
	    Integer tmp =ran.nextInt(2)+1;
	    if(tmp == 1 )
		items.setstatus(true);
	    else
	    	items.setstatus(false);
	    items.setname("name" + item.getTotal());
		items.settitle("title" + item.getTotal());
		items.setauthor("null");
		items.setdisplaytime("2016-05-02" );
		items.setpagviews("null");
		items.setcity("北京");
		items.show_data();
		/// add 陣列
		item.addList(items);
		//item.items.add(items);
	}
	listest.setData(item);
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	String userJsonStr = objectMapper.writeValueAsString(listest);
	//System.out.print(userJsonStr);
	return listest;
	}
	
//	@CrossOrigin
//	@RequestMapping(value = "/httpMethod")
//	@ResponseBody
//	public Shop httpMethod(){
////	System.out.println("sent name is "+  params.get("name").toString());
////	System.out.println("sent pwd is "+  params.get("pwd").toString());
////
////	  
//	  Shop memberAccount = new Shop();
//	  
////	if( params.get("name").equals("admin")   &&  params.get("pwd").equals("111111" ) ) {
////		
//		memberAccount.set_name( "test");
//		memberAccount.set_pwd ("test2");
//		
//		return memberAccount;
//		}
////	}
////	return memberAccount;
////	}
}
