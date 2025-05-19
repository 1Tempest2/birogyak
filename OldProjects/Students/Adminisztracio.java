import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Adminisztracio {

    public void tomegesKatasztrofa(List<Hallgato> hallgatok, String mappa) {
        File directory = new File(mappa);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (Hallgato hallgato : hallgatok) {
            String filePath = new File(directory, hallgato.getNeptun() + ".student").getPath();

            hallgato.katasztrofa(filePath);
        }
    }

    public List<Hallgato> hallgatokatFelvesz(String fajlnev) {
        List<Hallgato> hallgatok = new ArrayList<>();
        int minPoints = 0;

        try (Scanner sc = new Scanner(new File(fajlnev))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.startsWith("pontszam")) {
                    String[] parts = line.split("-");
                    minPoints = Integer.parseInt(parts[1]);
                } else if (line.startsWith("hallgato")) {
                    String[] parts = line.split("-");
                    String name = parts[1];
                    String neptun = parts[2];
                    int points = Integer.parseInt(parts[3]);

                    if (points >= minPoints) {
                        hallgatok.add(new Hallgato(name, neptun));
                    }
                }
            }
            return hallgatok;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void legjobbHallgato(String mappa) {
        Map<String, Double> hallgatoAtlagok = new HashMap<>();
        File mappaDir = new File(mappa);

        feldolgozMappa(mappaDir, hallgatoAtlagok);

        String legjobbNeptun = "";
        double legjobbAtlag = -1;

        for (Map.Entry<String, Double> entry : hallgatoAtlagok.entrySet()) {
            if (entry.getValue() > legjobbAtlag) {
                legjobbAtlag = entry.getValue();
                legjobbNeptun = entry.getKey();
            }
        }

        try (FileWriter writer = new FileWriter(new File(mappa, "legjobbhallgato.txt"))) {
            writer.write(legjobbNeptun + ";" + legjobbAtlag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void feldolgozMappa(File mappa, Map<String, Double> hallgatoAtlagok) {
        if (mappa.isDirectory()) {
            File[] fajlok = mappa.listFiles();
            if (fajlok != null) {
                for (File fajl : fajlok) {
                    if (fajl.isDirectory()) {
                        feldolgozMappa(fajl, hallgatoAtlagok);
                    } else if (fajl.getName().endsWith(".student")) {
                        feldolgozHallgatoFajl(fajl, hallgatoAtlagok);
                    }
                }
            }
        }
    }

    private void feldolgozHallgatoFajl(File fajl, Map<String, Double> hallgatoAtlagok) {
        String neptunKod = fajl.getName().substring(0, fajl.getName().lastIndexOf("."));
        try (Scanner scanner = new Scanner(fajl)) {
            if (scanner.hasNextLine()) {
                String content = scanner.nextLine();
                try {
                    double atlag = Double.parseDouble(content);
                    hallgatoAtlagok.put(neptunKod, atlag);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

