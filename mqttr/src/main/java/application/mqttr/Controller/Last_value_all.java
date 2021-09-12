package application.mqttr.Controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.mqttr.service.All_cards_service;
import application.mqttr.service.Data_service;

@RestController
public class Last_value_all{
	static String index = "test_final";
	
	@Autowired
	   public static All_cards_service values=new All_cards_service()   ;
	
	

	@RequestMapping("/service/final/{type}")        //url to map to 
	 
public static  Map[] lastdata(@PathVariable String type) {
		
	return(values.getdataall(index,type, 1)) ;
		
	}
}