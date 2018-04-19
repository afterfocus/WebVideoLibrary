-- СОЗДАНИЕ ТАБЛИЦ
CREATE TABLE person (
    person_id     INTEGER NOT NULL,
    surname       VARCHAR2(80) NOT NULL,
    name          VARCHAR2(80) NOT NULL,
    phonenumber   VARCHAR2(80)
);

ALTER TABLE person ADD CONSTRAINT person_pk PRIMARY KEY ( person_id );

CREATE TABLE disk (
    disk_id        INTEGER NOT NULL,
    rus_title      VARCHAR2(80),
    eng_title      VARCHAR2(80),
    release_year   INTEGER,
    person_id      INTEGER
);

ALTER TABLE disk ADD CONSTRAINT disk_pk PRIMARY KEY ( disk_id );

ALTER TABLE disk
    ADD CONSTRAINT person FOREIGN KEY ( person_id )
        REFERENCES person ( person_id );
        
        
        
-- СОЗДАНИЕ СЧЕТЧИКОВ

CREATE SEQUENCE person_id_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE SEQUENCE disk_id_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;



-- ПРОЦЕДУРЫ ДОБАВЛЕНИЯ ДАННЫХ

CREATE OR REPLACE PROCEDURE add_new_person (
    surname       VARCHAR2,
    name          VARCHAR2,
    phonenumber   VARCHAR2
) IS
BEGIN
    INSERT INTO person VALUES (
        person_id_sequence.nextval,
        surname,
        name,
        phonenumber
    );
    COMMIT;
END add_new_person;
/

CREATE OR REPLACE PROCEDURE add_new_disk (
    rus_title      IN VARCHAR2,
    eng_title      IN VARCHAR2,
    release_year   IN INTEGER
) IS
BEGIN
        INSERT INTO disk VALUES (
            disk_id_sequence.nextval,
            rus_title,
            eng_title,
            release_year,
            NULL
        );
    COMMIT;
END add_new_disk;
/



-- ЗАПОЛНЕНИЕ ТАБЛИЦ

BEGIN
    add_new_person('Концова','Яна','+7(917)175-30-90');
    add_new_person('Дунюшкин','Николай','227-94-92');
    add_new_person('Воронов','Артём','210-50-59');
    add_new_person('Краснобаев','Алексей','+7(927)745-24-25');
    add_new_person('Корцев','Матвей','+7(937)120-10-77');
    add_new_person('Дуняшин','Егор','+7(937)223-55-40');
    add_new_person('Потапов','Сергей','+7(927)780-32-13');
    add_new_person('Галанова','Дарья','212-52-59');
    add_new_person('Костюшкина','Мария','+7(927)735-30-25');
    add_new_person('Троц','Виктория','+7(937)122-12-77');
    add_new_disk('Иван Васильевич меняет профессию','-',1973);
    add_new_disk('Интерстеллар','Interstellar',2014);
    add_new_disk('Криминальное чтиво','Pulp Fiction',1994);
    add_new_disk('Крёстный отец','The Godfather',1972);
    add_new_disk('Леон','Leon',1994);
    add_new_disk('Список Шиндлера','Schindler''s List',1993);
    add_new_disk('Форрест Гамп','Forrest Gump',1994);
    add_new_disk('1+1','Intouchables',2011);
    add_new_disk('Бойцовский клуб','Fight Club',1999);
    add_new_disk('Властелин колец: Возвращение Короля','The Lord of the Rings: The Return of the King',2003);
    add_new_disk('Достучаться до небес','Knockin'' on Heaven''s Door',1997);
    add_new_disk('Жизнь прекрасна','La vita e bella',1997);
    add_new_disk('Зелёная миля','The Green Mile',1999);
    add_new_disk('Игры разума','A Beautiful Mind',2001);
    add_new_disk('Король Лев','The Lion King',1994);
    add_new_disk('Начало','Inception ',2010);
    add_new_disk('Операция «Ы» и другие приключения Шурика','-',1965);
    add_new_disk('Побег из Шоушенка','The Shawshank Redemption',1994);
    add_new_disk('Престиж','The Prestige',2006);
    add_new_disk('Гладиатор','Gladiator',2000);
    add_new_disk('Назад в будущее','Back to the Future',1985);
    add_new_disk('Карты, деньги, два ствола','Lock, Stock and Two Smoking Barrels',1998);
    add_new_disk('Матрица','The Matrix',1999);
    add_new_disk('Поймай меня, если сможешь','Catch Me If You Can',2002);
END;
/

UPDATE DISK SET person_id = 1 WHERE disk_id = 2;
UPDATE DISK SET person_id = 7 WHERE disk_id = 20;
UPDATE DISK SET person_id = 5 WHERE disk_id = 4;
UPDATE DISK SET person_id = 4 WHERE disk_id = 6;
UPDATE DISK SET person_id = 7 WHERE disk_id = 8;
UPDATE DISK SET person_id = 10 WHERE disk_id = 11;
UPDATE DISK SET person_id = 4 WHERE disk_id = 13;
UPDATE DISK SET person_id = 5 WHERE disk_id = 14;
UPDATE DISK SET person_id = 10 WHERE disk_id = 16;
UPDATE DISK SET person_id = 4 WHERE disk_id = 18;
UPDATE DISK SET person_id = 3 WHERE disk_id = 22;
UPDATE DISK SET person_id = 1 WHERE disk_id = 24;
COMMIT;
