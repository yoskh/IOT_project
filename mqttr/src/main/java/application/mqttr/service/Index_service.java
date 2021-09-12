package application.mqttr.service;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import application.mqttr.model.Mesure;

import net.minidev.json.parser.ParseException;

@Service
public class Index_service{

	@Autowired
	Mesure mesure ;
	
	String index = "test_final";

	RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

	public void writemsg( MqttMessage message) throws IOException, ParseException
		{  
    	System.out.println(message) ;
		Gson g = new Gson();

		mesure = g.fromJson(message.toString(), Mesure.class);
		
  
			IndexRequest indexRequest = new IndexRequest(index);
			indexRequest.source(new ObjectMapper().writeValueAsString(mesure), XContentType.JSON);
			
			IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
			System.out.println("oi") ;
		System.out.println("response id: "+indexResponse.getId());
		
		
		
		}
}
