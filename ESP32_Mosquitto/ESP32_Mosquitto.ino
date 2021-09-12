#include "DHT.h"
#include "MQ135.h"
#include <WiFi.h>
#include <PubSubClient.h>
//#include <WiFiClientSecure.h>
#include <ESPmDNS.h>
#include <ArduinoJson.h>
#include <NTPClient.h>
#include <WiFiUdp.h>

//Topics

#define mqttTemperature "mesure/temperature"
#define mqttHumidite "mesure/humidite"
#define mqttco2 "mesure/co2"
#define mqttnh4 "mesure/nh4"
#define mqttacetone "mesure/acetone"
#define mqtttoluene "mesure/toluene"
#define mqttethanol "mesure/ethanol"

//Card Id
String CardId = "Yos.k" ;


//MQTT 
#define mqtt_server "192.168.1.15"
#define mqtt_host  "DESKTOP-3"
  

//Wifi
 
const char* ssid     =      "";                 
const char* password =         "";                
WiFiClient espClient;
PubSubClient client(espClient);


//Sensors
const int mq135Pin = 32; 
MQ135 gasSensor = MQ135(mq135Pin);  // Initialise l'objet MQ135 sur le Pin spécifié    
#define DHTPIN 0     // Digital pin connected to the DHT sensor
#define DHTTYPE DHT22 
DHT dht(DHTPIN, DHTTYPE);


//Time 

// Define NTP Client to get time
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP);

// Variables to save date and time
String formattedDate;
String dayStamp;
String timeStamp;



//certificate

/*
const char* ca_cert =\
"-----BEGIN CERTIFICATE-----\n"\
"MIIEATCCAumgAwIBAgIUK5bSTo3ELxbk4swLQPZvNsPw3YkwDQYJKoZIhvcNAQEL\n"\
"BQAwgY8xCzAJBgNVBAYTAlROMQ0wCwYDVQQIDARTRkFYMQ0wCwYDVQQHDARTRkFY\n"\
"MQ4wDAYDVQQKDAVUQUxBTjEQMA4GA1UECwwHSU9UX1NTTDEVMBMGA1UEAwwMMTky\n"\
"LjE2OC4xLjE2MSkwJwYJKoZIhvcNAQkBFhp5b3Vzc2Vma2hlbWFraGVtQGdtYWls\n"\
"LmNvbTAeFw0yMTA3MjExOTMwNTFaFw0yNjA3MjExOTMwNTFaMIGPMQswCQYDVQQG\n"\
"EwJUTjENMAsGA1UECAwEU0ZBWDENMAsGA1UEBwwEU0ZBWDEOMAwGA1UECgwFVEFM\n"\
"QU4xEDAOBgNVBAsMB0lPVF9TU0wxFTATBgNVBAMMDDE5Mi4xNjguMS4xNjEpMCcG\n"\
"CSqGSIb3DQEJARYaeW91c3NlZmtoZW1ha2hlbUBnbWFpbC5jb20wggEiMA0GCSqG\n"\
"SIb3DQEBAQUAA4IBDwAwggEKAoIBAQDSLS3PWMlB7fmqbUGplWAMcwcudM4lyeeH\n"\
"SLZXs3bagxKKrlI6d8r0Sgoyei5J6Af1/99or09d4ckIjj9BR5L3EZrHT9WEp8Y4\n"\
"b3IjZyAvTUs37AYl7TBUb1m3tHvlsbPtN4vJq9IhRNJsMxUXlMXsyRO7fv3KTKSL\n"\
"Tg0KDt3W1b1GkdJxpOF+Of87wOhBh0Tse3qnoOq5VQmO7GocZZLubn1p99UuI056\n"\
"JA6Y3hTFWgkqydzVVTbRfweVhpxMtHSWaA9i2a3n98CYqA1RJWR1/94WIG7V5ZjP\n"\
"AhY7IEl/xD+lHXs8ax4K9kKchZHHRtfFWq2B7N0kW5lMxQXhboAVAgMBAAGjUzBR\n"\
"MB0GA1UdDgQWBBSgbFfm2c0Ljz69zl5Qiu5RKKAixDAfBgNVHSMEGDAWgBSgbFfm\n"\
"2c0Ljz69zl5Qiu5RKKAixDAPBgNVHRMBAf8EBTADAQH/MA0GCSqGSIb3DQEBCwUA\n"\
"A4IBAQB6emm7bkEk1PfWGrq/QaPjHVEtDnpxaYyG3nOXhkZadi+MMa6icsOWgFxC\n"\
"EyAYCYP6SmL3z4T3gcshbuRA+zN439n7nXX8CyGeK1HTnsrtYRC+1Q+WhMJNjyy5\n"\
"kIOLsTYe2MuIKGWeKuECfQOHMr+n35afkL4fn34xX8CFz3SiBwrly37l4NZ2E4co\n"\
"5MynZ/CU7REr5akiBUoMrpvnBzagoNJ0LAxHOA5qiKJziyyT8WLsjWiWLk0+4axX\n"\
"mx9RiB3wa/U31Spc/7M/VasIg5U2CbbYzlMUnB5QGk7dYc+Vf7GipIaxlx3sP+ge\n"\
"+nxBgn/K4vdPK/cqvEYbngrYp7Rn\n"\
"-----END CERTIFICATE-----" ;
*/


