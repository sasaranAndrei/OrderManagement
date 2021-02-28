package business_logic;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import connection.MyConnection;
import models.Client;
import models.Order;
import models.Product;
import validators.ClientValidator;
import validators.OrderValidator;
import validators.ProductValidator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;

public class Commands {

    public static int clientReportCounter = 0;
    public static int productReportCounter = 0;
    public static int orderReportCounter = 0;
    public static int underStockCounter = 0;
    public static int billCounter = 0;

    // client
    public static void insertClient (String arguments){

        ClientValidator clientValidator = new ClientValidator(arguments);
        if (clientValidator.isValid == true){
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String insertClientString = "{call insertClient(?,?)}";
                CallableStatement callableStatement = connection.prepareCall(insertClientString);
                callableStatement.setString(1, clientValidator.name);
                callableStatement.setString(2, clientValidator.address);
                callableStatement.execute();

                myConnection.close(callableStatement);
                myConnection.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //List<String> myList = new ArrayList<String>(10);
    }

    public static void deleteClient (String arguments){
        ClientValidator clientValidator = new ClientValidator(arguments);
        if (clientValidator.isValid == true){
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String searchClient = "select client.idClient from client where client.name = ?";
                PreparedStatement clientStatement = connection.prepareStatement(searchClient);
                clientStatement.setString(1, clientValidator.name);
                ResultSet clientResultSet = clientStatement.executeQuery();
                if (clientResultSet.next()){ // client exists so we delete him
                    int clientId = clientResultSet.getInt("idClient");
                    String deleteClientString = "{call deleteClient(?)}";
                    CallableStatement callableStatement = connection.prepareCall(deleteClientString);
                    callableStatement.setInt(1, clientId);
                    callableStatement.execute();

                    myConnection.close(callableStatement);
                    myConnection.close(connection);
                }
            }
            catch (Exception exception){
                System.out.println("DELETE CLIENT");
            }
        }
    }

