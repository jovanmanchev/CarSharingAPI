TRUNCATE TABLE ride CASCADE;

INSERT INTO ride (id, driver_id, location_from, location_to, time_from, time_to, price_per_person, chattiness, pets, smoking, music, ride_status)
VALUES
    (1, 1, 'Skopje', 'Veles', '2024-05-29 17:00:00.000000', null, 500, true, true, false, true, 'CREATED'),
    (2, 1, 'Skopje', 'Ohrid', '2024-05-03 11:00:00.000000', null, 1500, true, true, false, true, 'CANCELED'),
    (3, 1, 'Veles', 'Skopje', '2024-05-29 19:00:00.000000', null, 500, true, true, false, true, 'CREATED'),
    (4, 1, 'Skopje', 'Ohrid', '2024-04-28 06:30:00.000000', '2024-04-28 09:00:00.000000', 1400, true, true, false, true, 'FINISHED'),
    (5, 1, 'Ohrid', 'Skopje', '2024-04-28 10:00:00.000000', '2024-04-28 13:30:00.000000', 1500, true, true, false, true, 'FINISHED'),
    (6, 1, 'Skopje', 'Kavadarci', '2024-04-26 12:30:00.000000', '2024-04-26 13:56:00.000000', 700, true, true, false, true, 'FINISHED'),
    (7, 1, 'Kavadarci', 'Skopje', '2024-04-26 17:00:00.000000', '2024-04-26 18:42:00.000000', 800, true, true, false, true, 'FINISHED'),
    (8, 2, 'Dojran', 'Ohrid', '2024-05-01 08:30:00.000000', '2024-05-01 12:11:00.000000', 1600, false, true, false, true, 'FINISHED'),
    (9, 2, 'Ohrid', 'Dojran', '2024-05-01 17:00:00.000000', '2024-05-01 20:42:00.000000', 1600, false, true, false, true, 'FINISHED'),
    (10, 2, 'Dojran', 'Skopje', '2024-05-15 07:00:00.000000', null, 1400, false, true, false, true, 'CREATED'),
    (11, 3, 'Krushevo', 'Ohrid', '2024-05-25 14:00:00.000000', null, 600, true, true, false, false, 'CREATED'),
    (12, 3, 'Skopje', 'Krushevo', '2024-05-01 10:00:00.000000', null, 1000, true, true, false, false, 'CANCELED'),
    (13, 3, 'Krushevo', 'Skopje', '2024-05-25 14:00:00.000000', '2024-05-25 16:53:00.000000', 1000, true, true, false, false, 'FINISHED'),
    (14, 3, 'Skopje', 'Krushevo', '2024-05-02 16:00:00.000000', '2024-05-02 20:11:00.000000', 1000, true, true, false, false, 'FINISHED'),
    (15, 4, 'Kratovo', 'Bitola', '2024-05-10 08:00:00.000000', null, 1200, true, false, true, true, 'CREATED'),
    (16, 4, 'Bitola', 'Kratovo', '2024-05-10 16:00:00.000000', null, 1000, true, false, true, true, 'CREATED'),
    (17, 4, 'Kratovo', 'Skopje', '2024-04-22 08:00:00.000000', '2024-04-22 09:38:00.000000', 500, true, false, true, true, 'FINISHED'),
    (18, 4, 'Skopje', 'Kratovo', '2024-04-25 12:00:00.000000', '2024-04-25 13:50:00.000000', 500, true, false, true, true, 'FINISHED')