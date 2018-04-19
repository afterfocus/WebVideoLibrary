package beans;

public class Person {
    private int personID;
    private String surname;
    private String name;
    private String phonenumber;
    private int issuedDisks;

    public Person(int personID, String surname, String name, String phonenumber, int issuedDisks) {
        this.personID = personID;
        this.surname = surname;
        this.name = name;
        this.phonenumber = phonenumber;
        this.issuedDisks = issuedDisks;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getIssuedDisks() {
        return issuedDisks;
    }

    public void setIssuedDisks(int issuedDisks) {
        this.issuedDisks = issuedDisks;
    }
}
