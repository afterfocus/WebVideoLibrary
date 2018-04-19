-- �������� ������
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
        
        
        
-- �������� ���������

CREATE SEQUENCE person_id_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE SEQUENCE disk_id_sequence MINVALUE 1 START WITH 1 INCREMENT BY 1 NOCACHE;



-- ��������� ���������� ������

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



-- ���������� ������

BEGIN
    add_new_person('�������','���','+7(917)175-30-90');
    add_new_person('��������','�������','227-94-92');
    add_new_person('�������','����','210-50-59');
    add_new_person('����������','�������','+7(927)745-24-25');
    add_new_person('������','������','+7(937)120-10-77');
    add_new_person('�������','����','+7(937)223-55-40');
    add_new_person('�������','������','+7(927)780-32-13');
    add_new_person('��������','�����','212-52-59');
    add_new_person('����������','�����','+7(927)735-30-25');
    add_new_person('����','��������','+7(937)122-12-77');
    add_new_disk('���� ���������� ������ ���������','-',1973);
    add_new_disk('������������','Interstellar',2014);
    add_new_disk('������������ �����','Pulp Fiction',1994);
    add_new_disk('������� ����','The Godfather',1972);
    add_new_disk('����','Leon',1994);
    add_new_disk('������ ��������','Schindler''s List',1993);
    add_new_disk('������� ����','Forrest Gump',1994);
    add_new_disk('1+1','Intouchables',2011);
    add_new_disk('���������� ����','Fight Club',1999);
    add_new_disk('��������� �����: ����������� ������','The Lord of the Rings: The Return of the King',2003);
    add_new_disk('����������� �� �����','Knockin'' on Heaven''s Door',1997);
    add_new_disk('����� ���������','La vita e bella',1997);
    add_new_disk('������ ����','The Green Mile',1999);
    add_new_disk('���� ������','A Beautiful Mind',2001);
    add_new_disk('������ ���','The Lion King',1994);
    add_new_disk('������','Inception ',2010);
    add_new_disk('�������� �ۻ � ������ ����������� ������','-',1965);
    add_new_disk('����� �� ��������','The Shawshank Redemption',1994);
    add_new_disk('�������','The Prestige',2006);
    add_new_disk('���������','Gladiator',2000);
    add_new_disk('����� � �������','Back to the Future',1985);
    add_new_disk('�����, ������, ��� ������','Lock, Stock and Two Smoking Barrels',1998);
    add_new_disk('�������','The Matrix',1999);
    add_new_disk('������ ����, ���� �������','Catch Me If You Can',2002);
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
