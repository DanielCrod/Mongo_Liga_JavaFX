module com.mycompany.practica_obligatoria_mongo {
    
    
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;
            
    opens com.mycompany.practica_obligatoria_mongo to javafx.fxml;
    exports com.mycompany.practica_obligatoria_mongo;
}
