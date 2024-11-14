import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private List<Usuario> seguidores;
    private List<Usuario> seguidos;
    private List<Post> posts;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.seguidores = new ArrayList<>();
        this.seguidos = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Usuario> getSeguidos() {
        return seguidos;
    }

    public List<Usuario> getSeguidores() {
        return seguidores;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void agregarPost(Post post) {
        posts.add(post);
    }

    public void eliminarPost(Post post) {
        posts.remove(post);
    }

    public void seguirUsuario(Usuario usuario) {
        if (!seguidos.contains(usuario)) {
            seguidos.add(usuario);
            usuario.agregarSeguidor(this);
        }
    }

    public void dejarDeSeguirUsuario(Usuario usuario) {
        if (seguidos.contains(usuario)) {
            seguidos.remove(usuario);
            usuario.eliminarSeguidor(this);
        }
    }

    private void agregarSeguidor(Usuario usuario) {
        if (!seguidores.contains(usuario)) {
            seguidores.add(usuario);
        }
    }

    private void eliminarSeguidor(Usuario usuario) {
        seguidores.remove(usuario);
    }

    public List<Comentarios> listarComentarios() {
        List<Comentarios> comentariosUsuario = new ArrayList<>();
        for (Post post : posts) {
            for (Comentarios comentario : post.getComentarios()) {
                if (comentario.getPropietario().equals(this)) {
                    comentariosUsuario.add(comentario);
                }
            }
        }
        return comentariosUsuario;
    }

    @Override
    public String toString() {
        return "Usuario: " + nombre;
    }
}