DELETE FROM offers;
INSERT INTO offers (date, name, author, text)
SELECT * FROM
dblink('host=127.0.0.1 user=postgres password=123 dbname=grabber', 'SELECT date, name, author, text FROM joboffers') as ds1
(date timestamp without time zone, name text, author text, text text);

DELETE FROM candidates;
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Григорьев Платон Еремеевич', 'Junior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Симонов Фрол Федорович', 'Junior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Якушев Ермолай Геннадьевич', 'Junior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Поляков Иван Аркадьевич', 'Middle');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Калинин Агафон Еремеевич', 'Middle');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Кузнецов Сергей Альбертович', 'Middle');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Гущин Эрик Максович', 'Senior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Капустин Мартын Гордеевич', 'Senior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Тихонов Нинель Вениаминович', 'Senior');
INSERT INTO candidates (date, name, description) VALUES (current_date, 'Дьячков Родион Юлианович', 'Senior');