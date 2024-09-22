import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

    public static void procesoResolucion(String predicadoNegado, List<String> reglas) {
        List<String> predicadoBase = Arrays.asList(predicadoNegado);
        System.out.println("\n--- Iniciando proceso de resolución ---");
        
        // Separar las reglas en grupos
        List<List<String>> reglasSeparadas = separarReglas(reglas);
        Set<String> predicadosResueltos = new HashSet<>();

        while (true) {
            // Comprobar si ya se resolvio este predicado
            if (predicadosResueltos.contains(predicadoNegado)) {
                System.out.println("El predicado '" + predicadoNegado + "' ya fue resuelto. Deteniendo el proceso para evitar bucles.");
                break;
            }

            predicadosResueltos.add(predicadoNegado);

            // Buscar la regla que tiene el predicado negado
            List<String> reglaEncontrada = buscarPredicadoONegacion(Arrays.asList(predicadoNegado), reglasSeparadas);

            if (reglaEncontrada != null) {
                System.out.println("Comparando con el predicado negado '" + predicadoNegado + "'");
                String nuevoPredicado = generarNuevoPredicado(Arrays.asList(predicadoNegado), reglaEncontrada);

                if (!nuevoPredicado.isEmpty()) { // Asegurarse que no esté vacío
                    System.out.println("\nNuevo predicado: " + nuevoPredicado);
                    
                    // Verificar si el nuevo predicado se puede derivar
                    if (verificarPredicadoDerivable(nuevoPredicado, reglas)) {
                        List<String> nuevoProblema = crearProblemaSinReglaUsada(Arrays.asList(predicadoNegado), reglas);
                        reglas = nuevoProblema; // Actualizar las reglas
                        predicadoNegado = nuevoPredicado; // Actualizar el predicado
                        reglasSeparadas = separarReglas(reglas); // Re-separar reglas
                    } else {
                        System.out.println("\n¡El nuevo predicado no es derivable de las reglas actuales!");
                        break; // Salir si no se puede derivar
                    }
                } else {
                    System.out.println("\n¡No hay más predicados que resolver!");
                    break; // Salir si no hay más
                }
            } else {
                System.out.println("\nEl predicado '" + predicadoNegado + "' no fue encontrado en las reglas.");
                break; // Salir si no se encuentra el predicado
            }
        }
        System.out.println("------------------  " + Arrays.asList(predicadoNegado) + "  ---------------------");
    }

    public static List<List<String>> separarReglas(List<String> reglas) {
        List<List<String>> separadas = new ArrayList<>();
        for (String regla : reglas) {
            separadas.add(Arrays.asList(regla.split(" v "))); // Separar reglas por "v"
        }
        return separadas; // Devolver las reglas separadas
    }

    public static List<String> buscarPredicadoONegacion(List<String> predicadoCompleto, List<List<String>> reglas) {
        String[] subPredicados = predicadoCompleto.get(0).split(" v ");
        for (String subPredicado : subPredicados) {
            String predicadoSinNegacion = subPredicado.startsWith("¬") ? subPredicado.substring(1) : subPredicado;

            for (List<String> regla : reglas) {
                for (String subRegla : regla) {
                    if (subRegla.contains(predicadoSinNegacion) || subRegla.contains("¬" + predicadoSinNegacion)) {
                        System.out.println("\n---------------------------------------------------------------------------------------------------------------------");
                        System.out.println("|El sub-predicado '" + subPredicado + "' del predicado '" + predicadoCompleto + "' fue encontrado en la regla: " + regla);
                        return regla; // Devolver la regla que contiene el sub-predicado
                    }
                }
            }
        }
        return null; // No se encontró el predicado
    }

    public static String generarNuevoPredicado(List<String> predicado, List<String> regla) {
        List<String> reglaSinPredicado = new ArrayList<>(regla);
        String[] predicadoOriginal = predicado.get(0).split(" v ");
        List<String> predicadosRestantes = new ArrayList<>();

        for (String p : predicadoOriginal) {
            String subPredicadoSinNegacion = p.startsWith("¬") ? p.substring(1) : p;

            for (String subRegla : new ArrayList<>(reglaSinPredicado)) {
                String subReglaSinNegacion = subRegla.startsWith("¬") ? subRegla.substring(1) : subRegla;

                if (subPredicadoSinNegacion.equals(subReglaSinNegacion)) {
                    reglaSinPredicado.remove(subRegla);
                    break;
                }
            }

            if (!regla.contains(subPredicadoSinNegacion) && !regla.contains("¬" + subPredicadoSinNegacion)) {
                predicadosRestantes.add(p);
            }
        }

        if (reglaSinPredicado.isEmpty() && predicadosRestantes.isEmpty()) {
            return ""; // Para evitar retorno de predicado vacío
        }

        // Generar nuevo predicado evitando un `v` al final
        String nuevoPredicado = String.join(" v ", reglaSinPredicado);
        if (!predicadosRestantes.isEmpty()) {
            if (!nuevoPredicado.isEmpty()) {
                nuevoPredicado += " v "; // Añadir `v` solo si hay algo en `reglaSinPredicado`
            }
            nuevoPredicado += String.join(" v ", predicadosRestantes);
        }
        
        System.out.println("|Nuevo predicado generado: " + nuevoPredicado);
        return nuevoPredicado; // Devolver el nuevo predicado
    }

    public static boolean verificarPredicadoDerivable(String predicado, List<String> reglas) {
        List<List<String>> reglasSeparadas = separarReglas(reglas);
        for (List<String> regla : reglasSeparadas) {
            for (String subPredicado : predicado.split(" v ")) {
                String predicadoSinNegacion = subPredicado.startsWith("¬") ? subPredicado.substring(1) : subPredicado;
                for (String subRegla : regla) {
                    if (subRegla.contains(predicadoSinNegacion)) {
                        return true; // El predicado es derivable
                    }
                }
            }
        }
        return false; // No es derivable
    }

    public static List<String> crearProblemaSinReglaUsada(List<String> predicado, List<String> reglasOriginales) {
        String predicadoSinNegacion = predicado.get(0).startsWith("¬") ? predicado.get(0).substring(1) : predicado.get(0);
        List<String> nuevoProblema = new ArrayList<>();
        for (String regla : reglasOriginales) {
            if (!regla.contains(predicadoSinNegacion) && !regla.contains("¬" + predicadoSinNegacion)) {
                nuevoProblema.add(regla);
            }
        }
        return nuevoProblema;
    }
}
