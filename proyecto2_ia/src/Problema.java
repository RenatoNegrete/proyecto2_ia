import java.util.ArrayList;
import java.util.List;

public class Problema {
    private List<String> reglas; // Lista de reglas del problema

    public Problema() {
        reglas = new ArrayList<>(); // Inicializar la lista de reglas
    }

    public void agregarRegla(String regla) {
        reglas.add(regla); // Agregar regla a la lista
    }

    public List<String> getReglas() {
        return reglas; // Retornar las reglas
    }
}

