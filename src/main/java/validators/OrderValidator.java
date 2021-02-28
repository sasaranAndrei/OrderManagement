package validators;

public class OrderValidator {
    public boolean isValid;

    public String clientName;
    public String productName;
    public double quantity;

    public OrderValidator (String inputString){
        int clientIndex = inputString.indexOf(",");
        if (clientIndex == -1){
            isValid = false;
        }
        else {
            clientName = inputString.substring(0, clientIndex);
            inputString = inputString.substring(clientIndex + 2);
            int productIndex = inputString.indexOf(",");
            if (productIndex == -1){
                isValid = false;
            }
            else {
                isValid = true;
                productName = inputString.substring(0, productIndex);
                quantity = Double.parseDouble(inputString.substring(productIndex + 2));
            }
        }
    }

}
