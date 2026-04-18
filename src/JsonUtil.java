import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class JsonUtil {

    public static void guardar(ArrayList<NotaMusical> lista, String archivo) {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(archivo);
            gson.toJson(lista, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NotaMusical> cargar(String archivo) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(archivo);

            NotaMusical[] notas = gson.fromJson(reader, NotaMusical[].class);

            ArrayList<NotaMusical> lista = new ArrayList<>();
            for (NotaMusical n : notas) {
                lista.add(n);
            }

            return lista;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}