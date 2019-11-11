#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
MDNSResponder mdns;


// wifi username and password
const char* ssid   =  "";
const char* password =  "";

// pins for transistor and temperature sensor
int transistor_pin = 16;
int tempPin = 0;

// variables to calculate temperature
float tempC;
int reading;



WiFiClient espClient;
ESP8266WebServer server(80);
String webPage = "";

IPAddress ip();   //fill in the desired IP
IPAddress gateway();   //fill in the gateway
IPAddress subnet();    // fill in the subnet


void reconnect() {
  delay(100);
  WiFi.begin(ssid, password);

  delay(100);
  WiFi.config(ip, gateway, subnet);

  Serial.println("");

  // Wait for connection
  int i = 0;
  while (WiFi.status() != WL_CONNECTED && i < 19) {
    i++;
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}



void setup(void){
  webPage += "<h1>SONOFF Web Server</h1><p><a href=\"update\"><button>UPDATE</button></a>&nbsp;<a href=\"on\"><button>ON</button></a>&nbsp;<a href=\"off\"><button>OFF</button></a></p>";  
  // preparing GPIOs
  
  pinMode(transistor_pin, OUTPUT);

  Serial.begin(115200); 
  reconnect();
  


  
  if (mdns.begin("esp8266", WiFi.localIP())) {
    Serial.println("MDNS responder started");
  }
  
  server.on("/", [](){
    server.send(200, "text/html", webPage);
  });

  // Switch PC on
  server.on("/on", [](){
    digitalWrite(transistor_pin, LOW);
    delay(500);
    digitalWrite(transistor_pin, HIGH);
    delay(500);
    digitalWrite(transistor_pin, LOW);
    webPage = "1";
    server.send(200, "text/html", webPage);
    delay(1000);
  });

  // Switch PC off - Force shut down
  server.on("/off", [](){
    digitalWrite(transistor_pin, HIGH);
    webPage = "4";
    server.send(200, "text/html", webPage);
    delay(11000); 
    webPage = "5";
    server.send(200, "text/html", webPage);
    digitalWrite(transistor_pin, LOW);
  });

  // update the temperature
  server.on("/update", [](){
    Serial.println("You pushed the update ... ");
    Serial.println( digitalRead(transistor_pin) );
    reading = analogRead(tempPin);
    Serial.println(reading);
    tempC =281  * reading / 1024;
    Serial.println(tempC);
    webPage = tempC;
    server.send(200, "text/html", webPage);
    delay(1000); 
  });
  
  server.begin();
  Serial.println("HTTP server started");
}



void loop(void){
  if ( WiFi.status() ==  1) {
    reconnect();
    Serial.println("restart now");
    if ( WiFi.status() ==  1 ) {
      ESP.restart();
    }
  }

  server.handleClient();
} 
