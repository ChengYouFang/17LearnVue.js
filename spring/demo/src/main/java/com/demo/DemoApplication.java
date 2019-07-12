package com.demo;

import org.springframework.boot.SpringApplication;
import com.demo.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;



import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Iterator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.model.List;
import com.model.ListData;
import com.model.ListItem;
import com.model.Login;

import com.model.Token;


@ComponentScan(basePackages = {"com.demo.controller"}) 

@Controller
@EnableRedisHttpSession//开启spring session支持
@RestController
@RequestMapping("/")
@EnableAutoConfiguration
@CrossOrigin(origins = "https://localhost:443", maxAge = 3600)
public class DemoApplication {
	public JedisConnectionFactory jedisConFactory;
	public Integer currentindex;
	public Integer currentindexend;
	List listest= new List();
	List listest2= new List();

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
	                .allowCredentials(true)
	                 .allowedOrigins("https://localhost");//允许域名访问，如果*，代表所有域名
	                //.allowedOrigins("http://localhost:9527");//允许域名访问，如果*，代表所有域名
	                registry.addMapping("/httpMethod2**")
	                .allowedMethods("GET")
	                .allowCredentials(true)
                    .allowedOrigins("https://localhost");//允许域名访问，如果*，代表所有域名
	      
	                registry.addMapping("/setSession")
	                .allowCredentials(true)
                    .allowedOrigins("https://localhost");//允许域名访问，如果*，代表所有域名
		            //.allowedOrigins("http://localhost:9527");//允许域名访问，如果*，代表所有域名
		            registry.addMapping("/getSession")
		            .allowCredentials(true)
		            .allowedMethods("GET")
		          .allowedOrigins("https://localhost");//允许域名访问，如果*，代表所有域名
		            registry.addMapping("/removeSession")
		            .allowCredentials(true)
		            .allowedMethods("GET")
		          .allowedOrigins("https://localhost");//允许域名访问，如果*，代表所有域名
			            }
	        };
	    }


	Login memberAccount;
	@CrossOrigin
	@PostMapping(value = "/httpMethod", produces = "application/json")
	@ResponseBody
	public Login  httpMethod(@RequestBody Map<String, Object> params,HttpServletRequest request) throws JsonProcessingException{
	System.out.println("sent name is "+  params.get("name").toString());
	System.out.println("sent pwd is "+  params.get("pwd").toString());

	if( params.get("name").equals("admin")   &&  params.get("pwd").equals("111111" ) ) {
		Login memberAccount = new Login();
//	    if(request.getSession().getAttribute("name") == null) {
		memberAccount.setCode(20000);
		request.getSession().setAttribute("name",  params.get("name").toString());
//		}
//	    else
//	    memberAccount.setCode(500);
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
    @RequestMapping(value = "/setSession", method = RequestMethod.GET)
	@CrossOrigin
    public void setSession(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println(request.getSession().getId().toString());
    	request.getSession().setAttribute("name", "test");
   //     request.getSession().setAttribute("name2", "tom2");
     
    }
 
    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
	@CrossOrigin
    public List getInterestPro(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException{
    	System.out.println(request.getSession().getId().toString());
    	System.out.println(request.getSession().getAttribute("name"));
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	if( request.getSession().getAttribute("name") != null)
    		listest2.setCode(200);
    	else
    		listest2.setCode(404);
    	
    	 long time = System.currentTimeMillis();
    	 System.out.println(time);
    	if(jedisConFactory == null)
    	{
        	//select 所有 redis 
        	Set<byte[]> keys = jedisConFactory.getConnection().keys("*sessions:expires*".getBytes());

        	Iterator<byte[]> it = keys.iterator();

        	while(it.hasNext()){

        	    byte[] data = (byte[])it.next();

        	    System.out.println(new String(data, 0, data.length));
        	}
    	}

//    	
    	
    	String userJsonStr = objectMapper.writeValueAsString(listest2);
    	//System.out.print(userJsonStr);
    	return listest2;
    }
 
    @RequestMapping(value = "/removeSession", method = RequestMethod.GET)
	@CrossOrigin
    public void removeSession(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("name");

    }
    
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
    	jedisConFactory = new JedisConnectionFactory();
      jedisConFactory.setHostName("localhost");
      jedisConFactory.setPort(6379);
      return jedisConFactory;
    }
     
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
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