//MQTT connect method

void mqttconnect() {
  /* Loop until reconnected */
  while (!client.connected()) {
    Serial.print("MQTT connecting ...");
    /* client ID */
    String clientId = "ESP32Client";
    /* connect now */
    if (client.connect(clientId.c_str())) {
    
    } else {
      Serial.print("failed, status code =");
      Serial.print(client.state());
      Serial.println("try again in 5 seconds");
      /* Wait 5 seconds before retrying */
      delay(5000);
    }
  }
}





void setup() {
  Serial.begin(9600);
    
//   begin Wifi connect

  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();
  delay(2000);
  WiFi.begin(ssid, password);
  
 while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
 
  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  //end Wifi connect

  //Mqtt port
 //client.setServer(mqtt_server, 1884);

//Start temperature sensor    
  dht.begin();

//Time

// Initialize a NTPClient to get time
  timeClient.begin();
  // Set offset time in seconds to adjust for your timezone, for example:
  // GMT +1 = 3600
  // GMT +8 = 28800
  // GMT -1 = -3600
  // GMT 0 = 0
  timeClient.setTimeOffset(3600);
  

  //Security connect ssl

   /*setup MDNS for ESP32 */
/*  if (!MDNS.begin("esp32")) {
      Serial.println("Error setting up MDNS responder!");
      while(1) {
          delay(1000);
      }
  }
  /* get the IP address of server by MDNS name */
 /* Serial.println("mDNS responder started");
  IPAddress serverIp = MDNS.queryHost(mqtt_host);
  Serial.print("IP address of server: ");
  Serial.println(serverIp.toString());
  /* set SSL/TLS certificate */
 /* espClient.setCACert(ca_cert);
  /* configure the MQTT server with IPaddress and port */
 /* client.setServer(serverIp, 8883);
  */
  
  
    
  
}



void loop() {

  //Connect to MQTT
/*
if (!client.connected()){
   mqttconnect();
  }
*/
 //Gaz Values
    float ppm = gasSensor.getPPM();
    float Ethanol = gasSensor.getEthanol();
    float NH4 = gasSensor.getNH4();
    float Toluene = gasSensor.getToluene();
    float Acetone = gasSensor.getAcetone();
    
    Serial.print(" gazes:co2 ethanol nh4 toluene acetone ");
    Serial.println(ppm);
    Serial.println(Ethanol);
    Serial.println(NH4);
    Serial.println(Toluene);
    Serial.println(Acetone);
       

  //client.loop();

 //Temperature and Humidity
 
  float h = dht.readHumidity();
  
  float t = dht.readTemperature();
  

  // Check if any reads failed and exit early (to try again).
  if (isnan(h) || isnan(t) ) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }
   Serial.print(F("Humidity: "));
  Serial.print(h);
  Serial.print(F("%  Temperature: "));
  Serial.print(t);
  Serial.print(F("°C "));
 


