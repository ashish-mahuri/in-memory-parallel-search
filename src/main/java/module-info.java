module com.microchip.parallelsearch.inmemoryparallelsearch {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;


    opens com.microchip.parallelsearch to javafx.fxml;
    exports com.microchip.parallelsearch;
    exports com.microchip.parallelsearch.controller;
    opens com.microchip.parallelsearch.controller to javafx.fxml;
}