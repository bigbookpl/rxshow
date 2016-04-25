#define pin_nadajnik 2  
#define pin_odbiornik 3 //Definicja pinu, do którego podłączamy odbiornik (pin ECHO)
int odleglosc;          //Zmienna przechowująca odległość
long czas_impulsu = 0;  //Zmienna przechowująca czas trwania impulsu na pinie ECHO
 
void setup()
{
 Serial.begin (9600);
 pinMode(pin_nadajnik, OUTPUT);
 pinMode(pin_odbiornik, INPUT);
}
 
void loop()
{
 digitalWrite(pin_nadajnik, HIGH); //Wystawienie stanu wysokiego na pin nadajnika
 delayMicroseconds(10); //Czas trwania 10us
 digitalWrite(pin_nadajnik, LOW); //Wystawienie stanu niskiego na pin nadajnika
 
 czas_impulsu = pulseIn(pin_odbiornik, HIGH); //Czas trwania impulsu na pinie Echo
 odleglosc = czas_impulsu/58; //Wyznaczenie odległości w cm

//Zabezpieczenie przed przekroczeniem zakresu pomiarowego
  if ( odleglosc < 5 || odleglosc> 200 )
   Serial.println("ERROR");
  else
  {
   Serial.println(odleglosc);
  }
 delay(500);
}
