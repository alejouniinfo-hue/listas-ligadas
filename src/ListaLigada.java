import java.util.ArrayList;

public class ListaLigada {
    private Nodo cabeza;

    public Nodo getCabeza() {
        return cabeza;
    }

    // ✅ Agregar nota
    public void agregar(NotaMusical nota) {
        Nodo nuevo = new Nodo(nota);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    // ✅ Eliminar por índice
    public void eliminar(int index) {
        if (cabeza == null) return;

        if (index == 0) {
            cabeza = cabeza.siguiente;
            return;
        }

        Nodo actual = cabeza;

        for (int i = 0; i < index - 1 && actual.siguiente != null; i++) {
            actual = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
        }
    }

    // ✅ Obtener nota por índice
    public NotaMusical obtener(int index) {
        Nodo actual = cabeza;
        int i = 0;

        while (actual != null) {
            if (i == index) {
                return actual.dato;
            }
            actual = actual.siguiente;
            i++;
        }

        return null;
    }

    // ✅ Convertir a ArrayList (para JSON)
    public ArrayList<NotaMusical> toArrayList() {
        ArrayList<NotaMusical> lista = new ArrayList<>();
        Nodo actual = cabeza;

        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }

        return lista;
    }

    // ✅ Cargar desde ArrayList (cuando leas JSON)
    public void cargarDesdeArray(ArrayList<NotaMusical> lista) {
        cabeza = null;

        for (NotaMusical n : lista) {
            agregar(n);
        }
    }
}