    public static void reportClient (){
        MyConnection myConnection = new MyConnection();
        Connection connection = MyConnection.getConnection();

        try {
            String reportClient = "select * from client";
            PreparedStatement preparedStatement = connection.prepareStatement(reportClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            clientReportCounter++;
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("reportClient" + "No" + clientReportCounter + ".pdf"));
            document.open();
            document.add(new Paragraph("idClient     name               address"));
            while (resultSet.next()){
                Client client = new Client(resultSet.getInt("idClient"), resultSet.getString("name"), resultSet.getString("address"));
                Paragraph paragraph = new Paragraph(client.toString());
                document.add(paragraph);
                document.add(new Paragraph(" "));
            }
            document.close();
            //writer.close();


            myConnection.close(preparedStatement);
            myConnection.close(resultSet);
            myConnection.close(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    // product
    public static void insertProduct (String arguments){

        ProductValidator productValidator = new ProductValidator(arguments);

        if (productValidator.isValid == true){
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String searchProduct = "select product.idProduct from product where product.name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(searchProduct);
                preparedStatement.setString(1, productValidator.name);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){ // if that product already exist in the db
                    int productId = resultSet.getInt("idProduct");
                    String updateProductString = "{call updateProduct(?,?,?,?)}";
                    CallableStatement callableStatement = connection.prepareCall(updateProductString);
                    callableStatement.setInt(1, productId);
                    callableStatement.setString(2, productValidator.name);
                    callableStatement.setDouble(3, productValidator.quantity);
                    callableStatement.setDouble(4, productValidator.price);
                    callableStatement.execute();
                    myConnection.close(callableStatement);
                }
                else { // if that product isn t in the db

                    String insertProductString = "{call insertProduct(?,?,?)}";
                    CallableStatement callableStatement = connection.prepareCall(insertProductString);
                    callableStatement.setString(1, productValidator.name);
                    callableStatement.setDouble(2, productValidator.quantity);
                    callableStatement.setDouble(3, productValidator.price);
                    callableStatement.execute();
                    myConnection.close(callableStatement);
                }
                myConnection.close(preparedStatement);
                myConnection.close(resultSet);
                myConnection.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteProduct (String arguments){
        ProductValidator productValidator = new ProductValidator(arguments);

        if (productValidator.isValid == true){

            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String searchProduct = "select product.idProduct from product where product.name = ?";
                PreparedStatement productStatement = connection.prepareStatement(searchProduct);
                productStatement.setString(1, productValidator.name);
                ResultSet productResultSet = productStatement.executeQuery();
                if (productResultSet.next()){ // client exists so we delete him
                    int productId = productResultSet.getInt("idProduct");
                    String deleteProductString = "{call deleteProduct(?)}";
                    CallableStatement callableStatement = connection.prepareCall(deleteProductString);
                    callableStatement.setInt(1, productId);
                    callableStatement.execute();

                    myConnection.close(callableStatement);
                    myConnection.close(connection);
                }
            }
            catch (Exception exception){
                System.out.println("DELETE PRODUCT");
            }
        }
    }

    public static void reportProduct (){

        try {
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();

            String reportProduct = "select * from product";
            PreparedStatement preparedStatement = connection.prepareStatement(reportProduct);
            ResultSet resultSet = preparedStatement.executeQuery();
            productReportCounter++;
            String documentName = "reportProduct" + "No" + productReportCounter + ".pdf";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
            document.open();
            document.add(new Paragraph("idProduct  name     quantity    price"));
            while (resultSet.next()){
                Product product = new Product(resultSet.getInt("idProduct"), resultSet.getString("name"), resultSet.getDouble("quantity"), resultSet.getDouble("price"));
                Paragraph paragraph = new Paragraph(product.toString());
                document.add(paragraph);
                document.add(new Paragraph(" "));
            }
            document.close();
            //writer.close();


            myConnection.close(preparedStatement);
            myConnection.close(resultSet);
            myConnection.close(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    // order
    public static void order (String arguments){
        OrderValidator orderValidator = new OrderValidator(arguments);

        if (orderValidator.isValid == true){
            MyConnection myConnection = new MyConnection();
            Connection connection = MyConnection.getConnection();
            try {
                String searchProduct = "select product.idProduct from product where product.name = ?";
                PreparedStatement productStatement = connection.prepareStatement(searchProduct);
                productStatement.setString(1, orderValidator.productName);
                ResultSet productResultSet = productStatement.executeQuery();

                String searchClient = "select client.idClient from client where client.name = ?";
                PreparedStatement clientStatement = connection.prepareStatement(searchClient);
                clientStatement.setString(1, orderValidator.clientName);
                ResultSet clientResultSet = clientStatement.executeQuery();

                if (clientResultSet.next() && productResultSet.next()){ // if the product and the client exists, the order is valid
                    int clientId = clientResultSet.getInt("idClient");
                    int productId = productResultSet.getInt("idProduct");

                    String requestQuantity = "select product.quantity, product.price from product where product.idProduct = ?";
                    PreparedStatement requestStatement = connection.prepareStatement(requestQuantity);
                    requestStatement.setInt(1, productId);
                    ResultSet requestResultSet = requestStatement.executeQuery();

                    if (requestResultSet.next()){
                        double actualQuantity = requestResultSet.getDouble("quantity");
                        double productPrice = requestResultSet.getDouble("price");

                        if (orderValidator.quantity > actualQuantity) { // can t finish that order
                            //System.out.println("ERROR. INSUFFICIENT STOCK of that product");
                            // generate pdf
                            try {
                                underStockCounter++;
                                String documentName = "INVALID_ORDER_UNDER-STOCK_No" + underStockCounter + ".pdf";
                                Document document = new Document();
                                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
                                document.open();
                                String orderString = orderValidator.clientName + ", " + orderValidator.productName + ", " + orderValidator.quantity;
                                orderString = "For this order : " + orderString;
                                orderString += "     there are not enough products.";
                                document.add(new Paragraph(orderString));
                                document.add(new Paragraph(" "));

                                //writer.close();
                                document.close();
                            }
                            catch (Exception e){
                                System.out.println("ERROR BILL");
                            }
                        }
                        else {
                            String insertOrderString = "{call insertOrder(?,?,?)}";
                            CallableStatement callableStatement = connection.prepareCall(insertOrderString);
                            callableStatement.setDouble(1, orderValidator.quantity);
                            callableStatement.setInt(2, clientId);
                            callableStatement.setInt(3, productId);
                            callableStatement.execute();

                            String searchOrderId = "select `order`.idOrder from `order` inner join client on (`order`.client_idClient = ?) inner join product on (`order`.product_idProduct = ?)";
                            PreparedStatement orderStatement = connection.prepareStatement(searchOrderId);
                            orderStatement.setInt(1, clientId);
                            orderStatement.setInt(2, productId);
                            ResultSet orderResultSet = orderStatement.executeQuery();

                            if (orderResultSet.next()){
                                int orderId = orderResultSet.getInt("idOrder");

                                double totalPrice = orderValidator.quantity * productPrice;
                                // update total price of order
                                String setOrderTotalPriceString = "{call setOrderTotalPrize(?,?)}";
                                CallableStatement totalPriceStatement = connection.prepareCall(setOrderTotalPriceString);
                                totalPriceStatement.setInt(1, orderId);
                                totalPriceStatement.setDouble(2, totalPrice);
                                totalPriceStatement.execute();
                                // update product stock
                                String updateProductString = "{call updateProduct(?,?,?,?)}";
                                CallableStatement updateProductStatement = connection.prepareCall(updateProductString);
                                updateProductStatement.setInt(1, productId);
                                updateProductStatement.setString(2, orderValidator.productName);
                                updateProductStatement.setDouble(3, actualQuantity - orderValidator.quantity); // update quantity
                                updateProductStatement.setDouble(4, productPrice);
                                updateProductStatement.execute();
                                // generate bill
                                // pdf stuff
                                try {
                                    billCounter++;
                                    String documentName = "bill" + "No" + billCounter + ".pdf";
                                    Document document = new Document();
                                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
                                    document.open();
                                    document.add(new Paragraph("idOrder  clientName    productName    quantity  price"));
                                    String orderString = "   " + orderId + "     " +  orderValidator.clientName + "        " + orderValidator.productName +  "              " + orderValidator.quantity + "        " + totalPrice ;
                                    document.add(new Paragraph(orderString));
                                    document.add(new Paragraph(" "));

                                    //writer.close();
                                    document.close();
                                }
                                catch (FileNotFoundException e){
                                    System.out.println("ERROR BILL");
                                }
                                catch (DocumentException e){
                                    System.out.println("ERROR BILL");
                                }
                                MyConnection.close(totalPriceStatement);
                                MyConnection.close(updateProductStatement);

                            }

                            MyConnection.close(callableStatement);
                            MyConnection.close(orderStatement);
                            MyConnection.close(orderResultSet);

                        }
                    }
                    else {
                        System.out.println("ERROR IN DB");
                    }
                    MyConnection.close(requestStatement);
                    MyConnection.close(requestResultSet);

                }
                MyConnection.close(productStatement);
                MyConnection.close(productResultSet);
                MyConnection.close(clientStatement);
                MyConnection.close(clientResultSet);
                myConnection.close(connection);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reportOrder (){
        MyConnection myConnection = new MyConnection();
        Connection connection = MyConnection.getConnection();

        try {
            String reportOrder = "select * from `order`";
            PreparedStatement preparedStatement = connection.prepareStatement(reportOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            orderReportCounter++;
            String documentName = "reportOrder" + "No" + orderReportCounter + ".pdf";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
            document.open();
            document.add(new Paragraph("idOrder   idClient    idProduct   quantity    totalPrice"));
            while (resultSet.next()){
                Order order = new Order(resultSet.getInt("idOrder"), resultSet.getInt("client_idClient"), resultSet.getInt("product_idProduct"), resultSet.getDouble("quantity"), resultSet.getDouble("totalPrice"));
                Paragraph paragraph = new Paragraph(order.toString());
                document.add(paragraph);
                document.add(new Paragraph(" "));
            }

            myConnection.close(preparedStatement);
            myConnection.close(resultSet);
            myConnection.close(connection);

            document.close();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
