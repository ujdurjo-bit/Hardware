DELETE FROM USERS_AUTHORITY;
DELETE FROM AUTHORITY;
DELETE FROM USERS;

DELETE FROM hardware;
DELETE FROM hardware_type;

INSERT INTO hardware_type (name, description) VALUES
('CPU', 'Central Processing Unit'),
('GPU', 'Graphics Processing Unit'),
('MBO', 'Mainboard'),
('RAM', 'Random Access Memory'),
('STORAGE', 'SSD or HDD'),
('OTHER', 'Other hardware components');


INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Intel Core i7-13700K', 'CPU-001', 450.00, (SELECT id FROM hardware_type WHERE name='CPU' LIMIT 1), 15);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('AMD Ryzen 9 7950X', 'CPU-002', 700.00, (SELECT id FROM hardware_type WHERE name='CPU' LIMIT 1), 8);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('NVIDIA RTX 4080', 'GPU-001', 1200.00, (SELECT id FROM hardware_type WHERE name='GPU' LIMIT 1), 5);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('AMD Radeon RX 7900 XT', 'GPU-002', 900.00, (SELECT id FROM hardware_type WHERE name='GPU' LIMIT 1), 7);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('ASUS ROG STRIX Z790-E', 'MBO-001', 400.00, (SELECT id FROM hardware_type WHERE name='MBO' LIMIT 1), 10);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Corsair Vengeance 32GB DDR5', 'RAM-001', 180.00, (SELECT id FROM hardware_type WHERE name='RAM' LIMIT 1), 25);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Samsung 980 Pro 1TB NVMe', 'STORAGE-001', 120.00, (SELECT id FROM hardware_type WHERE name='STORAGE' LIMIT 1), 30);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Noctua NH-D15 CPU Cooler', 'OTHER-001', 100.00, (SELECT id FROM hardware_type WHERE name='OTHER' LIMIT 1), 12);

insert into USERS(id, username, password)
values
    (1, 'user', '$2a$12$h0HcS2QDb/7zPASbLa2GoOTSRP6CWK0oX7pCK.dPjkM6L5N4pNovi'), -- password = user
    (2, 'admin', '$2a$12$INo0nbj40sQrTB7b28KJput/bNltGmFyCfRsUhvy73qcXo5/XdsTG'); -- password = admin

insert into AUTHORITY (id, authority_name)
values
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

insert into USERS_AUTHORITY (user_id, authority_id)
values
    (1, 2),
    (2, 1);

