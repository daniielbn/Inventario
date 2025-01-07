package controladores;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ButtonLimpiarController {
    @FXML
    private Button buttonLimpiar;

    // Propiedad para asignar un evento personalizado al botón
    private EventHandler<ActionEvent> onAction;

    public void setOnAction(EventHandler<ActionEvent> onAction) {
        this.onAction = onAction;
    }

    public void limpiar(ActionEvent actionEvent) {
        if (onAction != null) {
            onAction.handle(actionEvent); // Ejecuta el evento personalizado
        }
    }
}
