package com.mycompany.practica_obligatoria_mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import connectionDB.ConnectionDB;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

public  class PrimaryController implements Initializable{
    MongoClient con;
    
    @FXML
    private Label aviso;
    
    @FXML
    private TextField dni;

    @FXML
    private TextField nombre;

    @FXML
    private TextField equipo;
    
    @FXML 
    private ComboBox<String> tipo;
    private ArrayList<String> tipos = new ArrayList<>();
    
    @FXML
    private TableView<persona> tabla;
    
    @FXML 
    private TableColumn<persona, String> columnDni;
    
    @FXML 
    private TableColumn<persona, String> columnNombre;
    
    @FXML 
    private TableColumn<persona, String> columnEquipo;
    
    @FXML 
    private TableColumn<persona, String> columnTipo;
    
    public static void fillTipos(ArrayList<String> tipos) {
        tipos.add("Jugador");
        tipos.add("Entrenador");
    }
    
    
     public ObservableList<persona> getPersona() {
        ObservableList<persona> listaTabla = FXCollections.observableArrayList();
        con = ConnectionDB.conectar();
        MongoDatabase database = con.getDatabase("PrácticaMongo");   
        
        MongoCollection coll = database.getCollection("Futbol");
        MongoCursor<Document> data = coll.find().iterator();
        
        while (data.hasNext()) {
            Document p1 = data.next();
            listaTabla.add(new persona(p1.getString("dni"), p1.getString("nombre"), p1.getString("equipo"), p1.getString("tipo")));
            
        }
        return listaTabla;
    }
     
     @FXML
    private void llenarTabla() {
        columnDni.setCellValueFactory(new PropertyValueFactory<persona, String>("dni"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<persona, String>("nombre"));
        columnEquipo.setCellValueFactory(new PropertyValueFactory<persona, String>("equipo"));
        columnTipo.setCellValueFactory(new PropertyValueFactory<persona, String>("tipo"));
        
        tabla.setItems(getPersona());
    }

    String a;
    
    @FXML
    public void nuevo(ActionEvent Event) {
        aviso.setVisible(false);
        con = ConnectionDB.conectar();
        MongoDatabase database = con.getDatabase("PrácticaMongo");
        MongoCollection<Document> collection = database.getCollection("Futbol");
        
        try {
            Document a = new Document();
                    a.append("dni", dni.getText())
                    .append("nombre", nombre.getText())
                    .append("equipo", equipo.getText())
                    .append("tipo", tipo.getValue());
            collection.insertOne(a);
            llenarTabla();
           
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
    
  

    @FXML
    public void modificar(ActionEvent Event) {
        if (dni.equals("")) {
            aviso.setVisible(true);
        } else {
            aviso.setVisible(false);
        }
         con = ConnectionDB.conectar();
        MongoDatabase database = con.getDatabase("PrácticaMongo");
        MongoCollection<Document> collection = database.getCollection("Futbol");
        
        try {
            Document third = collection.find(Filters.eq("dni", dni.getText())).first();

            collection.updateOne(new Document("dni", dni.getText()),
                    new Document("$set", new Document("nombre", nombre.getText())
                            .append("equipo", equipo.getText())  
                            .append("tipo", tipo.getValue()))              
            );
            llenarTabla();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @FXML
    public void eliminar(ActionEvent Event) {
        aviso.setVisible(false);
        con = ConnectionDB.conectar();
        MongoDatabase database = con.getDatabase("PrácticaMongo");
        MongoCollection<Document> collection = database.getCollection("Futbol");
        
        try {
            collection.deleteMany(Filters.gte("dni", dni.getText()));
            llenarTabla();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @FXML
    public void guardar(ActionEvent Event) {
           aviso.setVisible(false);
    }

    @FXML
    public void cancelar(ActionEvent Event) {
           aviso.setVisible(false);
           dni.setText("");
           nombre.setText("");
           equipo.setText("");
           tipo.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTipos(tipos);
        for (int i = 0; i<tipos.size();i++) {
            tipo.getItems().add(tipos.get(i));
        }
        llenarTabla();
        
    }
    
   
     
}
