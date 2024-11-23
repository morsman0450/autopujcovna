        CREATE TABLE IF NOT EXISTS car (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            brand VARCHAR(255),
            model VARCHAR(255),
            car_year INT,
            color VARCHAR(255),
            km_status INT
            );

        INSERT INTO car (brand, model, car_year, color, km_status) VALUES ('Škoda', 'Octavia', 2019, 'White', 45000);
        INSERT INTO car (brand, model, car_year, color, km_status) VALUES ('Ford', 'Focus', 2020, 'Blue', 15000);
        INSERT INTO car (brand, model, car_year, color, km_status) VALUES ('Volkswagen', 'Golf', 2021, 'Black', 5000);

        CREATE TABLE IF NOT EXISTS customer (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255),
            email VARCHAR(255),
            phone VARCHAR(255)
            );

        INSERT INTO customer (name, email, phone) VALUES ('John Doe', 'john.doe@example.com', '123456789');
        INSERT INTO customer (name, email, phone) VALUES ('Jane Smith', 'jane.smith@example.com', '987654321');

        CREATE TABLE rent (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              car_id BIGINT NOT NULL,
                              customer_id BIGINT NOT NULL,
                              rent_date DATE NOT NULL,
                              return_date DATE,
                              total_price DECIMAL(10, 2),
                              FOREIGN KEY (car_id) REFERENCES car(id),
                              FOREIGN KEY (customer_id) REFERENCES customer(id)
        );


