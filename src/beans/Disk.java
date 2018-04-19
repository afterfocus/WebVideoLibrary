package beans;

public class Disk {
    private int diskID;
    private String rusTitle;
    private String engTitle;
    private int releaseYear;
    private Person person;

    public Disk(int diskID, String rusTitle, String engTitle, int releaseYear, Person person) {
        this.diskID = diskID;
        this.rusTitle = rusTitle;
        this.engTitle = engTitle;
        this.releaseYear = releaseYear;
        this.person = person;
    }

    public int getDiskID() {
        return diskID;
    }

    public void setDiskID(int diskID) {
        this.diskID = diskID;
    }

    public String getRusTitle() {
        return rusTitle;
    }

    public void setRusTitle(String rusTitle) {
        this.rusTitle = rusTitle;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) { this.person = person; }
}
