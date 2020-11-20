CREATE EXTENSION IF NOT EXISTS dblink;
DELETE FROM offers;
INSERT INTO offers (date, name, author, text)
SELECT * FROM
dblink('host=127.0.0.1 user=postgres password=123 dbname=grabber', 'SELECT date, name, author, text FROM joboffers') as ds1
(date timestamp without time zone, name text, author text, text text);

DELETE FROM cities;
INSERT INTO cities (name) VALUES ('Екатеринбург');
INSERT INTO cities (name) VALUES ('Москва');
INSERT INTO cities (name) VALUES ('Севастополь');
INSERT INTO cities (name) VALUES ('Новороссийск');
INSERT INTO cities (name) VALUES ('Минск');
INSERT INTO cities (name) VALUES ('Мурманск');
INSERT INTO cities (name) VALUES ('Смоленск');
INSERT INTO cities (name) VALUES ('Санкт-Петербург');

DELETE FROM candidates;
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Григорьев Платон Еремеевич', 'Junior', (SELECT id FROM cities WHERE name = 'Екатеринбург'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Симонов Фрол Федорович', 'Junior', (SELECT id FROM cities WHERE name = 'Москва'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Якушев Ермолай Геннадьевич', 'Junior', (SELECT id FROM cities WHERE name = 'Севастополь'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Поляков Иван Аркадьевич', 'Middle', (SELECT id FROM cities WHERE name = 'Новороссийск'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Калинин Агафон Еремеевич', 'Middle', (SELECT id FROM cities WHERE name = 'Минск'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Кузнецов Сергей Альбертович', 'Middle', (SELECT id FROM cities WHERE name = 'Мурманск'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Гущин Эрик Максович', 'Senior', (SELECT id FROM cities WHERE name = 'Смоленск'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Капустин Мартын Гордеевич', 'Senior', (SELECT id FROM cities WHERE name = 'Санкт-Петербург'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Тихонов Нинель Вениаминович', 'Senior', (SELECT id FROM cities WHERE name = 'Москва'));
INSERT INTO candidates (date, name, description, city) VALUES (current_date, 'Дьячков Родион Юлианович', 'Senior', (SELECT id FROM cities WHERE name = 'Екатеринбург'));

DELETE FROM photo;

