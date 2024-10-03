INSERT INTO app_user (name, email, password, phone_number) VALUES
('Aarav Sharma', 'aarav@gmail.com', 'Password', '9876543210'),
('Vivaan Khanna', 'vivaan.khanna@example.com', '$2a$10$examplePasswordHash2', '9123456789'),
('Aditya Verma', 'aditya.verma@example.com', '$2a$10$examplePasswordHash3', '9234567890'),
('Vihaan Kapoor', 'vihaan.kapoor@example.com', '$2a$10$examplePasswordHash4', '9345678901'),
('Arjun Patel', 'arjun.patel@example.com', '$2a$10$examplePasswordHash5', '9456789012'),
('Sai Reddy', 'sai.reddy@example.com', '$2a$10$examplePasswordHash6', '9567890123');


-- Populating Address table
INSERT INTO address (street, city, state, postal_code) VALUES
('MG Road', 'Bangalore', 'Karnataka', '560001'),
('Park Street', 'Kolkata', 'West Bengal', '700016'),
('Connaught Place', 'Delhi', 'Delhi', '110001'),
('Marine Drive', 'Mumbai', 'Maharashtra', '400002'),
('Law Garden', 'Ahmedabad', 'Gujarat', '380009'),
('Banjara Hills', 'Hyderabad', 'Telangana', '500034'),
('Koregaon Park', 'Pune', 'Maharashtra', '411001'),
('Anna Salai', 'Chennai', 'Tamil Nadu', '600002'),
('Sector 18', 'Noida', 'Uttar Pradesh', '201301'),
('Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
('Nehru Place', 'Delhi', 'Delhi', '110019'),
('MG Road', 'Gurgaon', 'Haryana', '122001'),
('T Nagar', 'Chennai', 'Tamil Nadu', '600017'),
('Salt Lake', 'Kolkata', 'West Bengal', '700064'),
('R S Puram', 'Coimbatore', 'Tamil Nadu', '641002'),
('Jayanagar', 'Bangalore', 'Karnataka', '560011'),
('Hitech City', 'Hyderabad', 'Telangana', '500081'),
('Satellite', 'Ahmedabad', 'Gujarat', '380015'),
('Indira Nagar', 'Bangalore', 'Karnataka', '560038'),
('Powai', 'Mumbai', 'Maharashtra', '400076');

-- Populating Restaurant table
INSERT INTO restaurant (name, address_id, otp, restaurant_location, rating) VALUES
('The Spice Room', 1, '123456', ST_GeomFromText('POINT(77.5946 12.9716)', 4326), 4.5),
('Biryani Hub', 2, '123457', ST_GeomFromText('POINT(88.3639 22.5726)', 4326), 4.0),
('Pizza Palace', 3, '123458', ST_GeomFromText('POINT(77.2090 28.6139)', 4326), 4.3),
('Seafood Delight', 4, '123459', ST_GeomFromText('POINT(72.8777 19.0760)', 4326), 4.8),
('Vegan Paradise', 5, '123460', ST_GeomFromText('POINT(72.5714 23.0225)', 4326), 3.9),
('Grill House', 6, '123461', ST_GeomFromText('POINT(78.4867 17.3850)', 4326), 4.7),
('Italian Bistro', 7, '123462', ST_GeomFromText('POINT(73.8567 18.5204)', 4326), 4.1),
('South Indian Treat', 8, '123463', ST_GeomFromText('POINT(80.2707 13.0827)', 4326), 4.0),
('BBQ Nation', 9, '123464', ST_GeomFromText('POINT(77.3260 28.5355)', 4326), 4.6),
('Royal Cuisine', 10, '123465', ST_GeomFromText('POINT(80.9462 26.8467)', 4326), 4.2),
('Café Delight', 11, '123466', ST_GeomFromText('POINT(77.2195 28.5562)', 4326), 4.4),
('The Urban Grill', 12, '123467', ST_GeomFromText('POINT(77.0672 28.4595)', 4326), 4.5),
('Dosa Corner', 13, '123468', ST_GeomFromText('POINT(80.2391 13.0350)', 4326), 4.0),
('Curry House', 14, '123469', ST_GeomFromText('POINT(88.4183 22.5804)', 4326), 3.8),
('Tea Time', 15, '123470', ST_GeomFromText('POINT(76.9640 11.0046)', 4326), 4.3),
('Saffron Lounge', 16, '123471', ST_GeomFromText('POINT(77.5937 12.9250)', 4326), 4.7),
('The Great Indian Diner', 17, '123472', ST_GeomFromText('POINT(78.4011 17.4500)', 4326), 4.1),
('Fusion Grill', 18, '123473', ST_GeomFromText('POINT(72.5179 23.0307)', 4326), 4.6),
('Gourmet Junction', 19, '123474', ST_GeomFromText('POINT(77.6412 12.9719)', 4326), 4.8),
('Asian Wok', 20, '123475', ST_GeomFromText('POINT(72.9094 19.1257)', 4326), 4.5);

