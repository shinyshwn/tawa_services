CREATE TABLE product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(200),
                         price DECIMAL(10, 2) NOT NULL,
                         quantity INT NOT NULL
);
