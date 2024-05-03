TRUNCATE TABLE passenger CASCADE;

INSERT INTO passenger (id, user_id, phone_number, rating)
VALUES
    (1, 2, '071234567', 4.9),
    (2, 3, '070987654', 5.0),
    (3, 4, '078369258', 4.7),
    (4, 5, '075741258', 3.0),
    (5, 6, '070258789', 5.0),
    (6, 7, '077123987', 4.8),
    (7, 8, '075369456', 5.0)