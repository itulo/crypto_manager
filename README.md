# Crypto Manager
Crypto manager connects to crypto exchanges, fetches your coin balances and stores them into a local database. You can see a summary in your browser.

Coin balances are fetched every hour.

The backend uses the Spring framework, the frontend is in React.

## Getting started
### RabbitMQ
[Install RabbitMQ](https://www.rabbitmq.com/download.html).

### Database
You need a MySQL database. Create database and user with these instructions:

    CREATE DATABASE crypto_manager;
    CREATE USER 'crypto' IDENTIFIED BY 'crypto';
    GRANT ALL PRIVILEGES ON crypto_manager.* TO 'crypto'@'%';

Tables will be automatically created when running the app.

### Add your exchanges' and api keys
In application.properties `exchanges.*`

Currently exchanges supported are: binance, bittrex, coinbasepro, hitbtc, kraken, kucoin. But others can be added.

## Run
### Backend
    ./gradlew bootRun
NOTE: if you run spring through your IDE then add these JVM arguments:

    --add-opens java.base/java.net=ALL-UNNAMED

### Frontend
    cd frontend
    npm start
