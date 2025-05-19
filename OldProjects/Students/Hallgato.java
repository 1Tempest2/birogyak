import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Hallgato {
    private String nev;
    private String neptun;
    private Map<String, Set<Teljesites>> teljesitesek;

    public Hallgato(String nev, String neptun) {
        this.nev = nev;
        this.teljesitesek = new HashMap<>();

        if (!neptun.toUpperCase().equals(neptun) ||
                neptun.length() != 6 ||
                !neptun.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("hibas neptun kod");
        }
        this.neptun = neptun;
    }

    public void beiratkozik(String felev) {
        if (teljesitesek.containsKey(felev)) {
            System.err.println("nem lehet tobbszor beiratkozni");
        } else {
            teljesitesek.put(felev, new HashSet<>());
        }
    }

    public void teljesiteseketFelvesz(String fajlnev) {
        try (Scanner sc = new Scanner(new File(fajlnev))) {
            List<List<String>> lines = new ArrayList<>();
            while (sc.hasNext()) {
                lines.add(List.of(sc.nextLine().split(";")));
            }

            String felev = fajlnev;
            int dotIndex = felev.lastIndexOf('.');
            if (dotIndex > 0) {
                felev = felev.substring(0, dotIndex);
            }

            if (!teljesitesek.containsKey(felev)) {
                teljesitesek.put(felev, new HashSet<>());
            }

            for (var line : lines) {
                Kurzus temp = Kurzus.beolvas(line.get(0));
                teljesitesek.get(felev).add(new Teljesites(temp, Integer.parseInt(line.get(1))));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int bukas() {
        int count = 0;
        for (Map.Entry<String, Set<Teljesites>> entry : teljesitesek.entrySet()) {
            Set<Teljesites> teljesitesSet = entry.getValue();
            for (Teljesites t : teljesitesSet) {
                if (t.erdemjegy() == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    public void felvettKurzusok(String felevnev, String fajlnev) {
        Set<Teljesites> temp = teljesitesek.get(felevnev);
        if (temp == null) {
            temp = new HashSet<>();
        }

        try (FileWriter fw = new FileWriter(new File(fajlnev))) {
            for (Teljesites kurzus : temp) {
                fw.write(kurzus.getKurzus().getNev() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void katasztrofa(String fajlnev) {
        Map<String, Integer> egyesekSzama = new HashMap<>();

        for (Map.Entry<String, Set<Teljesites>> entry : teljesitesek.entrySet()) {
            Set<Teljesites> teljesitesSet = entry.getValue();
            int numEgyesek = 0;
            for (Teljesites t : teljesitesSet) {
                if (t.erdemjegy() == 1) {
                    numEgyesek++;
                }
            }
            egyesekSzama.put(entry.getKey(), numEgyesek);
        }

        int maxFailures = 0;
        for (Integer failures : egyesekSzama.values()) {
            if (failures > maxFailures) {
                maxFailures = failures;
            }
        }

        List<String> bukottFelevek = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : egyesekSzama.entrySet()) {
            if (entry.getValue() == maxFailures) {
                bukottFelevek.add(entry.getKey());
            }
        }

        try (FileWriter fw = new FileWriter(new File(fajlnev))) {
            if (maxFailures == 0) {
                fw.write("mindent teljesitett\n");
            } else if (bukottFelevek.size() == 1) {
                fw.write(bukottFelevek.get(0) + "\n");
            } else {
                fw.write("tobb ilyen felev is van\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double diplomaatlag() {
        int sumKredit = 0;
        double sumErdemjegyKredit = 0;

        for (Map.Entry<String, Set<Teljesites>> entry : teljesitesek.entrySet()) {
            Set<Teljesites> teljesitesSet = entry.getValue();
            for (Teljesites t : teljesitesSet) {
                int kredit = t.getKurzus().getKreditertek();
                sumKredit += kredit;
                sumErdemjegyKredit += kredit * t.erdemjegy();
            }
        }

        return sumKredit > 0 ? sumErdemjegyKredit / sumKredit : 0;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getNeptun() {
        return neptun;
    }

    public void setNeptun(String neptun) {
        this.neptun = neptun;
    }

    public Map<String, Set<Teljesites>> getTeljesitesek() {
        return teljesitesek;
    }

    public void setTeljesitesek(Map<String, Set<Teljesites>> teljesitesek) {
        this.teljesitesek = teljesitesek;
    }
}
