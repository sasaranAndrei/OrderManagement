package validators;

public class ProductValidator {
    public boolean isValid;

    public String name;
    public double quantity;
    public double price;

    public ProductValidator(String inputString) {
        int firstIndex = inputString.indexOf(",");
        if (firstIndex == -1){
            isValid = false;
        }
        else {
            isValid = true;
            name = inputString.substring(0, firstIndex);
            if (firstIndex + 2 < inputString.length()){
                inputString = inputString.substring(firstIndex + 2);
                int secondIndex = inputString.indexOf(",");
                // if it s only the time, that means the product must be deleted
                if (secondIndex != -1){
                    quantity = Double.parseDouble(inputString.substring(0, secondIndex));
                    price = Double.parseDouble(inputString.substring(secondIndex + 2));
                }
            }
        }
    }

    @Override
    public String toString() {
        return isValid + " " + name + " " + quantity + " " + price + "\n";
    }
}
