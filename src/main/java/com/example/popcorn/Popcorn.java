package com.example.popcorn;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;



public class Popcorn extends Application {
    private static final String[] TICKET_OPTIONS = {
            "Beanie", "Classic", "Deluxe", "Family-Friendly",
            "Flexound", "IMAX", "Indulge", "Infinity",
            "Junior", "Onyx"
    };

    private static final double[] TICKET_PRICES = {
            19.90, 15.90, 23.90, 23.90,
            25.90, 25.90, 120.00,
            120.00, 15.9, 89.90
    };

    private static final double[] FOOD_PRICES = {
            19.90,17.90,21.90
    };

    private TextField movieTextField;
    private ComboBox<String> ticketComboBox;
    private TextField seatTextField;
    private Label priceLabel;
    private RadioButton fnbprice1;
    private RadioButton fnbprice2;
    private RadioButton fnbprice3;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Movie Ticketing System");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        Label movieLabel = new Label("Movie :");
        gridPane.add(movieLabel, 0, 0);

        movieTextField = new TextField();
        gridPane.add(movieTextField, 1, 0);

        Label ticketLabel = new Label("Experience :");
        gridPane.add(ticketLabel, 0, 1);

        ticketComboBox = new ComboBox<>(FXCollections.observableArrayList(TICKET_OPTIONS));
        gridPane.add(ticketComboBox, 1, 1);


        Label sessionLabel = new Label("Sesion :");
        gridPane.add(sessionLabel, 0, 2);

        VBox showtimeVBox = new VBox(5);
        ToggleGroup showtimeToggleGroup = new ToggleGroup();
        RadioButton session1 = new RadioButton("11:00 AM");
        RadioButton session2 = new RadioButton("01:30 PM");
        RadioButton session3 = new RadioButton("04:00 PM");
        RadioButton session4 = new RadioButton("06:30 PM");
        RadioButton session5 = new RadioButton("09:00 PM");


        session1.setToggleGroup(showtimeToggleGroup);
        session2.setToggleGroup(showtimeToggleGroup);
        session3.setToggleGroup(showtimeToggleGroup);
        session4.setToggleGroup(showtimeToggleGroup);
        session5.setToggleGroup(showtimeToggleGroup);

        showtimeVBox.getChildren().addAll(session1, session2, session3, session4, session5);
        gridPane.add(showtimeVBox, 1, 2);


        Label seatLabel = new Label("Seats :");
        gridPane.add(seatLabel, 0, 3);

        seatTextField = new TextField();
        gridPane.add(seatTextField,1,3);

        Label inform1 = new Label("Enter seat(s) using A-I for Row and 1-9 for Seat format. (For example, F6, F7)");
        gridPane.add(inform1,1,4);

        Label inform2 = new Label("* If the input format is wrong, the purchased tickets will not be counted.");
        gridPane.add(inform2,1,5);


        Label fnb = new Label("Food & Beverages :");
        gridPane.add(fnb, 0, 8);

        HBox imageHBox = new HBox(3);
        ImageView imagepopcorn1 = new ImageView(new Image(Popcorn.class.getResource("popcorn1.png").toString()));
        imagepopcorn1.setFitWidth(300);
        imagepopcorn1.setPreserveRatio(true);

        ImageView imagepopcorn2 = new ImageView(new Image(Popcorn.class.getResource("popcorn2.png").toString()));
        imagepopcorn2.setFitWidth(300);
        imagepopcorn2.setPreserveRatio(true);

        ImageView imagepopcorn3 = new ImageView(new Image(Popcorn.class.getResource("popcorn3.png").toString()));
        imagepopcorn3.setFitWidth(300);
        imagepopcorn3.setPreserveRatio(true);

        Label fnbLabel1 = new Label("Royal Popcorn Combo – Member Special");
        Label fnbLabel2 = new Label("Royal Popcorn ");
        Label fnbLabel3 = new Label("Royal Popcorn Combo ");

        ToggleGroup fnbpriceToggleGroup = new ToggleGroup();
        fnbprice1 = new RadioButton("RM 19.90");
        fnbprice2 = new RadioButton("RM 17.90");
        fnbprice3 = new RadioButton("RM 21.90");

        fnbprice1.setToggleGroup(fnbpriceToggleGroup);
        fnbprice2.setToggleGroup(fnbpriceToggleGroup);
        fnbprice3.setToggleGroup(fnbpriceToggleGroup);

