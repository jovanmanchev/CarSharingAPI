TRUNCATE TABLE car CASCADE;

INSERT INTO car (id, driver_id, model, passenger_capacity, luggage_per_person)
VALUES
    (1, 1, 'Audi A3', 3, 'BACKPACK'),
    (2, 2, 'Peugeot 308', 3, 'SMALL_SUITCASE'),
    (3, 3, 'Renault Clio 4', 2, 'BACKPACK'),
    (4, 4, 'Peugeot 3008', 3, 'BIG_SUITCASE')