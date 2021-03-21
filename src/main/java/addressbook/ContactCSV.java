package addressbook;

import com.opencsv.bean.CsvBindByName;

public class ContactCSV {

    @CsvBindByName(column = "FirstName", required = true)
    private String firstName;

    @CsvBindByName(column = "LastName", required = true)
    private String lastName;

    @CsvBindByName(column = "Address", required = true)
    private String address;

    @CsvBindByName(column = "City", required = true)
    private String city;

    @CsvBindByName(column = "State", required = true)
    private String state;

    @CsvBindByName(column = "Zip", required = true)
    private String zip;

    @CsvBindByName(column = "PhoneNumber", required = true)
    private String phoneNum;

    @CsvBindByName(column = "Email", required = true)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName +
                " Address: " + address + " " + city + " " + state +
                " ZIP: " + zip + " Phone Number: " + phoneNum + " Email: " + email + "\n";
    }
}
