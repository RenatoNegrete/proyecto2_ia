import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solver {

    public List<String> cargar(String archivo) {
        List<String> clausulas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String clausula;
            while ((clausula = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(clausula, "v");
                while (st.hasMoreTokens()) {
                    String token = st.nextToken().trim();
                    int inicio = token.indexOf('(');
                    String predicado = token.substring(0, inicio);
                    System.out.println("Predicado: " + predicado);
                    inicio++;
                    int fin = token.indexOf(')');
                    String args = token.substring(inicio, fin);
                    String[] arguments = args.split(",");
                    String arg1 = arguments[0].trim();
                    String arg2 = arguments.length > 1 ? arguments[1].trim() : null;
                    System.out.println("Arg1: " + arg1 + "; Arg2: " + arg2);

                }
                //clausulas.add(clausula.trim()); Agrega la línea a la lista, eliminando espacios en blanco
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return clausulas;
    }
}
