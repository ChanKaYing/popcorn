module com.example.popcorn {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.popcorn to javafx.fxml;
    exports com.example.popcorn;
}