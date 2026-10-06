Métodos para colas:
1. Crear/agregar elementos
public Queue<Obj> llenarCola(Queue<Obj> cola, Scanner sc) {

    boolean continuar = true;

    while (continuar) {

        System.out.println("Desea agregar un registro: 1.Si, 2.No");
        int opt = sc.nextInt();

        if (opt == 1) {

            Obj o = new Obj();

            System.out.println("Ingrese número:");
            o.setNumero(sc.nextInt());

            cola.offer(o);

        } else {
            continuar = false;
        }
    }
   Ojo: si después del nextInt() vas a usar nextLine(), ya sabes que necesitas el:

sc.nextLine();

2. Mostrar la cola
public void mostrarCola(Queue<Obj> cola) {

    for (Obj o : cola) {
        System.out.println(o.getNumero());
    }
}

 3. Sacar el elemento
 public Queue<Obj> eliminar(Queue<Obj> cola) {

    if (!cola.isEmpty()) {

        Obj o = cola.poll();

        System.out.println("Se eliminó: " + o.getNumero());
    }

    return cola;
}

4. Consultar el primero 
public void mostrarPrimero(Queue<Obj> cola) {

    if (!cola.isEmpty()) {

        Obj o = cola.peek();

        System.out.println("Primero: " + o.getNumero());
    }
}
 
5. ELIMINAR REGISTRO ESPECIFICO:
Queue<Obj> aux = new LinkedList<>();

while (!cola.isEmpty()) {

    Obj o = cola.poll();

    if (o.getNumero() != numero) {
        aux.offer(o);
    }
}

while (!aux.isEmpty()) {
    cola.offer(aux.poll());
}

6. BUCAR MAYOR O MENOR 
public Queue<Obj> buscarMayor(Queue<Obj> cola) {

    if (cola.isEmpty()) {
        return cola;
    }

    Obj mayor = null;
    Queue<Obj> aux = new LinkedList<>();

    while (!cola.isEmpty()) {

        Obj o = cola.poll();
        aux.offer(o);

        if (mayor == null) {
            mayor = o;
        } else if (o.getNumero() > mayor.getNumero()) {
            mayor = o;
        }
    }

    System.out.println("Mayor: " + mayor.getNumero());

    while (!aux.isEmpty()) {
        cola.offer(aux.poll());
    }

    return cola;
}

7. ESTRUCTURA DEL MENU:
Queue<Obj> cola = new LinkedList<>();
Metodos m = new Metodos();

boolean continuar = true;

while (continuar) {

    System.out.println("1. Agregar");
    System.out.println("2. Eliminar");
    System.out.println("3. Consultar primero");
    System.out.println("4. Mostrar");
    System.out.println("5. Salir");

    int opt = sc.nextInt();

    switch (opt) {

        case 1:
            cola = m.llenarCola(cola, sc);
            break;

        case 2:
            cola = m.eliminar(cola);
            break;

        case 3:
            m.mostrarPrimero(cola);
            break;

        case 4:
            m.mostrarCola(cola);
            break;

        case 5:
            continuar = false;
            break;

        default:
            System.out.println("Opción inválida");
    }
}

8. MÉTODOS DE VALIDACIÓN
VALIDACIÓN NUMERICA:
public int validarNumero(Scanner sc) {

    while (!sc.hasNextInt()) {
        System.out.println("Debe ingresar un número.");
        sc.nextLine();
    }

    int numero = sc.nextInt();
    sc.nextLine();

    return numero;
}

vALIDAR LETRA:
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




    
    return cola;
}
