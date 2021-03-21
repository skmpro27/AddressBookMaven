package addressbook;

import java.util.Comparator;

public enum InputTypeCompare {
    SORT_BY_NAME {
        @Override
        public Comparator<ContactCSV> nameOrAddressCompare() {
            return Comparator.comparing(ContactCSV::getFirstName).thenComparing(ContactCSV::getLastName);
        }
    },
    SORT_BY_ADDRESS {
        @Override
        public Comparator<ContactCSV> nameOrAddressCompare(){
            return Comparator.comparing(ContactCSV::getFirstName).thenComparing(ContactCSV::getLastName);
        }
    };
    public abstract Comparator<ContactCSV> nameOrAddressCompare();
}