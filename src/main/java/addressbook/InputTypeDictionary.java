package addressbook;

import java.util.Map;

import static addressbook.AddressBookMain.cityDictionary;
import static addressbook.AddressBookMain.stateDictionary;

public enum InputTypeDictionary {
    ENTERED_CITY {
        @Override
        public Map<String, String> stateOrCityDictionary() {
            return cityDictionary;
        }
    },
    ENTERED_STATE {
        @Override
        public Map<String, String> stateOrCityDictionary() {
            return stateDictionary;
        }
    };
    public abstract Map<String, String> stateOrCityDictionary();
}
