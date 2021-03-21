package addressbook;

public class AlreadyExistContactException extends Exception{
    public AlreadyExistContactException(String message) {
        super(message);
    }
}
