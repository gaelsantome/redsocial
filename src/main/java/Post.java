import java.util.ArrayList;
import java.util.List;

public class Post {
    private Usuario propietario;
    private String tipoContenido; // tipo de contenido: texto, imagen, video
    private String contenido; // el texto o el contenido del post
    private int ancho; // ancho de la imagen/video (en píxeles)
    private int alto;  // alto de la imagen/video (en píxeles)
    private List<Comentarios> comentarios;

    // Constructor para Post con solo texto
    public Post(Usuario propietario, String tipoContenido, String contenido) {
        this.propietario = propietario;
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
        this.ancho = -1;  // Si no es imagen o video, no tiene dimensiones
        this.alto = -1;   // Si no es imagen o video, no tiene dimensiones
        this.comentarios = new ArrayList<>();
    }

    // Constructor para Post con imagen o video
    public Post(Usuario propietario, String tipoContenido, String contenido, int ancho, int alto) {
        this.propietario = propietario;
        this.tipoContenido = tipoContenido;
        this.contenido = contenido;
        this.ancho = ancho;
        this.alto = alto;
        this.comentarios = new ArrayList<>();
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

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public List<Comentarios> getComentarios() {
        return comentarios;
    }

    // Método para agregar comentarios al post
    public void agregarComentario(Comentarios comentario) {
        comentarios.add(comentario);
    }

    // Método para eliminar un comentario del post
    public void eliminarComentario(Comentarios comentario) {
        comentarios.remove(comentario);
    }

    // Método para obtener el número de comentarios del post
    public int obtenerNumeroComentarios() {
        return comentarios.size();
    }

    @Override
    public String toString() {
        if (tipoContenido.equalsIgnoreCase("imagen") || tipoContenido.equalsIgnoreCase("video")) {
            return "Post de " + propietario.getNombre() + " (" + tipoContenido + "): " + contenido + " - " +
                    "Dimensiones: " + ancho + "x" + alto + " píxeles";
        } else {
            return "Post de " + propietario.getNombre() + " (" + tipoContenido + "): " + contenido;
        }
    }
}