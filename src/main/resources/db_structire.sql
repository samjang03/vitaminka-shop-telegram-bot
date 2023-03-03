create database supplements_store
character set utf8mb4
collate utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS supplement (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    manufacturer VARCHAR(50),
    country_of_origin VARCHAR(50),
    price DECIMAL(6, 2),
    photoUrl VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    phone_number VARCHAR(13),
    city VARCHAR(30),
    address VARCHAR(255),
    price DECIMAL(6 , 2 )
);
