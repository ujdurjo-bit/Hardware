

INSERT INTO hardware_type (name, description) VALUES
('CPU', 'Central Processing Unit'),
('GPU', 'Graphics Processing Unit'),
('MBO', 'Mainboard'),
('RAM', 'Random Access Memory'),
('STORAGE', 'SSD or HDD'),
('OTHER', 'Other hardware components');


INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Intel Core i7-13700K', 'CPU-001', 450.00, (SELECT id FROM hardware_type WHERE name='CPU'), 15);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('AMD Ryzen 9 7950X', 'CPU-002', 700.00, (SELECT id FROM hardware_type WHERE name='CPU'), 8);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('NVIDIA RTX 4080', 'GPU-001', 1200.00, (SELECT id FROM hardware_type WHERE name='GPU'), 5);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('AMD Radeon RX 7900 XT', 'GPU-002', 900.00, (SELECT id FROM hardware_type WHERE name='GPU'), 7);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('ASUS ROG STRIX Z790-E', 'MBO-001', 400.00, (SELECT id FROM hardware_type WHERE name='MBO'), 10);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Corsair Vengeance 32GB DDR5', 'RAM-001', 180.00, (SELECT id FROM hardware_type WHERE name='RAM'), 25);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Samsung 980 Pro 1TB NVMe', 'STORAGE-001', 120.00, (SELECT id FROM hardware_type WHERE name='STORAGE'), 30);

INSERT INTO hardware (name, snum, price, type_id, quantity) VALUES
('Noctua NH-D15 CPU Cooler', 'OTHER-001', 100.00, (SELECT id FROM hardware_type WHERE name='OTHER'), 12);



SELECT * FROM hardware;