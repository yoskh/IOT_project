package application.mqttr.service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class All_cards_service {

	@Autowired
	   public static Data_service get_value=new Data_service()   ;
	
	static String index = "test_final";
	
	static <T> T[] concatWithCollection(T[] array1, T[] array2,T[] array3, T[] array4,T[] array5) {
	    List<T> resultList = new ArrayList<>(array1.length + array2.length+ array3.length+ array4.length+ array5.length);
	    Collections.addAll(resultList, array1);
	    Collections.addAll(resultList, array2);
	    Collections.addAll(resultList, array3);
	    Collections.addAll(resultList, array4);
	    Collections.addAll(resultList, array5);
	    
	    
	    @SuppressWarnings("unchecked")
	    //the type cast is safe as the array1 has the type T[]
	    T[] resultArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), 0);
	    return resultList.toArray(resultArray);
	}
	
	
	
	public  Map[] getdataall(String index,String type,int size)
	{
		Map[] carte1 = new Map[size] ;
		 carte1 = get_value.getdata(index,type, size,"Yos.k") ;
		 
		 Map[] carte2 = new Map[size] ;
		 carte2 = get_value.getdata(index,type, size,"sirius") ;
		 
		Map[] carte3 = new Map[size] ;
		 carte3 = get_value.getdata(index,type, size,"hach") ;
		 
		Map[] carte4 = new Map[size] ;
		 carte4 = get_value.getdata(index,type, size,"hamdoun") ;
		 
		 Map[] carte5 = new Map[size] ;
		 carte5 = get_value.getdata(index,type, size,"khaoula") ;
		
		Map[] arrayf=concatWithCollection(carte1,carte2,carte3,carte4,carte5) ;
   
   return(arrayf) ;
	
	
}
}
