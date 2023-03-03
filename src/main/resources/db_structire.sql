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
INSERT INTO supplement(id, name, manufacturer, country_of_origin, price, photo_url)
VALUES 
(1, "Вітамін С", "Now Foods", "США", 414.00, "https://vitaminka.com.ua/uploads/product/400/409/thumbs/70_00690.jpg"),
(2, "Омега-3 риб'ячий жир", "Now Foods", "США", 150.00, "https://vitaminka.com.ua/uploads/product/400/472/01649.jpg"),
(3, "Кальцію цитрат", "Douglas Laboratories", "Канада", 1201.00, "https://vitaminka.com.ua/uploads/product/3200/3217/DOU-97891.jpg"),
(4, "ВІтамін А", "Bluebonnet Nutrition", "США", 323.00 , "https://vitaminka.com.ua/uploads/product/1600/1625/BB-0298.jpg"),
(5, "Вітамін В12", "Puritan's pride", "США", 333.00, "https://vitaminka.com.ua/uploads/product/200/245/001380.jpg"),
(6, "Магній з вітаміном В6", "Solgar", "США", 395.00, "https://vitaminka.com.ua/uploads/product/1000/1030/1720.jpg"),
(7, "Селен", "Now Foods", "США", 209.00, "https://vitaminka.com.ua/uploads/product/400/458/thumbs/70_01482.jpg");
