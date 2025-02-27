INSERT INTO categories (name) VALUES('GRAPHICS_CARD'),('CPU'),('RAM'),('MOTHERBOARD'),('POWER_SUPPLY');

INSERT INTO products (name, category_id, price, stock, stock_min, stock_max, description)
VALUES
('NVIDIA RTX 4080 Graphics Card', 1, 1299.99, 40, 5, 80, 'High-performance GPU for gaming and rendering'),
('AMD Radeon RX 7900 XT', 1, 899.99, 35, 5, 70, 'Powerful graphics card with RDNA3 architecture'),
('Intel Core i9-13900K', 2, 589.99, 50, 10, 90, 'Top-tier CPU with 24 cores and high overclocking potential'),
('AMD Ryzen 9 7950X', 2, 699.99, 45, 8, 85, 'Flagship processor with high multi-threaded performance'),
('Corsair Vengeance RGB 32GB RAM', 3, 149.99, 120, 20, 300, 'High-speed DDR5 RAM with RGB lighting'),
('G.Skill Trident Z5 64GB RAM', 3, 329.99, 100, 10, 250, 'Extreme-performance DDR5 RAM for enthusiasts'),
('ASUS ROG Maximus Z790 Hero', 4, 499.99, 30, 5, 70, 'Premium gaming motherboard with PCIe 5.0 and DDR5 support'),
('MSI MAG B650 Tomahawk WiFi', 4, 259.99, 50, 10, 100, 'Mid-range AM5 motherboard with solid VRM design'),
('Corsair RM850x Power Supply', 5, 159.99, 80, 10, 150, 'Fully modular 850W PSU with 80+ Gold certification'),
('EVGA SuperNOVA 1000 G5 PSU', 5, 189.99, 60, 8, 120, 'High-performance 1000W power supply with 80+ Gold rating');


INSERT INTO users (email, password, role) VALUES('a@g.com', 'qwerty', 'CUSTOMER');
INSERT INTO users (email, password, role) VALUES('b@g.com', 'qwerty', 'MANAGER');

INSERT INTO customers (user_id, name, surname) VALUES(1, 'Rodrigo', 'Fernandes');

INSERT INTO managers (user_id, name, surname) VALUES(2, 'Muhammad', 'Avdol');