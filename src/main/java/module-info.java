module ubb.scs.map {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.jgrapht.core;


    opens ubb.scs.map to javafx.fxml;
    exports ubb.scs.map to javafx.graphics;

}