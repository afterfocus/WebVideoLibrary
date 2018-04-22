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

    private static final String SQL_ALL_DISKS = "SELECT * FROM disk";
    private static final String SQL_ALL_PERSONS = "SELECT * FROM PERSON";

    private static final String SQL_DISK_BY_ID = "SELECT * FROM DISK WHERE DISK_ID = ?";
    private static final String SQL_PERSON_BY_ID= "SELECT * FROM PERSON WHERE PERSON_ID = ?";

    private static final String SQL_ADD_DISK = "{ call add_new_disk(?, ?, ? ) } ";
    private static final String SQL_ADD_PERSON = "{ call add_new_person(?, ?, ? ) } ";

    private static final String SQL_UPDATE_DISK = "UPDATE DISK SET rus_title = ?, eng_title = ?, release_year = ? WHERE disk_id = ?";
    private static final String SQL_UPDATE_PERSON = "UPDATE PERSON SET SURNAME = ?, NAME = ?, PHONENUMBER = ? WHERE PERSON_ID = ?";

    private static final String SQL_DELETE_DISK = "DELETE FROM disk WHERE disk_id = ?";
    private static final String SQL_DELETE_PERSON = "DELETE FROM PERSON WHERE PERSON_ID = ?";

    private static final String SQL_ISSUE_DISK = "UPDATE DISK SET person_id = ? WHERE disk_id = ?";
    private static final String SQL_RETURN_DISK = "UPDATE DISK SET person_id = NULL WHERE disk_id = ?";

    private static final String SQL_DISKS_BY_PERSON_ID = "SELECT * FROM disk WHERE PERSON_ID = ?";
    private static final String SQL_COUNT_DISKS_BY_PERSON = "SELECT COUNT(*) FROM DISK WHERE PERSON_ID = ?";

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
        try (Connection connection = getConnection()) {

            ArrayList<Disk> disks = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_ALL_DISKS);

            while (resultSet.next()) {
                int personID = resultSet.getInt("person_id");

                if (personID != 0) {
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL_PERSON_BY_ID);
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
    }

    public static List<Disk> getDisksByPerson(int personID) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {

            ArrayList<Disk> disks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_DISKS_BY_PERSON_ID);
            statement.setInt(1, personID);
            ResultSet resultSet = statement.executeQuery();

            statement = connection.prepareStatement(SQL_PERSON_BY_ID);
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
    }

    public static Disk getDisk(int diskID) throws SQLException, NamingException {

        try (Connection connection = getConnection()) {

            Disk disk;
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DISK_BY_ID);
            preparedStatement.setInt(1, diskID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int personID = resultSet.getInt("person_id");
            if (personID != 0) {
                preparedStatement = connection.prepareStatement(SQL_PERSON_BY_ID);
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
    }

    /**
     * Добавляет диск в видеотеку
     */
    public static void addDisk(Disk disk) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareCall(SQL_ADD_DISK);
            statement.setString(1, disk.getRusTitle());
            statement.setString(2, disk.getEngTitle());
            statement.setInt(3, disk.getReleaseYear());
            statement.execute();
            statement.close();
            connection.close();
        }
    }

    public static void updateDisk(Disk disk) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DISK);
            statement.setString(1, disk.getRusTitle());
            statement.setString(2, disk.getEngTitle());
            statement.setInt(3, disk.getReleaseYear());
            statement.setInt(4, disk.getDiskID());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }


    public static void issueDisk(int diskID, int personID) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ISSUE_DISK);
            statement.setInt(1, personID);
            statement.setInt(2, diskID);
            statement.execute();
            statement.close();
            connection.close();
        }
    }

    public static void updatePerson(Person person) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PERSON);
            statement.setString(1, person.getSurname());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPhonenumber());
            statement.setInt(4, person.getPersonID());
            statement.executeUpdate();
            statement.close();
            connection.close();
        }
    }

    public static ArrayList<Person> getPersonList() throws SQLException, NamingException {

        try (Connection connection = getConnection()) {

            ArrayList<Person> persons = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(SQL_ALL_PERSONS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("person_id");

                statement = connection.prepareStatement(SQL_COUNT_DISKS_BY_PERSON);
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
    }

    public static Person getPerson(int personID) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_PERSON_BY_ID);
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
    }

    public static void addPerson(Person person) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareCall(SQL_ADD_PERSON);
            statement.setString(1, person.getSurname());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPhonenumber());
            statement.execute();
            statement.close();
            connection.close();
        }
    }

    /**
     * Удаляет информацию о выдаче диска
     */
    public static void returnDisk(int diskID) throws SQLException, NamingException {
        operate(diskID, SQL_RETURN_DISK);
    }

    /**
     * Удаляет диск из базы данных
     */
    public static void removeDisk(int id) throws SQLException, NamingException {
        operate(id, SQL_DELETE_DISK);
    }

    public static void removePerson(int id) throws SQLException, NamingException {
        operate(id, SQL_DELETE_PERSON);
    }

    private static void operate(int id, String sql) throws SQLException, NamingException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            statement.close();
            connection.close();
        }
    }
}
