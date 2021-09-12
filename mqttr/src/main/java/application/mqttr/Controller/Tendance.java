package application.mqttr.Controller;


import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.mqttr.service.Data_service;

@RestController
public class Tendance{
	
	static String index = "test_final";
	
	@Autowired
	   public static Data_service get_data=new Data_service()   ;
	@RequestMapping("/service/tendance/{types}/{carte}")        //url to map to 
	
public static  Map[] thisday(@PathVariable String types,@PathVariable String carte) {
		
	return(get_data.getdata24h(index,types,carte)) ;
		
	}
}