Se desarrollo a base de Java 17.
para ganar tiempo se instalo en postgres 14.8
-Al instalar postgres crear un user= postgres , 
 password = postgres , crear la base de datos llamada
 "paymentsdb"
Ejecutar la clase Java

-MaerskTechnicalTestApplication

dejo Curl 
///guardar tokem
curl --location 'localhost:8080/v1/tokens' \
--header 'Authorization: Bearer pk_test_123456789qwertyuiop' \
--header 'Content-Type: application/json' \
--data-raw '{
"card_number": 1358954993914435,
"cvv": 123,
"expire_moth":"09",
"expire_year":"2025",
"email":"alexis.pumayalla@culqui.com"
}'


//consultar tarjeta

curl --location 'localhost:8080/v1/cards/card?idCard=de0b6b3a763fcdb' \
--header 'Authorization: Bearer pk_test_123456789qwertyuiop' \
--data ''



Nota : por tiempo nose realizo la conexion a dos base de datos.