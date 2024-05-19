TRUNCATE TABLE request CASCADE;

INSERT INTO request (id, status, passenger_id, ride_id)
VALUES
    (1, 'ACCEPTED',  2, 17),
    (2, 'PENDING', 3, 18),
    (3, 'IGNORED', 2, 6),
    (4, 'PENDING', 5, 7),
    (5, 'PENDING', 4, 4),
    (6, 'PENDING', 1, 4),
    (7, 'PENDING', 5, 5),
    (8, 'PENDING', 7, 8),
    (9, 'PENDING', 2, 12),
    (10,'PENDING', 6, 14),
    (11, 'PENDING', 6, 10),
    (12, 'PENDING', 4, 2),
    (13, 'PENDING', 3, 15),
    (14, 'PENDING', 2, 16),
    (15, 'PENDING', 1, 10),
    (16, 'PENDING', 1, 11),
    (17, 'PENDING', 2, 13),
    (18, 'PENDING', 1, 9),
    (19, 'PENDING', 5, 18)