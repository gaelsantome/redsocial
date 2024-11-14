import java.util.Date;

public class Comentarios {
    private String texto;
    private Date fecha;
    private Usuario propietario;

    public Comentarios(String texto, Usuario propietario) {
        this.texto = texto;
        this.fecha = new Date();  // Fecha actual
        this.propietario = propietario;
    }

    public String getTexto() {
        return texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    @Override
    public String toString() {
        return "Comentario de " + propietario.getNombre() + ": " + texto + " (" + fecha + ")";
    }
}
