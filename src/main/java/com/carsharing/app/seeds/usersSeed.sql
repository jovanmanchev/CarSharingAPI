TRUNCATE TABLE users CASCADE;

INSERT INTO users (id, first_name, last_name, age, email, encrypted_password, type)
VALUES
    (1, 'Admin', 'Admin', 23, 'admin@admin.com', '#Admin123', 'ADMIN'),
    (2, 'Maja', 'Spasovska', 32, 'maja.spasovska@gmail.com', '#Maja123', 'PASSENGER'),
    (3, 'Eva', 'Milosevska', 28, 'eva.milosevska@hotamil.com', '#Eva123', 'PASSENGER'),
    (4, 'Stefan', 'Gjorev', 49, 'stefan.gjorev@gmail.com', '#Stefan123', 'PASSENGER'),
    (5, 'Filip', 'Dimitrovski', 19, 'filip.dimitrovski@hotmail.com', '#Filip123', 'PASSENGER'),
    (6, 'Kristina', 'Sekulovska', 29, 'kristina.sekulovska@hotmail.com', '#Kristina123', 'PASSENGER'),
    (7, 'Vedran', 'Filipov', 49, 'vedran.filipov@hotmail.com', '#Vedran123', 'PASSENGER'),
    (8, 'Svetle', 'Jovanovska', 21, 'svetle.jovanovska@hotmail.com', '#Svetle123', 'PASSENGER'),
    (9, 'Dimitar', 'Savreski', 28, 'dimitar.savreski@hotmail.com', '#Dimitar123', 'DRIVER'),
    (10, 'Anstasija', 'Popovska', 30, 'anastasija.popovska@gmail.com', '#Anastasija123', 'DRIVER'),
    (11, 'Ivana', 'Ivanovska', 44, 'ivana.ivanovska@yahoo.com', '#Ivana123', 'DRIVER'),
    (12, 'Ivan', 'Ristovski', 23, 'ivan.ristovski@admin.com', '#Ivan123', 'DRIVER')