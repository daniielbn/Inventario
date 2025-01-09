module com.example.inventario_hib {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens com.example.inventario_hib to javafx.fxml;
    exports com.example.inventario_hib;
    exports controladores;
    opens controladores to javafx.fxml;
    exports modelo;
    opens modelo to org.hibernate.orm.core;
    exports DAO;
    opens DAO to org.hibernate.orm.core;
    exports controladores.aula;
    opens controladores.aula to javafx.fxml;
    exports controladores.categoria;
    opens controladores.categoria to javafx.fxml;
    exports controladores.marcaje;
    opens controladores.marcaje to javafx.fxml;
    exports controladores.producto;
    opens controladores.producto to javafx.fxml;
    exports controladores.prodCat;
    opens controladores.prodCat to javafx.fxml;
    exports controladores.consultas;
    opens controladores.consultas to javafx.fxml;
}