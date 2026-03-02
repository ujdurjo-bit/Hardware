CREATE TABLE hardware
(
    id IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    snum VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    quantity  INT NOT NULL

);

