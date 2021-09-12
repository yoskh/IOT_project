package application.mqttr.Controller;


import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import application.mqttr.model.Mesure;
import application.mqttr.model.Tendency_parse;
import application.mqttr.service.Data_service;
@ResponseBody
@RestController
@RequestMapping("/service") 
public class Tendance_intervalle{
	static String index = "test_final";
	
	@Autowired
	   public static Data_service get_data=new Data_service()   ;
	
	//json format {carte:"Yos.k",type:"Temperature",dateDeb:"01-07-2021-10-37-50",dateFin:"13-08-2021-10-37-50"}	
	
	
	@PostMapping("/Tendence")
	public static  Map[] tendenceinterv(@RequestBody  String jvar) throws JsonSyntaxException
	{
		Tendency_parse tend  ;
		Gson json_var = new Gson() ;
		tend = json_var.fromJson(jvar, Tendency_parse.class) ;
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm"); //2021-01-21 10:35:35.56446 
		Date from1;
		Date to1 ;
		try {
			from1 = formatter.parse(tend.dateDeb);
			 to1=formatter.parse(tend.dateFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return(get_data.getdata24h(index,tend.type,tend.carte)) ;
		}
		
	return(get_data.getdatatend(index,tend.type,tend.carte,from1,to1)) ;
		
	}
}