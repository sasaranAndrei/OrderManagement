package validators;

import models.Product;

public class Validation {

    public TaskValidator taskValidator;
    public int validationCode = NO_OPERATION_CODE;

    public static final int NO_OPERATION_CODE = -1;

    public static final int INSERT_CLIENT_CODE = 0;
    public static final int INSERT_PRODUCT_CODE = 1;
    public static final int DELETE_CLIENT_CODE = 2;
    public static final int DELETE_PRODUCT_CODE = 3;
    public static final int ORDER_CODE = 4;
    public static final int REPORT_CLIENT_CODE = 5;
    public static final int REPORT_PRODUCT_CODE = 6;
    public static final int REPORT_ORDER_CODE = 7;

    public Validation(String command) {
        taskValidator = new TaskValidator(command);
        String arguments = taskValidator.arguments;
        //System.out.println(command + " " + taskValidator.isValid);
        //System.out.println(taskValidator.entity + " " + taskValidator.task + " " + taskValidator.arguments + "!");

        if (taskValidator.isValid){
            if (taskValidator.entity.equals("client")){
                if (taskValidator.task.equals("Report")){ // reportClient
                    validationCode = REPORT_CLIENT_CODE;
                }
                else {
                    ClientValidator clientValidator = new ClientValidator(arguments);
                    if (clientValidator.isValid == true){
                        if (taskValidator.task.equals("Insert")){ // insertClient
                            validationCode = INSERT_CLIENT_CODE;
                        }
                        if (taskValidator.task.equals("Delete")){ // deleteClient
                            validationCode = DELETE_CLIENT_CODE;
                        }
                    }
                    else {
                        System.out.println("Invalid client input");
                        validationCode =  NO_OPERATION_CODE;
                    }
                }
            }
            if (taskValidator.entity.equals("product")){
                if (taskValidator.task.equals("Report")){ // reportProduct
                    validationCode = REPORT_PRODUCT_CODE;
                }
                else {
                    if (taskValidator.task.equals("Delete")){ // deleteProduct
                        taskValidator.arguments += ",";
                        validationCode = DELETE_PRODUCT_CODE;
                    }
                    else if (taskValidator.task.equals("Insert")){
                        ProductValidator productValidator = new ProductValidator(arguments);
                        if (productValidator.isValid == true){ // insertProduct
                            validationCode = INSERT_PRODUCT_CODE;
                        }
                    }
                    else {
                        System.out.println("Invalid product input");
                        validationCode =  NO_OPERATION_CODE;
                    }
                }
            }
            if (taskValidator.entity.equals("order")){
                if (taskValidator.task.equals("Report")){ // reportOrder
                    validationCode = REPORT_ORDER_CODE;
                }
                else {
                    System.out.println("Invalid order input");
                    validationCode =  NO_OPERATION_CODE;
                }
            }
            if (taskValidator.entity.equals("orderInsert")){
                OrderValidator orderValidator = new OrderValidator(arguments);
                if (orderValidator.isValid == true){
                    validationCode = ORDER_CODE;
                }
                else {
                    System.out.println("Invalid order input");
                    validationCode =  NO_OPERATION_CODE;
                }
            }
        }
    }
}
