API Dokumentacija
1. Registracija korisnika
Endpoint: /api/users/register

Metoda: POST

URL: http://localhost:8080/api/users/register

Opis: Registracija novog korisnika sa inicijalnim stanjem od $1000.

Telo Zahteva (JSON):

json
Copy code
{
    "username": "newuser",
    "password": "newpassword"
}
Primer Odgovora (JSON):

json
Copy code
{
    "message": "Uspesna registracija"
}
2. Prijava korisnika
Endpoint: /api/users/login

Metoda: POST

URL: http://localhost:8080/api/users/login

Opis: Prijava korisnika i generisanje JWT tokena za autentifikaciju.

Telo Zahteva (JSON):

json
Copy code
{
    "username": "newuser",
    "password": "newpassword"
}
Primer Odgovora (JSON):

json
Copy code
{
    "jwt": "YOUR_JWT_TOKEN"
}
3. Kupovina akcija
Endpoint: /api/stocks/buy

Metoda: POST

URL: http://localhost:8080/api/stocks/buy

Opis: Kupovina akcija od strane korisnika. Ovaj endpoint zahteva autentifikaciju.

Zaglavlja:

Content-Type: application/json
Authorization: Bearer YOUR_JWT_TOKEN
Telo Zahteva (JSON):

json
Copy code
{
    "user": {
        "username": "newuser"
    },
    "stock": {
        "symbol": "AAPL"
    },
    "quantity": 10,
    "price": 150.00
}
Primer Odgovora (JSON):

json
Copy code
{
    "id": 1,
    "user": {
        "id": 1,
        "username": "newuser"
    },
    "stock": {
        "id": 1,
        "symbol": "AAPL"
    },
    "quantity": 10,
    "price": 150.00,
    "date": "2024-06-14T00:00:00.000+00:00",
    "type": "buy"
}