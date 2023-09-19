module com.example.serverclienthicaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.serverclienthicaz.Server;
    opens com.example.serverclienthicaz.Server.ModelProses;
    opens com.example.serverclienthicaz.Server.ModelAnbar;
    opens com.example.serverclienthicaz.Server.ProsesAnbarGUI;
    opens com.example.serverclienthicaz.Server.AnbarGUI;
    opens com.example.serverclienthicaz.Server.AdminGUI;

    exports com.example.serverclienthicaz.Server;
    exports com.example.serverclienthicaz.Server.ProsesAnbarGUI;
    exports com.example.serverclienthicaz.Server.ModelProses;
    exports com.example.serverclienthicaz.Server.AnbarGUI;
    exports com.example.serverclienthicaz.Server.AdminGUI;
}