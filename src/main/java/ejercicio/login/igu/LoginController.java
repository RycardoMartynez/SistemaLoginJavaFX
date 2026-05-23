package ejercicio.login.igu;

import ejercicio.login.logica.Controladora;
import ejercicio.login.logica.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private TextArea txtResultado;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private Button btnBorrar;
    @FXML private Button btnEditar;
    @FXML private Button btnLogin;
    @FXML private Usuario usuarioEdicion = null;
    @FXML private TableColumn<Usuario,String> colIde;
    @FXML private TableColumn<Usuario,String> colUsu;
    @FXML private TableColumn<Usuario,String> colRol;

    Controladora controladora  = new Controladora();

    @FXML
    public void initialize() {
        colIde.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsu.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        deshabilitarTodo();
    }

    @FXML
    private void limpiar(){
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtResultado.setText("");
    }

    @FXML
    private void Login() {
        String usuario = txtUsuario.getText();
        String contrasenia = txtContrasena.getText();

        if (usuarioEdicion != null) {
            usuarioEdicion.setNombreUsuario(usuario);
            usuarioEdicion.setContrasenia(contrasenia);
            controladora.editarUsuario(usuarioEdicion);
            txtResultado.setText("¡Usuario actualizado correctamente!");
            usuarioEdicion = null;
            btnLogin.setText("INGRESAR");
            limpiar();
            cargarTabla();
            return;
        }
        String respuestaLogica = controladora.validarUsuario(usuario, contrasenia);
        switch (respuestaLogica) {
            case "ADMIN":
                habilitarAdmin("ADMIN");
                cargarTabla();
                break;
            case "USER":
                habilitarAdmin("USER");
                cargarTabla();
                break;
            default:
                txtResultado.setText(respuestaLogica);
                deshabilitarTodo();
                break;
        }
    }
    @FXML
    private void cargarTabla() {
        List<Usuario> listaUsuarios = controladora.traerUsuarios();
        ObservableList<Usuario> data = FXCollections.observableArrayList(listaUsuarios);
        tablaUsuarios.setItems(data);
    }
    @FXML
    private void habilitarAdmin(String rolUsuario) {

        if (rolUsuario.equals("ADMIN")) {
            btnBorrar.setDisable(false);
            btnBorrar.setVisible(true);
            btnEditar.setDisable(false);
            btnEditar.setVisible(true);
            txtResultado.setText("Conectado como ADMINISTRADOR. Permisos totales.");

        } else if (rolUsuario.equals("USER")) {
            btnBorrar.setDisable(true);
            btnBorrar.setVisible(false);
            btnEditar.setDisable(true);
            btnEditar.setVisible(false);

            txtResultado.setText("Conectado como USUARIO. Modo lectura activado.");
        } else {
            deshabilitarTodo();
        }
    }

    @FXML
    private void deshabilitarTodo() {
        btnBorrar.setDisable(true);
        btnBorrar.setVisible(false);
        btnEditar.setDisable(true);
        btnEditar.setVisible(false);
    }


    @FXML
    private void AEditar() {
        usuarioEdicion = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioEdicion != null) {
            txtUsuario.setText(usuarioEdicion.getNombreUsuario());
            txtContrasena.setText(usuarioEdicion.getContrasenia());

            btnLogin.setText("GUARDAR CAMBIOS");
            txtResultado.setText("Editando al usuario: " + usuarioEdicion.getNombreUsuario());
        }
    }
    @FXML
    private void borrar() {
        usuarioEdicion = tablaUsuarios.getSelectionModel().getSelectedItem();
        controladora.eliminarUsuario(usuarioEdicion.getId());
        txtResultado.setText("Usuario "+usuarioEdicion.getNombreUsuario()+" Eliminado!");
        cargarTabla();

    }

}
