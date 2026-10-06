import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Queue<Cliente> preferenciales = new LinkedList<>();
        Queue<Cliente> normales = new LinkedList<>();
        Queue<Cliente> atendidos = new LinkedList<>();

        Metodos m = new Metodos();

        boolean continuar = true;

        while (continuar) {

            System.out.println();
            System.out.println("===== BANCO =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Consultar clientes esperando");
            System.out.println("3. Llamar siguiente cliente");
            System.out.println("4. Marcar cliente como atendido");
            System.out.println("5. Cambiar normal a preferencial");
            System.out.println("6. Cancelar turno");
            System.out.println("7. Buscar cliente");
            System.out.println("8. Consultar cuántos esperan");
            System.out.println("9. Mostrar cantidad por tipo");
            System.out.println("10. Salir");

            int opcion = m.validarOpcion(sc, 1, 10);

            switch (opcion) {

                case 1:
                    m.registrarCliente(preferenciales, normales, sc);
                    break;

                case 2:
                    m.consultarEsperando(preferenciales, normales);
                    break;

                case 3:
                    m.llamarSiguiente(preferenciales, normales);
                    break;

                case 4:
                    m.marcarAtendido(
                            preferenciales,
                            normales,
                            atendidos
                    );
                    break;

                case 5:
                    m.cambiarPreferencial(
                            normales,
                            preferenciales,
                            sc
                    );
                    break;

                case 6:

                    System.out.println("¿En qué cola desea cancelar?");
                    System.out.println("1. Preferencial");
                    System.out.println("2. Normal");

                    int tipo = m.validarOpcion(sc, 1, 2);

                    if (tipo == 1) {

                        m.cancelarTurno(preferenciales, sc);

                    } else {

                        m.cancelarTurno(normales, sc);
                    }

                    break;

                case 7:
                    m.buscarCliente(
                            preferenciales,
                            normales,
                            atendidos,
                            sc
                    );
                    break;

                case 8:
                    m.cantidadEsperando(
                            preferenciales,
                            normales
                    );
                    break;

                case 9:
                    m.cantidadPorTipo(
                            preferenciales,
                            normales
                    );
                    break;

                case 10:
                    continuar = false;
                    System.out.println("Hasta luego.");
                    break;
            }
        }
    }
}
