import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    public List<String> cargar(String archivo) {
        List<String> clausulas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String clausula;
            while ((clausula = br.readLine()) != null) {
                clausulas.add(clausula.trim()); // Agrega la línea a la lista, eliminando espacios en blanco
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return clausulas;
    }
}
