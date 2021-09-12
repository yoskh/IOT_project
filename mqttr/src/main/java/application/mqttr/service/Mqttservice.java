package application.mqttr.service;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import application.mqttr.Callbackx;

@Service
public class Mqttservice {

	 public static MqttAsyncClient Clientx; //create client*/
	 
	 public void client(String URL,String ID, String Topic) throws MqttException
	 {
		 Clientx = new MqttAsyncClient(URL,ID) ;//inst client
	 Callbackx Callbackq =new Callbackx() ;
     Clientx.setCallback(Callbackq);
     IMqttToken token =Clientx.connect() ;
     token.waitForCompletion();
     Clientx.subscribe(Topic, 0) ;
	 }
}
