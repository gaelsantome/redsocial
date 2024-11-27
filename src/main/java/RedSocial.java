import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RedSocial {
    private List<Usuario> usuarios;
    private Usuario usuarioActual;  // Usuario que está actualmente logueado

    public RedSocial() {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;  // No hay usuario logueado al inicio
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
        int opcion;

        // Menú de registro e inicio de sesión
        do {
            System.out.println("\n--- Menú de Registro e Inicio de Sesión ---");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Registrar un nuevo usuario
                    System.out.print("Introduce el nombre del nuevo usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    Usuario nuevoUsuario = new Usuario(nombreUsuario);
                    red.agregarUsuario(nuevoUsuario);
                    System.out.println("Usuario " + nombreUsuario + " registrado con éxito.");
                    break;

                case 2:
                    // Iniciar sesión
                    System.out.print("Introduce tu nombre de usuario: ");
                    String nombreDeUsuario = scanner.nextLine();
                    Usuario usuarioLogueado = red.buscarUsuarioPorNombre(nombreDeUsuario);
                    if (usuarioLogueado != null) {
                        red.usuarioActual = usuarioLogueado;  // Establecer el usuario logueado
                        System.out.println("Has iniciado sesión como " + usuarioLogueado.getNombre());

                        red.menuPrincipal(scanner);
                    } else {
                        System.out.println("Usuario no encontrado. Intenta de nuevo.");
                    }
                    break;

                case 0:
                    // Salir
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 0);

        scanner.close();
    }

    // Método para buscar un usuario por su nombre
    public Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    // Menú principal de la red social
    public void menuPrincipal(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Añadir Post");
            System.out.println("2. Añadir Comentario");
            System.out.println("3. Comenzar a Seguir a un Usuario");
            System.out.println("4. Dejar de Seguir a un Usuario");
            System.out.println("5. Eliminar un Usuario");
            System.out.println("6. Listar Todos los Posts de un Usuario");
            System.out.println("7. Listar Todos los Comentarios de un Usuario");
            System.out.println("8. Mostrar el Número de Comentarios de un Post");
            System.out.println("9. Cerrar sesión");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    // Añadir Post
                    if (usuarioActual != null) {
                        System.out.print("Introduce el tipo de contenido del post (texto, imagen, video): ");
                        String tipoContenido = scanner.nextLine();
                        System.out.print("Introduce el contenido del post: ");
                        String contenidoPost = scanner.nextLine();

                        if (tipoContenido.equalsIgnoreCase("imagen") || tipoContenido.equalsIgnoreCase("video")) {
                            // Si el contenido es imagen o video, pedimos las dimensiones
                            System.out.print("Introduce el ancho de la imagen/video (en píxeles): ");
                            int ancho = scanner.nextInt();
                            System.out.print("Introduce el alto de la imagen/video (en píxeles): ");
                            int alto = scanner.nextInt();
                            scanner.nextLine();  // Limpiar el buffer

                            // Creamos un nuevo Post con dimensiones
                            Post nuevoPost = new Post(usuarioActual, tipoContenido, contenidoPost, ancho, alto);
                            usuarioActual.agregarPost(nuevoPost);
                            System.out.println("Post de " + tipoContenido + " añadido con éxito.");
                        } else {
                            // Crear el post sin dimensiones
                            Post nuevoPost = new Post(usuarioActual, tipoContenido, contenidoPost);
                            usuarioActual.agregarPost(nuevoPost);
                            System.out.println("Post añadido con éxito.");
                        }
                    }
                    break;

                case 2:
                    // Añadir Comentario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario propietario del post: ");
                        String nombrePostComentario = scanner.nextLine();
                        Usuario propietarioPostComentario = buscarUsuarioPorNombre(nombrePostComentario);
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
                                Comentarios nuevoComentario = new Comentarios(textoComentario, usuarioActual);
                                postComentario.agregarComentario(nuevoComentario);
                                System.out.println("Comentario añadido con éxito.");
                            } else {
                                System.out.println("Post no encontrado.");
                            }
                        } else {
                            System.out.println("Usuario propietario del post no encontrado.");
                        }
                    }
                    break;

                case 3:
                    // Comenzar a Seguir a un Usuario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario a seguir: ");
                        String nombreSeguir = scanner.nextLine();
                        Usuario seguir = buscarUsuarioPorNombre(nombreSeguir);
                        if (seguir != null) {
                            usuarioActual.seguirUsuario(seguir);
                            System.out.println(usuarioActual.getNombre() + " ahora sigue a " + seguir.getNombre());
                        } else {
                            System.out.println("Usuario a seguir no encontrado.");
                        }
                    }
                    break;

                case 4:
                    // Dejar de Seguir a un Usuario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario a dejar de seguir: ");
                        String nombreDejarSeguir = scanner.nextLine();
                        Usuario dejarSeguirA = buscarUsuarioPorNombre(nombreDejarSeguir);
                        if (dejarSeguirA != null) {
                            usuarioActual.dejarDeSeguirUsuario(dejarSeguirA);
                            System.out.println(usuarioActual.getNombre() + " ha dejado de seguir a " + dejarSeguirA.getNombre());
                        } else {
                            System.out.println("Usuario a dejar de seguir no encontrado.");
                        }
                    }
                    break;

                case 5:
                    // Eliminar Usuario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario a eliminar: ");
                        String nombreEliminar = scanner.nextLine();
                        Usuario usuarioEliminar = buscarUsuarioPorNombre(nombreEliminar);
                        if (usuarioEliminar != null) {
                            eliminarUsuario(usuarioEliminar);
                            System.out.println("Usuario " + nombreEliminar + " eliminado con éxito.");
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    }
                    break;

                case 6:
                    // Listar Posts de un Usuario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario para listar sus posts: ");
                        String nombreUsuarioPosts = scanner.nextLine();
                        Usuario usuarioPosts = buscarUsuarioPorNombre(nombreUsuarioPosts);
                        if (usuarioPosts != null) {
                            List<Post> posts = listarPostsDeUsuario(usuarioPosts);
                            if (!posts.isEmpty()) {
                                posts.forEach(System.out::println);
                            } else {
                                System.out.println("Este usuario no tiene posts.");
                            }
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    }
                    break;

                case 7:
                    // Listar Comentarios de un Usuario
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario para listar sus comentarios: ");
                        String nombreUsuarioComentarios = scanner.nextLine();
                        Usuario usuarioComentarios = buscarUsuarioPorNombre(nombreUsuarioComentarios);
                        if (usuarioComentarios != null) {
                            List<Comentarios> comentarios = listarComentariosDeUsuario(usuarioComentarios);
                            if (!comentarios.isEmpty()) {
                                comentarios.forEach(System.out::println);
                            } else {
                                System.out.println("Este usuario no tiene comentarios.");
                            }
                        } else {
                            System.out.println("Usuario no encontrado.");
                        }
                    }
                    break;

                case 8:
                    // Mostrar el Número de Comentarios de un Post
                    if (usuarioActual != null) {
                        System.out.print("Introduce el nombre del usuario propietario del post: ");
                        String nombrePostComentarioNumero = scanner.nextLine();
                        Usuario propietarioPostComentarioNumero = buscarUsuarioPorNombre(nombrePostComentarioNumero);
                        if (propietarioPostComentarioNumero != null) {
                            System.out.print("Introduce el contenido del post: ");
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
                    }
                    break;

                case 9:
                    // Cerrar sesión
                    usuarioActual = null;
                    System.out.println("Has cerrado sesión.");
                    break;

                case 0:
                    // Salir
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 0);
    }
}