        VBox box1 = new VBox(imagepopcorn1, fnbLabel1, fnbprice1);
        VBox box2 = new VBox(imagepopcorn2, fnbLabel2, fnbprice2);
        VBox box3 = new VBox(imagepopcorn3, fnbLabel3, fnbprice3);

        imageHBox.getChildren().addAll(box1, box2, box3);
        gridPane.add(imageHBox, 1, 8);


        Button submitButton = new Button("Submit");
        Alert a = new Alert(AlertType.NONE);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try{
                    RadioButton rb1= (RadioButton) showtimeToggleGroup.getSelectedToggle();
                    RadioButton rb2= (RadioButton) fnbpriceToggleGroup.getSelectedToggle();
                    a.setAlertType(AlertType.CONFIRMATION);
                    a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    a.setContentText("You select "+movieTextField.getText()+" with "+ticketComboBox.getValue()+" experience at "+
                            rb1.getText()+" for "+ String.format("%.0f", numberOfTicket()) +" ticket(s) and a "+
                            getIndexFood(rb2.getText())+". The total is RM"+String.format("%.2f", totalPrice()));
                    a.show();
                } catch (ArithmeticException ex) {

                    System.out.println(ex.getMessage());
                } catch (NullPointerException ex) {
                    Alert alertNull = new Alert(AlertType.ERROR);
                    alertNull.setContentText("Please fill in properly");
                    alertNull.show();
                    System.out.println("Null Pointer Exception is handled: "+ex.getMessage());
                } catch (IndexOutOfBoundsException ex) {
                    Alert alertNull = new Alert(AlertType.ERROR);
                    alertNull.setContentText("Please fill in properly");
                    alertNull.show();
                    System.out.println("Index Out Of Bounds Exception is handled: "+ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
        submitButton.setOnAction(event);
        gridPane.add(submitButton, 1, 9);




        priceLabel = new Label();

//////////////////////////////////////////////////////////////////////////


        Image backgroundImage = new Image(Popcorn.class.getResource("cinema.png").toString());

        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        gridPane.setBackground(new Background(background));


        Scene scene = new Scene(gridPane, 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public double totalPrice(){
            String seatType = ticketComboBox.getValue();
            System.out.println(seatType);
            System.out.println("Ticket = "+ numberOfTicket());
            int index = getIndex(seatType);
            double price = TICKET_PRICES[index];
            System.out.println("Price = "+ price*numberOfTicket());

            double foodPrice=0;
            if(fnbprice1.isSelected()){
                foodPrice = 19.9;
            }
            else if(fnbprice2.isSelected()){
                foodPrice = 17.9;
            }
            else if(fnbprice3.isSelected()){
                foodPrice = 21.9;
            }

            System.out.println("Price = "+ price*numberOfTicket());
            double totalPrice = price * numberOfTicket() + foodPrice;
            priceLabel.setText(String.format("%.2f", totalPrice));
            return totalPrice;

    }


    public double numberOfTicket() {
            int ticket = 0;
            String selectedOption = seatTextField.getText();
            System.out.println("SO = " + selectedOption);
            String[] arraySeat = selectedOption.split(",", 0);
            char[] ROW={'A','B','C','D','E','F','G','H','I'};
            for (String a : arraySeat){
                System.out.println(a);
                for(char c : ROW){
                    if(a.charAt(0)==c&&Integer.parseInt(String.valueOf(a.charAt(1)))<10&&Integer.parseInt(String.valueOf(a.charAt(1)))>0){
                        System.out.println("Correct input");
                        ticket = ticket + 1;
                    }
                }
            }
            return ticket;
    }


    private int getIndex(String option) {
        for (int i = 0; i < TICKET_OPTIONS.length; i++) {
            if (TICKET_OPTIONS[i].equals(option)) {
                return i;
            }
        }
        return -1;
    }
    private String getIndexFood(String option) {
        for (int i = 0; i < FOOD_PRICES.length; i++) {
            if(FOOD_PRICES[i]==19.9)
                return "Royal Popcorn Combo – Member Special";
            else if(FOOD_PRICES[i]==17.9){
                return "Royal Popcorn";
            } else if (FOOD_PRICES[i]==21.9){
                return"Royal Popcorn Combo";
            }
        }
        return "";
    }

    public static void main(String[] args) {
        launch(args);
    }
}

