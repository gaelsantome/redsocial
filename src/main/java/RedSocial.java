import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RedSocial {
    private List<Usuario> usuarios;

    public RedSocial() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        for (Post post : usuario.getPosts()) {
            eliminarPost(post);
        }
        // Eliminar los comentarios del usuario en otros posts
        for (Usuario u : usuarios) {
            for (Post post : u.getPosts()) {
                post.getComentarios().removeIf(comentario -> comentario.getPropietario().equals(usuario));
            }
        }
    }

    public void eliminarPost(Post post) {
        post.getPropietario().eliminarPost(post);
    }

    public void eliminarComentario(Post post, Comentarios comentario) {
        post.eliminarComentario(comentario);
    }

    public List<Post> listarPostsDeUsuario(Usuario usuario) {
        return usuario.getPosts();
    }

    public List<Comentarios> listarComentariosDeUsuario(Usuario usuario) {
        return usuario.listarComentarios();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedSocial red = new RedSocial();

        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Añadir Usuario");
            System.out.println("2. Añadir Post");
            System.out.println("3. Añadir Comentario");
            System.out.println("4. Comenzar a Seguir a un Usuario");
            System.out.println("5. Dejar de Seguir a un Usuario");
            System.out.println("6. Eliminar un Usuario");
            System.out.println("7. Listar Todos los Posts de un Usuario");
            System.out.println("8. Listar Todos los Comentarios de un Usuario");
            System.out.println("9. Mostrar el Número de Comentarios de un Post");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Añadir Usuario
                    System.out.print("Introduce el nombre del nuevo usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    Usuario nuevoUsuario = new Usuario(nombreUsuario);
                    red.agregarUsuario(nuevoUsuario);
                    System.out.println("Usuario " + nombreUsuario + " añadido con éxito.");
                    break;

                case 2:
                    // Añadir Post
                    System.out.print("Introduce el nombre del usuario propietario del post: ");
                    String nombreUsuarioPost = scanner.nextLine();
                    Usuario propietarioPost = buscarUsuarioPorNombre(red, nombreUsuarioPost);
                    if (propietarioPost != null) {
                        System.out.print("Introduce el tipo de contenido del post (texto, imagen, video): ");
                        String tipoContenido = scanner.nextLine();
                        System.out.print("Introduce el contenido del post: ");
                        String contenidoPost = scanner.nextLine();
                        Post nuevoPost = new Post(propietarioPost, tipoContenido, contenidoPost);
                        propietarioPost.agregarPost(nuevoPost);
                        System.out.println("Post añadido con éxito.");
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 3:
                    // Añadir Comentario
                    System.out.print("Introduce el nombre del usuario que comenta: ");
                    String nombreUsuarioComentario = scanner.nextLine();
                    Usuario propietarioComentario = buscarUsuarioPorNombre(red, nombreUsuarioComentario);
                    if (propietarioComentario != null) {
                        System.out.print("Introduce el nombre del usuario propietario del post: ");
                        String nombrePostComentario = scanner.nextLine();
                        Usuario propietarioPostComentario = buscarUsuarioPorNombre(red, nombrePostComentario);
                        if (propietarioPostComentario != null) {
                            System.out.print("Introduce el texto del comentario: ");
                            String textoComentario = scanner.nextLine();
                            Post postComentario = null;
                            for (Post post : propietarioPostComentario.getPosts()) {
                                if (post.getPropietario().getNombre().equals(nombrePostComentario)) {
                                    postComentario = post;
                                    break;
                                }
                            }
                            if (postComentario != null) {
                                Comentarios nuevoComentario = new Comentarios(textoComentario, propietarioComentario);
                                postComentario.agregarComentario(nuevoComentario);
                                System.out.println("Comentario añadido con éxito.");
                            } else {
                                System.out.println("Post no encontrado.");
                            }
                        } else {
                            System.out.println("Usuario propietario del post no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario que comenta no encontrado.");
                    }
                    break;

                case 4:
                    // Comenzar a Seguir a un Usuario
                    System.out.print("Introduce el nombre del usuario que sigue: ");
                    String nombreSeguidor = scanner.nextLine();
                    Usuario seguidor = buscarUsuarioPorNombre(red, nombreSeguidor);
                    if (seguidor != null) {
                        System.out.print("Introduce el nombre del usuario a seguir: ");
                        String nombreSeguir = scanner.nextLine();
                        Usuario seguir = buscarUsuarioPorNombre(red, nombreSeguir);
                        if (seguir != null) {
                            seguidor.seguirUsuario(seguir);
                            System.out.println(seguidor.getNombre() + " ahora sigue a " + seguir.getNombre());
                        } else {
                            System.out.println("Usuario a seguir no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario seguidor no encontrado.");
                    }
                    break;

                case 5:
                    // Dejar de Seguir a un Usuario
                    System.out.print("Introduce el nombre del usuario que deja de seguir: ");
                    String nombreDejarSeguir = scanner.nextLine();
                    Usuario dejarSeguir = buscarUsuarioPorNombre(red, nombreDejarSeguir);
                    if (dejarSeguir != null) {
                        System.out.print("Introduce el nombre del usuario a dejar de seguir: ");
                        String nombreDejarSeguirA = scanner.nextLine();
                        Usuario dejarSeguirA = buscarUsuarioPorNombre(red, nombreDejarSeguirA);
                        if (dejarSeguirA != null) {
                            dejarSeguir.dejarDeSeguirUsuario(dejarSeguirA);
                            System.out.println(dejarSeguir.getNombre() + " ha dejado de seguir a " + dejarSeguirA.getNombre());
                        } else {
                            System.out.println("Usuario a dejar de seguir no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario seguidor no encontrado.");
                    }
                    break;

                case 6:
                    // Eliminar Usuario
                    System.out.print("Introduce el nombre del usuario a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    Usuario usuarioEliminar = buscarUsuarioPorNombre(red, nombreEliminar);
                    if (usuarioEliminar != null) {
                        red.eliminarUsuario(usuarioEliminar);
                        System.out.println("Usuario " + nombreEliminar + " eliminado con éxito.");
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 7:
                    // Listar Posts de un Usuario
                    System.out.print("Introduce el nombre del usuario para listar sus posts: ");
                    String nombreUsuarioPosts = scanner.nextLine();
                    Usuario usuarioPosts = buscarUsuarioPorNombre(red, nombreUsuarioPosts);
                    if (usuarioPosts != null) {
                        List<Post> posts = red.listarPostsDeUsuario(usuarioPosts);
                        if (!posts.isEmpty()) {
                            posts.forEach(System.out::println);
                        } else {
                            System.out.println("Este usuario no tiene posts.");
                        }
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 8:
                    // Listar Comentarios de un Usuario
                    System.out.print("Introduce el nombre del usuario para listar sus comentarios: ");
                    String nombreUsuarioComentarios = scanner.nextLine();
                    Usuario usuarioComentarios = buscarUsuarioPorNombre(red, nombreUsuarioComentarios);
                    if (usuarioComentarios != null) {
                        List<Comentarios> comentarios = red.listarComentariosDeUsuario(usuarioComentarios);
                        if (!comentarios.isEmpty()) {
                            comentarios.forEach(System.out::println);
                        } else {
                            System.out.println("Este usuario no tiene comentarios.");
                        }
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 9:
                    // Mostrar el Número de Comentarios de un Post
                    System.out.print("Introduce el nombre del usuario propietario del post: ");
                    String nombrePostComentarioNumero = scanner.nextLine();
                    Usuario propietarioPostComentarioNumero = buscarUsuarioPorNombre(red, nombrePostComentarioNumero);
                    if (propietarioPostComentarioNumero != null) {
                        System.out.print("Introduce el contenido del post (o una parte del mismo): ");
                        String contenidoPostNumero = scanner.nextLine();
                        boolean encontrado = false;
                        for (Post post : propietarioPostComentarioNumero.getPosts()) {
                            if (post.getContenido().contains(contenidoPostNumero)) {
                                System.out.println("Número de comentarios: " + post.obtenerNumeroComentarios());
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Post no encontrado.");
                        }
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;

                case 0:
                    // Salir
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }

    // Método auxiliar para buscar un usuario por nombre
    public static Usuario buscarUsuarioPorNombre(RedSocial red, String nombre) {
        for (Usuario usuario : red.usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }
}