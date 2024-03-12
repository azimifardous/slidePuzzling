module org.seng218.slidepuzzling {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.seng218.slidepuzzling to javafx.fxml;
    exports org.seng218.slidepuzzling;
}