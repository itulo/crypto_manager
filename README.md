# Crypto Manager
Crypto manager connects to crypto exchanges and fetches your coin balances. You can see a summary in your browser.

Coin balances are fetched every hour.

The backend use the Spring framework, the  frontend React.

## Getting started
### Database
You need a MySQL database. Create database and user with these instructions:

    CREATE DATABASE crypto_manager;
    CREATE USER 'crypto' IDENTIFIED BY 'crypto';
    GRANT ALL PRIVILEGES ON crypto_manager.* TO 'crypto'@'%';

### Add your exchanges' api keys
In application.properties `exchanges.*`

## Run
### Backend
    ./gradlew bootRun

### Frontend
    cd frontend
    npm start