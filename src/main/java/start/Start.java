package start;

import business_logic.Commands;
import connection.MyConnection;
import models.Client;
import validators.ClientValidator;
import validators.TaskValidator;
import validators.Validation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Start {
    public static void main(String[] args) {
        Stream.generate(() -> "123") .limit(3).forEach(System.out::print);
        System.out.println();
        Stream.iterate(0, i -> i + 1).limit(3).forEach(System.out::print);
        /*
        if (args.length != 1) {
            System.out.println("INVALID NO OF ARUGMENTS. SUPORTED VALUES : 1 - Commands text file.");
        }
        else {
            try {
                FileReader fr = new FileReader(args[0]);
                BufferedReader br = new BufferedReader(fr);
                String command;
                boolean stop = false;

                do {
                    command = br.readLine();
                    if (command == null){
                        stop = true;
                    }
                    else {
                        //System.out.println(command);
                        Validation validation = new Validation(command);
                        //System.out.println("Arg + " + validation.taskValidator.arguments);
                        switch (validation.validationCode){
                            case (Validation.INSERT_CLIENT_CODE) :
                                Commands.insertClient(validation.taskValidator.arguments);
                                break;
                            case (Validation.DELETE_CLIENT_CODE) :
                                Commands.deleteClient(validation.taskValidator.arguments);
                                break;
                            case (Validation.INSERT_PRODUCT_CODE) :
                                Commands.insertProduct(validation.taskValidator.arguments);
                                break;
                            case (Validation.DELETE_PRODUCT_CODE) :
                                Commands.deleteProduct(validation.taskValidator.arguments);
                                break;
                            case (Validation.ORDER_CODE) :
                                Commands.order(validation.taskValidator.arguments);
                                break;
                            case (Validation.REPORT_CLIENT_CODE) :
                                Commands.reportClient();
                                break;
                            case (Validation.REPORT_PRODUCT_CODE) :
                                Commands.reportProduct();
                                break;
                            case (Validation.REPORT_ORDER_CODE) :
                                Commands.reportOrder();
                                break;
                            case (Validation.NO_OPERATION_CODE) :
                                System.out.println("Invalid / No operation");
                        }


                    }

                }
                while (stop == false);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }



/*
        String inputString1 = "Ion Popescu, Bucuresti";
        String inputString2 = "Alex Frer, Baia Mare";
        String inputString3 = "Marius Pope, Sasar";
        String inputString4 = "Vali Pescu, Hunedoara";

        ClientValidator a = new ClientValidator(inputString1);
        if (a.isValid == true){
         //   Client c = new Client()
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String insertClientString = "{call insertClient(?,?)}";
                CallableStatement statement = connection.prepareCall(insertClientString);
                statement.setString(1, a.name);
                statement.setString(2, a.address);
                statement.execute();
                myConnection.close(statement);
                myConnection.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
*/
    }


}
