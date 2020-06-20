
public class Donor {

    // instance variables
    private int ID;
    private String firstName;
    private String lastName;
    private String country;
    private long phone;
    private double contribution;

    // constructor
    public Donor(
            int ID,
            String firstName,
            String lastName,
            String country,
            long phone,
            double contribution) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.phone = phone;
        this.contribution = contribution;
    }

    // getters
    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getCountry() {
        return this.country;
    }

    public long getPhone() {
        return this.phone;
    }

    public double getContribution() {
        return this.contribution;
    }

    // setters
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }

    // common methods
    public Donor getInstance() {
        return this;
    }

    /*
        Converts record to string format
     */
    public String toString() {
        String output;

        output = firstName + ","
                + lastName + ","
                + country + ","
                + phone + ","
                + (int) contribution + ","
                + ID;

        return output;
    }

} // END Donor CLASS
