package addressbook;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AddressBookMain {

    private InputType type;
    private InputTypeCompare typeCompare;
    private InputTypeDictionary typeDictionary;

    private int numBook = 0;
    private String cityState;
    private String firstName;
    private String lastName;
    private String string;
    private static final String MASTER_PATH = "C:\\Users\\Manish\\Development\\Assignment\\Day28\\AddressBookM\\src\\main\\resources\\MasterAddressBookList.csv";
    private static final String COMMON_PATH = "C:\\\\Users\\\\Manish\\\\Development\\\\Assignment\\\\Day28\\\\AddressBookM\\\\src\\\\main\\\\resources\\\\";

    public Scanner scanner = new Scanner(System.in);

    public static Map<String, String> stateDictionary = new HashMap<>();
    public static Map<String, String> cityDictionary = new HashMap<>();

    public List<ContactCSV> allContacts = new ArrayList<>();
    public List<AddressBookCSV> book;

    public void addAddressFromFile(String callType) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(MASTER_PATH));
        CsvToBeanBuilder<AddressBookCSV> csvCsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvCsvToBeanBuilder.withType(AddressBookCSV.class);
        csvCsvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        csvCsvToBeanBuilder.withSeparator(',');
        CsvToBean<AddressBookCSV> csvToBean = csvCsvToBeanBuilder.build();
        book = csvToBean.parse();
        book.forEach(addressBook -> System.out.println("Name: " + addressBook.bookName + "   Path: " + addressBook.path));

        if (callType.equals("Start of Program"))
            book.forEach(addressBook -> {
                try {
                    addressBook.addContactFromFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    public void writeAddressBook() throws IOException {

    }

    public void writeWholeFile() throws IOException {

    }

    public void addAddressBook() throws Exception {
        System.out.print("Enter name of new Address Book: ");
        String name = scanner.nextLine();
        String path = COMMON_PATH + name + ".csv";
        File file = new File(path);
        file.createNewFile();
        writeHeaderInFile(path);
        FileWriter fileWriter = new FileWriter(MASTER_PATH, true);
        fileWriter.write("\n" + name + "," + path);
        fileWriter.close();
        addAddressFromFile("Create new AddressBook");
    }

    private void writeHeaderInFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        fileWriter.write("FirstName,LastName,Address,City,State,Zip,PhoneNumber,Email");
        fileWriter.close();
    }

    public void removeAddressBook() throws IOException {
        if (book.size() > 1) {
            chooseAddressBook();
            book.remove(numBook);
            writeWholeFile();
        } else
            System.out.println("Only default Address Book is Available");
    }

    private void chooseAddressBook() throws IOException {
        System.out.println("Current Address Book: " + book.get(numBook));
        if (book.size() > 1) {
            for (int i = 0; i < book.size(); i++)
                System.out.println(i + ". " + book.get(i));
            System.out.print("Choose number for Address Book: ");
            numBook = Integer.parseInt(scanner.nextLine());
        }
    }

    private void askDetails() {
        System.out.print("\nEnter " + string);
        cityState = scanner.nextLine();
        System.out.print("Enter First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter out Last Name: ");
        lastName = scanner.nextLine();
    }

    private void reduceToSingleContactList() {
        book.forEach(addressBook -> allContacts.addAll(addressBook.contactList));
    }

    private  void reduceListToNull() {
        allContacts.clear();
    }

    private final Predicate<ContactCSV> isPresentInState = contact -> type.stateOrCity(contact).equals(cityState) &&
                                                        contact.getFirstName().equals(firstName) &&
                                                        contact.getLastName().equals(lastName);
    private final Consumer<String> displayCount = nameOfPlace -> System.out
                                                          .println("Number of person in " + nameOfPlace + ": " + allContacts.stream()
                                                          .filter(contact -> nameOfPlace.equals(type.stateOrCity(contact))).count());

    private void personStateOrCity() {
        askDetails();
        //to search a person by his name and city or state
        allContacts.stream()
                .filter(isPresentInState)
                .forEach(System.out::println);
    }

    private void personStateOrCityDictionary() {
        System.out.print("\nEnter " + string);
        cityState = scanner.nextLine();
        //to search for persons in city or state and maintain dictionary for the same
        allContacts.stream()
                .filter(contact -> type.stateOrCity(contact).equals(cityState))
                .forEach(contact -> typeDictionary.stateOrCityDictionary().put(contact.getFirstName() + " " + contact.getLastName(), type.stateOrCity(contact)));

       typeDictionary.stateOrCityDictionary().forEach((key, value) -> System.out.println("Name: " + key));
    }

    private void getCountCityState() {
        System.out.println();
        //to make a list of city
        allContacts.stream()
                .map(type::stateOrCity)
                .distinct()
                .forEach(displayCount);
    }

    private void sortByNameOrAddress() {
        System.out.println();
        //sort the contact by name or by city, state or zip
        allContacts.stream()
                .sorted(typeCompare.nameOrAddressCompare())
                .forEach(System.out::println);
    }

    public void choice() {
        try {
            System.out.println();
            System.out.println("Current Address Book: " + book.get(numBook));
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Display Contact");
            System.out.println("5. Add Address Book");
            System.out.println("6. Delete Address Book");
            System.out.println("7. Switch Address Book");
            System.out.println("8. Search person by State");
            System.out.println("9. Search person by City");
            System.out.println("10. View persons in State");
            System.out.println("11. View persons in City");
            System.out.println("12. Number of persons in State");
            System.out.println("13. Number of persons in City");
            System.out.println("14. Sort by Name");
            System.out.println("15. Sort by Address");
            System.out.println("16. Exit");

            System.out.print("Enter your choice(1-16): ");
            String choose = scanner.nextLine();
            reduceToSingleContactList();
            switch (choose) {

                case "1":
                    book.get(numBook).addContact();
                    break;

                case "2":
                    chooseAddressBook();
                    System.out.println(book.get(numBook).editContact());
                    break;

                case "3":
                    chooseAddressBook();
                    System.out.println(book.get(numBook).removeContact());
                    break;

                case "4":
                    chooseAddressBook();
                    book.get(numBook).displayContact();
                    break;

                case "5":
                    addAddressBook();
                    break;

                case "6":
                    removeAddressBook();
                    break;

                case "7":
                    chooseAddressBook();
                    break;

                case "8":
                    string = "State: ";
                    type = InputType.ENTERED_STATE;
                    personStateOrCity();
                    break;

                case "9":
                    string = "City: ";
                    type = InputType.ENTERED_CITY;
                    personStateOrCity();
                    break;

                case "10":
                    string = "State: ";
                    type = InputType.ENTERED_STATE;
                    typeDictionary = InputTypeDictionary.ENTERED_STATE;
                    personStateOrCityDictionary();
                    break;

                case "11":
                    string = "City: ";
                    type = InputType.ENTERED_CITY;
                    typeDictionary = InputTypeDictionary.ENTERED_CITY;
                    personStateOrCityDictionary();
                    break;

                case "12":
                    type = InputType.ENTERED_STATE;
                    getCountCityState();
                    break;

                case "13":
                    type = InputType.ENTERED_CITY;
                    getCountCityState();
                    break;

                case "14":
                    typeCompare = InputTypeCompare.SORT_BY_NAME;
                    sortByNameOrAddress();
                    break;

                case "15":
                    typeCompare = InputTypeCompare.SORT_BY_ADDRESS;
                    sortByNameOrAddress();
                    break;

                case "16":
                    System.exit(0);

                default:
                    System.out.println("Invalid Input");
            }
        } catch (AlreadyExistContactException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please enter number in given range only");
        } catch (NumberFormatException e) {
            System.out.println("Please enter only valid input");
        } catch (IOException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------X-------------\nPress any key to continue: ");
        string = scanner.nextLine();
        reduceListToNull();
        choice();
    }

    public static void main(String[] args) {
        try {
            System.out.println("Welcome to Address Book Program");
            AddressBookMain bookMain = new AddressBookMain();
            bookMain.addAddressFromFile("Start of Program");
            bookMain.choice();
        } catch (Exception e) {
            System.out.println("Some thing went wrong");
        }
    }
}
