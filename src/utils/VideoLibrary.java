package utils;

import beans.Disk;
import beans.Person;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoLibrary {

    private static DataSource dataSource;

    private static Connection getConnection() throws SQLException, NamingException {

        if (dataSource == null) {
            Locale.setDefault(Locale.ENGLISH);
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/WebVideoLibrary");
        }
        return dataSource.getConnection();
    }

    /**
     * Возвращает массив дисков видеотеки
     */
    public static List<Disk> getDiskList() throws SQLException, NamingException {

        ArrayList<Disk> disks = new ArrayList<>();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM disk");

        while (resultSet.next()) {
            int personID = resultSet.getInt("person_id");

            if (personID != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSON_ID = ?");
                preparedStatement.setInt(1, personID);
                ResultSet person = preparedStatement.executeQuery();
                person.next();

                disks.add(new Disk(
                        resultSet.getInt("disk_id"),
                        resultSet.getString("rus_title"),
                        resultSet.getString("eng_title"),
                        resultSet.getInt("release_year"),
                        new Person(
                                personID,
                                person.getString("surname"),
                                person.getString("name"),
                                person.getString("phonenumber"),
                                0))
                );
                person.close();
                preparedStatement.close();

            } else {
                disks.add(new Disk(
                        resultSet.getInt("disk_id"),
                        resultSet.getString("rus_title"),
                        resultSet.getString("eng_title"),
                        resultSet.getInt("release_year"),
                        null));
            }
        }
        resultSet.close();
        statement.close();
        connection.close();
        return disks;
    }

    public static List<Disk> getDisksByPerson(int personID) throws SQLException, NamingException {

        ArrayList<Disk> disks = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM disk WHERE PERSON_ID = ?");
        statement.setInt(1, personID);
        ResultSet resultSet = statement.executeQuery();

        statement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSON_ID = ?");
        statement.setInt(1, personID);
        ResultSet person = statement.executeQuery();
        person.next();

        while (resultSet.next()) {
            disks.add(new Disk(
                    resultSet.getInt("disk_id"),
                    resultSet.getString("rus_title"),
                    resultSet.getString("eng_title"),
                    resultSet.getInt("release_year"),
                    new Person(
                            personID,
                            person.getString("surname"),
                            person.getString("name"),
                            person.getString("phonenumber"),
                            0))
            );
        }
        person.close();
        resultSet.close();
        statement.close();
        connection.close();
        return disks;
    }

    public static Disk getDisk(int diskID) throws SQLException, NamingException {
        Disk disk;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DISK WHERE DISK_ID = ?");
        preparedStatement.setInt(1, diskID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        int personID = resultSet.getInt("person_id");
        if (personID != 0) {
            preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSON_ID = ?");
            preparedStatement.setInt(1, personID);
            ResultSet person = preparedStatement.executeQuery();
            person.next();

            disk = new Disk(
                    resultSet.getInt("disk_id"),
                    resultSet.getString("rus_title"),
                    resultSet.getString("eng_title"),
                    resultSet.getInt("release_year"),
                    new Person(
                            personID,
                            person.getString("surname"),
                            person.getString("name"),
                            person.getString("phonenumber"),
                            0));

            person.close();

        } else {
            disk = new Disk(
                    resultSet.getInt("disk_id"),
                    resultSet.getString("rus_title"),
                    resultSet.getString("eng_title"),
                    resultSet.getInt("release_year"),
                    null);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return disk;
    }

    /**
     * Добавляет диск в видеотеку
     */
    public static void addDisk(String rusTitle, String engTitle, int releaseYear) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareCall("{ call add_new_disk(?, ?, ? ) } ");
        statement.setString(1, rusTitle);
        statement.setString(2, engTitle);
        statement.setInt(3, releaseYear);
        statement.execute();
        statement.close();
        connection.close();
    }

    public static void editDisk(int diskID, String rusTitle, String engTitle, int releaseYear) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISK SET rus_title = ?, eng_title = ?, release_year = ? WHERE disk_id = ?");
        statement.setString(1, rusTitle);
        statement.setString(2, engTitle);
        statement.setInt(3, releaseYear);
        statement.setInt(4, diskID);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    /**
     * Удаляет диск из базы данных
     */
    public static void removeDisk(int id) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM disk WHERE disk_id = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        connection.close();
    }

    public static void issueDisk(int diskID, int personID) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISK SET person_id = ? WHERE disk_id = ?");
        statement.setInt(1, personID);
        statement.setInt(2, diskID);
        statement.execute();
        statement.close();
        connection.close();
    }

    /**
     * Удаляет информацию о выдаче диска
     */
    public static void returnDisk(int diskID) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE DISK SET person_id = NULL WHERE disk_id = ?");
        statement.setInt(1, diskID);
        statement.execute();
        statement.close();
        connection.close();
    }


    public static void editPerson(int personID, String surname, String name, String phonenumber) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE PERSON SET SURNAME = ?, NAME = ?, PHONENUMBER = ? WHERE PERSON_ID = ?");
        statement.setString(1, surname);
        statement.setString(2, name);
        statement.setString(3, phonenumber);
        statement.setInt(4, personID);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public static ArrayList<Person> getPersonList() throws SQLException, NamingException {

        ArrayList<Person> persons = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM PERSON");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("person_id");

            statement = connection.prepareStatement("SELECT COUNT(*) FROM DISK WHERE PERSON_ID = ?");
            statement.setInt(1, id);
            ResultSet numberOfDisks = statement.executeQuery();
            numberOfDisks.next();

            persons.add(new Person(
                    id,
                    resultSet.getString("surname"),
                    resultSet.getString("name"),
                    resultSet.getString("phonenumber"),
                    numberOfDisks.getInt(1))
            );
        }
        resultSet.close();
        statement.close();
        connection.close();
        return persons;
    }

    public static Person getPerson(int personID) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE PERSON_ID = ?");
        preparedStatement.setInt(1, personID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Person person = new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("surname"),
                resultSet.getString("name"),
                resultSet.getString("phonenumber"),
                0
        );
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return person;
    }

    public static void addPerson(String surname, String name, String phonenumber) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareCall("{ call add_new_person(?, ?, ? ) } ");
        statement.setString(1, surname);
        statement.setString(2, name);
        statement.setString(3, phonenumber);
        statement.execute();
        statement.close();
        connection.close();
    }

    public static void removePerson(int id) throws SQLException, NamingException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM PERSON WHERE PERSON_ID = ?");
        statement.setInt(1, id);
        statement.execute();
        statement.close();
        connection.close();
    }
}
