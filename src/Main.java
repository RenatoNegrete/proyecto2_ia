import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<String> clausulas = new ArrayList<>();
        Solver solver = new Solver();
        clausulas = solver.cargar("//home/estudiante/IdeaProjects/proyecto2_ia/src/test.txt");
        System.out.println(clausulas);
    }
}