INSERT INTO categories (name) VALUES('GRAPHICS_CARD');

INSERT INTO products (name, category_id, price, stock, stock_Min, stock_max, description)
VALUES('GTX 960', 1, 100.00, 1, 1, 1, 'low-mid');

INSERT INTO users (email, password, role) VALUES('a@g.com', 'qwerty', 'CUSTOMER');
INSERT INTO users (email, password, role) VALUES('b@g.com', 'qwerty', 'MANAGER');

INSERT INTO customers (user_id, name, surname) VALUES(1, 'Rodrigo', 'Fernandes');

INSERT INTO managers (user_id, name, surname) VALUES(2, 'Muhammad', 'Avdol');