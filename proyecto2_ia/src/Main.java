import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Leer reglas desde el archivo
        List<String> reglas;
        try {
            reglas = Files.readAllLines(Paths.get("C:\\Users\\quiro\\ProyectoIA\\proyecto3\\proyecto2_ia\\src\\test.txt"));
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de reglas: " + e.getMessage());
            e.printStackTrace(); // Muestra el stack trace completo
            return;
        }
        // Predicado negado que queremos resolver
        String predicadoNegado = "¬Odia(Marco,Cesar)";
        // Iniciar el proceso de resolución
        Solver.procesoResolucion(predicadoNegado, reglas);
    }
}