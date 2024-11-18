module ubb.scs.map {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.jgrapht.core;


    opens ubb.scs.map to javafx.fxml;
    exports ubb.scs.map to javafx.graphics;

    exports ubb.scs.map.Controllers to javafx.graphics;
    opens ubb.scs.map.Controllers to javafx.fxml;

    exports ubb.scs.map.Services to javafx.graphics;
    opens ubb.scs.map.Services to javafx.fxml;

    opens ubb.scs.map.Domain to javafx.graphics;
    exports ubb.scs.map.Domain to javafx.graphics;

    opens ubb.scs.map.Repository to javafx.graphics;
    exports ubb.scs.map.Repository to javafx.graphics;


}