//Time

  while(!timeClient.update()) {
    timeClient.forceUpdate();
  }
  // The formattedDate comes with the following format:
  // 2018-05-28T16:00:13Z

  formattedDate = timeClient.getFormattedDate();
  Serial.println(formattedDate);
  
  
 
 
//Define data to send json

  //Temperature
  
 StaticJsonDocument<500> doc;
  JsonObject root = doc.to<JsonObject>();

  root["value"] = t;
  root["type"]="temperature";
  root["dates"]=formattedDate;  
  root["card"]=CardId ;
  
char Message[200];
  
    serializeJsonPretty(doc, Message);
    Serial.print(Message);
    Serial.println();

  
  //Humidity
     StaticJsonDocument<500> doc1;
  JsonObject root1 = doc1.to<JsonObject>();

  
  root1["value"] = h;
  root1["type"]="humidite";
  root1["dates"]=formattedDate;
  root1["card"]=CardId ;
    

  char Message1[200];
  
    serializeJsonPretty(doc1, Message1);
    Serial.print(Message1);
    Serial.println();

  //Co2  

     StaticJsonDocument<500> doc2;
  JsonObject root2 = doc2.to<JsonObject>();
  
  root2["value"] = ppm;
  root2["type"]="co2";
  root2["dates"]=formattedDate; 
  root2["card"]=CardId ;
   

  char Message2[200];
  
    serializeJsonPretty(doc2, Message2);
    Serial.print(Message2);
    Serial.println();

    //Ethanol 

     StaticJsonDocument<500> doc3;
  JsonObject root3 = doc3.to<JsonObject>();
  
  root3["value"] = Ethanol;
  root3["type"]="ethanol";
  root3["dates"]=formattedDate; 
  root3["card"]=CardId ;
   

  char Message3[200];
  
    serializeJsonPretty(doc3, Message3);
    Serial.print(Message3);
    Serial.println();

//NH4

     StaticJsonDocument<500> doc4;
  JsonObject root4 = doc4.to<JsonObject>();
  
  root4["value"] = NH4;
  root4["type"]="nh4";
  root4["dates"]=formattedDate; 
  root4["card"]=CardId ;
   

  char Message4[200];
  
    serializeJsonPretty(doc4, Message4);
    Serial.print(Message4);
    Serial.println();


 //Toluene 

     StaticJsonDocument<500> doc5;
  JsonObject root5 = doc5.to<JsonObject>();
  
  root5["value"] = Toluene;
  root5["type"]="toluene";
  root5["dates"]=formattedDate; 
  root5["card"]=CardId ;
   

  char Message5[200];
  
    serializeJsonPretty(doc5, Message5);
    Serial.print(Message5);
    Serial.println();

 //Acetone 

     StaticJsonDocument<500> doc6;
  JsonObject root6 = doc6.to<JsonObject>();
  
  root6["value"] = Acetone;
  root6["type"]="acetone";
  root6["dates"]=formattedDate; 
  root6["card"]=CardId ;
   

  char Message6[200];
  
    serializeJsonPretty(doc6, Message6);
    Serial.print(Message6);
    Serial.println();
    
//Publish data 5s in between  each minute

  client.publish(mqttTemperature, Message);
  delay(5000) ;
  
  client.publish(mqttHumidite, Message1);
  delay(5000) ;
  
  client.publish(mqttco2, Message2);
  delay(5000) ;
  
  client.publish(mqttethanol, Message3);
  delay(5000) ;
  
  client.publish(mqttnh4, Message4);
  delay(5000) ;
  
  client.publish(mqtttoluene, Message5);
  delay(5000) ;
  
  client.publish(mqttacetone, Message6);
  delay(30000) ;
    
    

}
