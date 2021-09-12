package application.mqttr.Controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.mqttr.service.Data_service;

@RestController
public class Last_value_API{
	
	@Autowired
	   public static Data_service get_value=new Data_service()   ;
	
	static String index = "test_final";

	@RequestMapping("/service/final/{type}/{carte}")        //url to map to 
	 
public static  Map[] lastvalue(@PathVariable String type,@PathVariable String carte) {
		
	return(get_value.getdata(index,type, 1,carte)) ;
		
	}
}