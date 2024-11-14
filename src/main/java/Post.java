import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private Date fecha;
    private List<Comentarios> comentarios;
    private Usuario propietario;

    // Tipos de contenido para el post
    private String tipoContenido;
    private String contenido;

    public Post(Usuario propietario, String tipoContenido, String contenido) {
        this.fecha = new Date();
        this.comentarios = new ArrayList<>();
        this.propietario = propietario;
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void agregarComentario(Comentarios comentario) {
        comentarios.add(comentario);
    }

    public void eliminarComentario(Comentarios comentario) {
        comentarios.remove(comentario);
    }

    public int obtenerNumeroComentarios() {
        return comentarios.size();
    }

    public List<Comentarios> getComentarios() {
        return comentarios;
    }

    @Override
    public String toString() {
        return "Post de " + propietario.getNombre() + " (" + tipoContenido + "): " + contenido + " (" + fecha + ")";
    }
}