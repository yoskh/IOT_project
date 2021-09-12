package application.mqttr;




import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.springframework.beans.factory.annotation.Autowired;
import application.mqttr.service.Index_service;


public class Callbackx implements MqttCallback
{ 
	@Autowired
	   public static Index_service writex=new Index_service()   ;
	

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		  writex.writemsg(message);
	
		
		
	
	}


	
  
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

}
