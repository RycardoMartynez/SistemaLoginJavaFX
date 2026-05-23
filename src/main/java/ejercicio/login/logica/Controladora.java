package ejercicio.login.logica;

import ejercicio.login.persistencia.ControladoraPersistencia;

import java.util.List;

public class Controladora {

    private ControladoraPersistencia controlPersis;

    public Controladora() {
        this.controlPersis = new ControladoraPersistencia();
    }
    public void eliminarUsuario(long id) {
        controlPersis.borraUsuario(id);
    }

    public void editarUsuario(Usuario usuario) {
        controlPersis.editarUsuario(usuario);
    }
        public void guardar (String nombreUsuario, String contrasenia){

            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContrasenia(contrasenia);
            controlPersis.guardarUsuario(usuario);

        }

        public List<Usuario> traerUsuarios () {
            return controlPersis.traerUsuarios();
        }

    public String validarUsuario(String nombreUsuario, String contrasenia) {
        List<Usuario> usuarios = controlPersis.traerUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                if (usuario.getContrasenia().equals(contrasenia)) {
                    return usuario.getRol();
                } else {
                    return "INVALID_PASSWORD";
                }
            }
        }
        return "USER_NOT_FOUND";
    }
}




