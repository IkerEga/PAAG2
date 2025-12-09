module opendata {
    requires javafx.controls;
    requires javafx.fxml;

    opens opendata to javafx.fxml;
    opens opendata.controller to javafx.fxml;

    exports opendata;
    exports opendata.controller;
}
