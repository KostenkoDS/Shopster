DROP TABLE IF EXISTS order_details, product_pictures, orders, products, categories, managers, customers, users;

CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email varchar(30) NOT NULL,
  password varchar(45) NOT NULL,
  role enum('CUSTOMER','MANAGER') NOT NULL COMMENT 'User role'
);

CREATE TABLE managers (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id int DEFAULT NULL,
  name varchar(30) NOT NULL,
  surname varchar(30) NOT NULL,
  middle_name varchar(30) DEFAULT NULL,
  address varchar(45) DEFAULT NULL,
  phone_number varchar(18) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE customers (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id int DEFAULT NULL,
  name varchar(30) NOT NULL,
  surname varchar(30) NOT NULL,
  middle_name varchar(30) DEFAULT NULL,
  address varchar(45) DEFAULT NULL,
  phone_number varchar(18) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE categories (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name enum('GRAPHICS_CARD','CPU','RAM','MOTHERBOARD','POWER_SUPPLY') NOT NULL COMMENT 'Category name'
);

CREATE TABLE products (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(45) NOT NULL,
  category_id int DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  price decimal(10,2) DEFAULT NULL,
  stock int DEFAULT NULL,
  stock_Min int DEFAULT NULL,
  stock_max int DEFAULT NULL,
  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE product_pictures (
  product_id int NOT NULL,
  sequence int NOT NULL,
  url varchar(255) NOT NULL,
  PRIMARY KEY (product_id, url),
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE orders (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer_id int NOT NULL,
  order_date date DEFAULT NULL,
  status enum('PENDING','READY','PROCESSING','COMPLETED','CANCELED') NOT NULL COMMENT 'Order status',
  FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);

CREATE TABLE order_details (
  product_id int NOT NULL,
  order_id int NOT NULL,
  amount int DEFAULT NULL,
  PRIMARY KEY (product_id,order_id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);