package ejercicio.login.persistencia;

import ejercicio.login.logica.Usuario;

import java.util.List;

public class ControladoraPersistencia {

    UsuarioJPAController usuarioJpa = new UsuarioJPAController();


    public void guardarUsuario(Usuario usuario) {
        usuarioJpa.create(usuario);

    }

    public Usuario buscarUsuario(long id) {
        return usuarioJpa.find(id);
    }

    public void editarUsuario(Usuario usuario) {
        try {
            usuarioJpa.edit(usuario);

        } catch (Exception e) {
            System.out.println("Error al editar en la persistencia: " + e.getMessage());
        }
    }

    public List<Usuario> traerUsuarios() {
        return usuarioJpa.findEntities();
    }

    public void borraUsuario(long id) {
        try {
            Usuario usuario = usuarioJpa.find(id);
            if (usuario != null) {
                usuarioJpa.destroy(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar en la persistencia: " + e.getMessage());
        }
    }

}
