import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Metodos {

    private int contadorTurno = 1;
    private Cliente clienteLlamado = null;

    // =====================================================
    // VALIDACIONES
    // =====================================================

    public int validarNumero(Scanner sc) {

        while (!sc.hasNextInt()) {
            System.out.println("Debe ingresar un número.");
            sc.nextLine();
        }

        int numero = sc.nextInt();
        sc.nextLine();

        return numero;
    }

    public long validarIdentificacion(Scanner sc) {

        while (!sc.hasNextLong()) {
            System.out.println("La identificación debe ser numérica.");
            sc.nextLine();
        }

        long identificacion = sc.nextLong();
        sc.nextLine();

        while (identificacion <= 0) {
            System.out.println("La identificación debe ser mayor que 0.");
            identificacion = validarIdentificacion(sc);
        }

        return identificacion;
    }

    public String validarLetras(Scanner sc) {

        String texto;

        do {
            texto = sc.nextLine();

            if (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("Solo se permiten letras.");
            }

        } while (!texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"));

        return texto;
    }

    public int validarEdad(Scanner sc) {

        int edad;

        do {
            System.out.println("Ingrese la edad:");
            edad = validarNumero(sc);

            if (edad < 1 || edad > 120) {
                System.out.println("La edad debe estar entre 1 y 120.");
            }

        } while (edad < 1 || edad > 120);

        return edad;
    }

    public int validarOpcion(Scanner sc, int minimo, int maximo) {

        int opcion;

        do {
            opcion = validarNumero(sc);

            if (opcion < minimo || opcion > maximo) {
                System.out.println("Ingrese una opción entre "
                        + minimo + " y " + maximo + ".");
            }

        } while (opcion < minimo || opcion > maximo);

        return opcion;
    }


    // =====================================================
    // 1. REGISTRAR CLIENTE
    // =====================================================

    public void registrarCliente(Queue<Cliente> preferenciales,
                                 Queue<Cliente> normales,
                                 Scanner sc) {

        Cliente c = new Cliente();

        System.out.println("Ingrese identificación:");
        c.setIdentificacion(validarIdentificacion(sc));

        System.out.println("Ingrese nombre:");
        c.setNombre(validarLetras(sc));

        System.out.println("Ingrese tipo de trámite:");
        c.setTipoTramite(validarLetras(sc));

        c.setEdad(validarEdad(sc));

        System.out.println("¿Tiene atención preferencial?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int opcion = validarOpcion(sc, 1, 2);

        if (opcion == 1) {
            c.setPreferencial(true);
        } else {
            c.setPreferencial(false);
        }

        c.setNumeroTurno(contadorTurno);
        contadorTurno++;

        if (c.isPreferencial()) {
            preferenciales.offer(c);
            System.out.println("Cliente agregado a preferenciales.");
        } else {
            normales.offer(c);
            System.out.println("Cliente agregado a normales.");
        }

        System.out.println("Número de turno: " + c.getNumeroTurno());
    }


    // =====================================================
    // 2. CONSULTAR CLIENTES ESPERANDO
    // =====================================================

    public void consultarEsperando(Queue<Cliente> preferenciales,
                                   Queue<Cliente> normales) {

        System.out.println("===== CLIENTES PREFERENCIALES =====");

        for (Cliente c : preferenciales) {
            mostrarCliente(c);
        }

        System.out.println("===== CLIENTES NORMALES =====");

        for (Cliente c : normales) {
            mostrarCliente(c);
        }
    }


    // =====================================================
    // 3. LLAMAR AL SIGUIENTE
    // =====================================================

    public void llamarSiguiente(Queue<Cliente> preferenciales,
                                Queue<Cliente> normales) {

        if (clienteLlamado != null) {
            System.out.println("Ya hay un cliente llamado actualmente.");
            System.out.println("Turno: " + clienteLlamado.getNumeroTurno());
            return;
        }

        if (!preferenciales.isEmpty()) {

            clienteLlamado = preferenciales.peek();

        } else if (!normales.isEmpty()) {

            clienteLlamado = normales.peek();

        } else {

            System.out.println("No hay clientes esperando.");
            return;
        }

        System.out.println("===== SIGUIENTE CLIENTE =====");
        mostrarCliente(clienteLlamado);
    }


    // =====================================================
    // 4. MARCAR CLIENTE COMO ATENDIDO
    // =====================================================

    public void marcarAtendido(Queue<Cliente> preferenciales,
                               Queue<Cliente> normales,
                               Queue<Cliente> atendidos) {

        if (clienteLlamado == null) {
            System.out.println("Primero debe llamar a un cliente.");
            return;
        }

        Cliente c;

        if (clienteLlamado.isPreferencial()) {

            c = preferenciales.poll();

        } else {

            c = normales.poll();
        }

        c.setAtendido(true);
        atendidos.offer(c);

        System.out.println("Cliente atendido correctamente.");
        System.out.println("Turno: " + c.getNumeroTurno());

        clienteLlamado = null;
    }


    // =====================================================
    // 5. CAMBIAR NORMAL A PREFERENCIAL
    // =====================================================

    public void cambiarPreferencial(Queue<Cliente> normales,
                                    Queue<Cliente> preferenciales,
                                    Scanner sc) {

        System.out.println("Ingrese la identificación del cliente:");
        long identificacion = validarIdentificacion(sc);

        Queue<Cliente> aux = new LinkedList<>();

        boolean encontrado = false;

        while (!normales.isEmpty()) {

            Cliente c = normales.poll();

            if (c.getIdentificacion() == identificacion) {

                c.setPreferencial(true);
                preferenciales.offer(c);
                encontrado = true;

                System.out.println("Cliente cambiado a preferencial.");

            } else {

                aux.offer(c);
            }
        }

        while (!aux.isEmpty()) {
            normales.offer(aux.poll());
        }

        if (!encontrado) {
            System.out.println("No se encontró el cliente en la cola normal.");
        }
    }


    // =====================================================
    // 6. CANCELAR TURNO
    // =====================================================

    public void cancelarTurno(Queue<Cliente> cola,
                              Scanner sc) {

        System.out.println("Ingrese la identificación:");
        long identificacion = validarIdentificacion(sc);

        Queue<Cliente> aux = new LinkedList<>();

        boolean encontrado = false;

        while (!cola.isEmpty()) {

            Cliente c = cola.poll();

            if (c.getIdentificacion() == identificacion) {

                encontrado = true;

                if (clienteLlamado != null &&
                    clienteLlamado.getIdentificacion() == identificacion) {

                    clienteLlamado = null;
                }

                System.out.println("Turno cancelado: "
                        + c.getNumeroTurno());

            } else {

                aux.offer(c);
            }
        }

        while (!aux.isEmpty()) {
            cola.offer(aux.poll());
        }

        if (!encontrado) {
            System.out.println("El cliente no se encuentra en esta cola.");
        }
    }


    // =====================================================
    // 7. BUSCAR CLIENTE
    // =====================================================

    public void buscarCliente(Queue<Cliente> preferenciales,
                              Queue<Cliente> normales,
                              Queue<Cliente> atendidos,
                              Scanner sc) {

        System.out.println("Ingrese la identificación:");
        long identificacion = validarIdentificacion(sc);

        Cliente encontrado = null;

        for (Cliente c : preferenciales) {

            if (c.getIdentificacion() == identificacion) {
                encontrado = c;
                break;
            }
        }

        if (encontrado == null) {

            for (Cliente c : normales) {

                if (c.getIdentificacion() == identificacion) {
                    encontrado = c;
                    break;
                }
            }
        }

        if (encontrado == null) {

            for (Cliente c : atendidos) {

                if (c.getIdentificacion() == identificacion) {
                    encontrado = c;
                    break;
                }
            }
        }

        if (encontrado != null) {

            mostrarCliente(encontrado);

        } else {

            System.out.println("Cliente no encontrado.");
        }
    }


    // =====================================================
    // 8. CANTIDAD DE PERSONAS ESPERANDO
    // =====================================================

    public void cantidadEsperando(Queue<Cliente> preferenciales,
                                  Queue<Cliente> normales) {

        int total = preferenciales.size() + normales.size();

        System.out.println("Personas esperando: " + total);
    }


    // =====================================================
    // 9. CANTIDAD NORMALES Y PREFERENCIALES
    // =====================================================

    public void cantidadPorTipo(Queue<Cliente> preferenciales,
                                Queue<Cliente> normales) {

        System.out.println("Preferenciales pendientes: "
                + preferenciales.size());

        System.out.println("Normales pendientes: "
                + normales.size());
    }


    // =====================================================
    // MÉTODO AUXILIAR PARA MOSTRAR
    // =====================================================

    public void mostrarCliente(Cliente c) {

        System.out.println("Turno: " + c.getNumeroTurno());
        System.out.println("Identificación: " + c.getIdentificacion());
        System.out.println("Nombre: " + c.getNombre());
        System.out.println("Trámite: " + c.getTipoTramite());
        System.out.println("Edad: " + c.getEdad());
        System.out.println("Preferencial: "
                + (c.isPreferencial() ? "Sí" : "No"));
        System.out.println("Atendido: "
                + (c.isAtendido() ? "Sí" : "No"));
        System.out.println("--------------------------------");
    }
}
