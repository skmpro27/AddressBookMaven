package addressbook;

enum InputType {
    ENTERED_CITY {
        @Override
        public String stateOrCity(ContactCSV contact) {
            return contact.getCity();
        }
    },
    ENTERED_STATE {
        @Override
        public String stateOrCity(ContactCSV contact) {
            return contact.getState();
        }
    };
    public abstract String stateOrCity(ContactCSV contact);
}
