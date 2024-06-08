package controllers;

import domain.User;
import domain.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import operations.UserOperations;
import ucr.proyecto.proyectoalgoritmosv1.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class ChangePasswordController {
    @javafx.fxml.FXML
    private TextField txf_confirmPassword;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Circle circleImage;
    @javafx.fxml.FXML
    private TextField txf_newPassword;
    @javafx.fxml.FXML
    private BorderPane bp;

    User loggedUser = UserSession.getInstance().getLoggedUser();
    UserOperations UO = new UserOperations();

    private void loadPage(String page){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void mainPageOnAction(ActionEvent actionEvent) {loadPage("mainPage.fxml");}

    @javafx.fxml.FXML
    public void saveChangesOnAction(ActionEvent actionEvent) {
        if (Objects.equals(txf_newPassword.getText(), loggedUser.getPassword())) {
            util.UtilityFX.alert("Error al cambiar contraseña", "La contraseña nueva no puede ser la misma que la actual.\nInténtelo de nuevo.");
        } else if (!Objects.equals(txf_newPassword.getText(), txf_confirmPassword.getText()))
            util.UtilityFX.alert("Error al cambiar contraseña","La nueva contraseña debe ser la misma en ambos campos para actualizar.\nInténtelo de nuevo.");
         else {
            UO.changePassword(loggedUser.getId(), txf_newPassword.getText());
            UO.updateUser(loggedUser);
            util.UtilityFX.alert("Contraseña cambiada", "La contraseña del usuario '" + loggedUser.getName() + "' ha sido modificada exitosamente.");
        }
    }
}