package application.mqttr.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.lucene.search.SortField;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DistanceFeatureQueryBuilder;
import org.elasticsearch.index.query.DistanceFeatureQueryBuilder.Origin;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import application.mqttr.model.Mesure;
import java.util.Date;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
@Service
public class Data_service {


	
	
	RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));
	
	
	
	public  Map[] getdata(String index,String type,int size,String carte)
	{
	SearchRequest searchRequest = new SearchRequest();//intialize search req
    searchRequest.indices(index);//index of search
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("type",type)).must(QueryBuilders.matchQuery("card",carte)));
	
	
	
	
    searchSourceBuilder.size(size) ;
    searchSourceBuilder.sort("dates", SortOrder.DESC);
    searchRequest.source(searchSourceBuilder);
    Map<String, Object> map=null;
   
   
    try {
        SearchResponse searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);//search request
        if (searchResponse.getHits().getTotalHits().value > 0) {
            SearchHit[] searchHit = searchResponse.getHits().getHits();
            Map [] map_array = new Map[searchHit.length] ;
       
         int i =0 ;
          for (SearchHit hit : searchHit) {
        	
          map = hit.getSourceAsMap();
          map_array[i]=map ; 
          i++ ;
          
            
          }
          return map_array ;
        }
    } 
        catch (IOException e) {
        e.printStackTrace();
    }
    
    
    
	return null;
	




	
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public static Date minuit()
	{
		Date min =new Date() ;
		
		min.setHours(0);
		min.setMinutes(0);
		min.setSeconds(0);
		return(min) ;
	
	}
	
	public static Date addSubtractDate(int n)
	{
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE,n);
	return cal.getTime() ;
	}
	
	
	public  Map[] getdata24h(String index,String type,String carte)
	{
	Date currentDate = new Date();
	SearchRequest searchRequest = new SearchRequest();//Initialize search req
	searchRequest.indices(index);//index of search
	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("type",type)).must(QueryBuilders.matchQuery("card",carte)).must(QueryBuilders.rangeQuery("dates").gt(minuit().getTime()).lt(currentDate.getTime())));
	System.out.println(minuit()) ;
	System.out.println(currentDate) ;
	
	
	searchSourceBuilder.sort("dates", SortOrder.DESC);
	
	searchSourceBuilder.size(10000) ;
	
	searchRequest.source(searchSourceBuilder);
	Map<String, Object> map=null;
	    
	    try {
	        SearchResponse searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);//search request
	        if (searchResponse.getHits().getTotalHits().value > 0) {
	            SearchHit[] searchHit = searchResponse.getHits().getHits();
	            Map [] map_array = new Map[searchHit.length] ;
	       
	         int i =0 ;
	          for (SearchHit hit : searchHit) {
	        	
	          map = hit.getSourceAsMap();
	          map_array[i]=map ; 
	          i++ ;
	          
	            
	          }
	          return map_array ;
	        }
	    } 
	        catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null;
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	public  Map[] getdatatend(String index,String type,String carte,Date dateDeb,Date dateFin)
    {
		Date currentDate = new Date();
		SearchRequest searchRequest = new SearchRequest();//intialize search req
		searchRequest.indices(index);//index of search
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("type",type)).must(QueryBuilders.matchQuery("card",carte)).must(QueryBuilders.rangeQuery("dates").gt(dateDeb.getTime()).lt(dateFin.getTime())));
		System.out.println(dateDeb) ;
		System.out.println(dateFin) ;
		
		
		searchSourceBuilder.sort("dates", SortOrder.DESC);
		
		searchSourceBuilder.size(10000) ;
		
		searchRequest.source(searchSourceBuilder);
		Map<String, Object> map=null;
		    
		    try {
		        SearchResponse searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);//search request
		        if (searchResponse.getHits().getTotalHits().value > 0) {
		            SearchHit[] searchHit = searchResponse.getHits().getHits();
		            Map [] map_array = new Map[searchHit.length] ;
		       
		         int i =0 ;
		          for (SearchHit hit : searchHit) {
		        	
		          map = hit.getSourceAsMap();
		          map_array[i]=map ; 
		          i++ ;
		          
		            
		          }
		          return map_array ;
		        }
		    } 
		        catch (IOException e) {
		        e.printStackTrace();
		    }
		    return null;
    }

}