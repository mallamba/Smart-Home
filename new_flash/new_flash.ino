#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
MDNSResponder mdns;

const char* ssid   =  "";
const char* password =  "";


boolean welcome = false;

WiFiClient espClient;

ESP8266WebServer server(80);
String webPage = "";
int gpio13Led = 13;
int gpio12Relay = 12;
//choose a static ip
  IPAddress ip(x,x,x,x);   
  IPAddress gateway(x,x,x,x);  
  IPAddress subnet(x,x,x,x); 

void reconnect() {
  delay(500);
  WiFi.begin(ssid, password);
  digitalWrite(gpio13Led, HIGH);
  delay(500);
  WiFi.config(ip, gateway, subnet);

  
  Serial.println("");

  // Wait for connection
  int i = 0;
  while (WiFi.status() != WL_CONNECTED && i < 19) {
      digitalWrite(gpio13Led, HIGH);
    i++;
    delay(500);
    Serial.print(".");
      digitalWrite(gpio13Led, LOW);
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
  pinMode(gpio13Led, OUTPUT);
  digitalWrite(gpio13Led, HIGH);
  
  pinMode(gpio12Relay, OUTPUT);
  digitalWrite(gpio12Relay, HIGH);
 
  Serial.begin(115200); 

  reconnect();
  


  
  if (mdns.begin("esp8266", WiFi.localIP())) {
    Serial.println("MDNS responder started");
  }
  
  server.on("/", [](){
    server.send(200, "text/html", webPage);
  });
  server.on("/on", [](){
    digitalWrite(gpio13Led, HIGH);
    digitalWrite(gpio12Relay, HIGH);
    webPage = digitalRead(gpio12Relay);
    server.send(200, "text/html", webPage);
    delay(1000);
  });
  server.on("/off", [](){
    digitalWrite(gpio13Led, LOW);
    digitalWrite(gpio12Relay, LOW);
    webPage = digitalRead(gpio12Relay);
    server.send(200, "text/html", webPage);
    delay(1000); 
  });
  server.on("/update", [](){
    webPage = digitalRead(gpio12Relay);
    server.send(200, "text/html", webPage);
    Serial.println("You pushed the update ... ");
    Serial.println( digitalRead(gpio12Relay) );
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
