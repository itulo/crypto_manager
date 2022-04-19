### MySQL
    CREATE DATABASE crypto_manager;
    CREATE USER 'crypto' IDENTIFIED BY 'crypto';
    GRANT ALL PRIVILEGES ON crypto_manager.* TO 'crypto'@'%';