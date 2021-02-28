package validators;

public class ClientValidator {
    public boolean isValid;

    public String name;
    public String address;

    public ClientValidator(String inputString) {
        int index = inputString.indexOf(",");
        if (index == -1){
            isValid = false;
        }
        else {
            isValid = true;
            name = inputString.substring(0, index);
            address = inputString.substring(index + 2);
        }
    }
}
