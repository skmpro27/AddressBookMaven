package addressbook;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookCSV {

    @CsvBindByName(column = "BookName", required = true)
    public String bookName;

    @CsvBindByName(column = "Path", required = true)
    public String path;

    public String firstName;
    public String lastName;
    public int index;

    List<ContactCSV> contactList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addContactFromFile() throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CsvToBeanBuilder<ContactCSV> csvCsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvCsvToBeanBuilder.withType(ContactCSV.class);
        csvCsvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvCsvToBeanBuilder.withSeparator(',');
        CsvToBean<ContactCSV> csvToBean = csvCsvToBeanBuilder.build();
        contactList = csvToBean.parse();
    }

    public boolean checkName() {
        System.out.print("\nEnter First Name: ");
        firstName = sc.nextLine();
        System.out.print("Enter Last Name: ");
        lastName = sc.nextLine();
        for(ContactCSV contact: contactList) {
                 if (firstName.equals(contact.getFirstName()) && lastName.equals(contact.getLastName()))
            return true;
        }
        return false;
    }

    //taking input from user
    public void addContact() throws Exception {
        String address = null, city = null, state = null, zip = null, phoneNum = null, email = null;
        String check = "y";
        System.out.println("\nAdd Contact");

        while(check.equals("y") || check.equals("Y")) {
            if (!checkName()) {
                System.out.print("Enter Address: ");
                address = sc.nextLine();
                System.out.print("Enter City: ");
                city = sc.nextLine();
                System.out.print("Enter State ");
                state = sc.nextLine();
                System.out.print("Enter ZIP Code: ");
                zip = sc.nextLine();
                System.out.print("Enter Phone Number: ");
                phoneNum = sc.nextLine();
                System.out.print("Enter Email: ");
                email = sc.nextLine();
                FileWriter fileWriter = new FileWriter(this.path, true);
                fileWriter.write("\n" + firstName + "," + lastName + "," + address + "," + city + "," + state + "," + zip + "," + phoneNum + "," + email);
                fileWriter.close();
                addContactFromFile();
            } else
                throw new AlreadyExistContactException("Contact name already exist");

            System.out.print("Do you want to add more Contacts(y/n): ");
            check = sc.nextLine();

        }
    }

    //to edit particular contact
    public String editContact() throws IOException {
        System.out.println("\nEdit Contact");
        if (checkName()) {
            System.out.print("\nEnter First Name: ");
            contactList.get(index).setFirstName(sc.nextLine());
            System.out.print("Enter Last Name: ");
            contactList.get(index).setLastName(sc.nextLine());
            System.out.print("Enter Address: ");
            contactList.get(index).setAddress(sc.nextLine());
            System.out.print("Enter City: ");
            contactList.get(index).setCity(sc.nextLine());
            System.out.print("Enter State ");
            contactList.get(index).setState(sc.nextLine());
            System.out.print("Enter ZIP Code: ");
            contactList.get(index).setZip(sc.nextLine());
            System.out.print("Enter Phone Number: ");
            contactList.get(index).setPhoneNum(sc.nextLine());
            System.out.print("Enter Email: ");
            contactList.get(index).setEmail(sc.nextLine());
            System.out.println();
            System.out.println(contactList.get(index));
            writeWholeFile();
            return "Updated";
        }
        return "Name not found";
    }

    //to remove contact
    public String removeContact() throws IOException {
        System.out.println("\nRemove Contact");
        if (checkName()) {
            contactList.remove(index);
            writeWholeFile();
            return "Deleted";
        }
        return "Name not found";
    }

    public void writeWholeFile() {

    }

    public void displayContact() {
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println();
            System.out.println(contactList.get(i));
        }
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "BookName='" + bookName + '\'' +
                ", Path='" + path + '\'' +
                '}';
    }
}
