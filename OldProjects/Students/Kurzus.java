import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kurzus {
    private String kurzuskod;
    private String nev;
    private int maxpont;
    private int kreditertek;
    private OsztalyozasiMod osztalyozasiMod;

    public Kurzus(String kurzuskod, String nev, int maxpont, int kreditertek,
                  OsztalyozasiMod osztalyozasiMod) {
        this.kurzuskod = kurzuskod;
        this.nev = nev;
        this.maxpont = maxpont;
        this.kreditertek = kreditertek;
        this.osztalyozasiMod = osztalyozasiMod;
    }

    public String getKurzuskod() {
        return kurzuskod;
    }

    public void setKurzuskod(String kurzuskod) {
        this.kurzuskod = kurzuskod;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getMaxpont() {
        return maxpont;
    }

    public void setMaxpont(int maxpont) {
        this.maxpont = maxpont;
    }

    public int getKreditertek() {
        return kreditertek;
    }

    public void setKreditertek(int kreditertek) {
        this.kreditertek = kreditertek;
    }

    public OsztalyozasiMod getOsztalyozasiMod() {
        return osztalyozasiMod;
    }

    public void setOsztalyozasiMod(OsztalyozasiMod osztalyozasiMod) {
        this.osztalyozasiMod = osztalyozasiMod;
    }

    public static Kurzus beolvas(String nev) {
        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(nev))) {
            while (sc.hasNext()) {
                lines.add(sc.nextLine());
            }

            String[] firstLine = lines.get(0).split(";");
            OsztalyozasiMod osztalyozasiMod = OsztalyozasiMod.beolvas(lines.get(1));

            return new Kurzus(
                    firstLine[0],
                    firstLine[1],
                    Integer.parseInt(firstLine[2]),
                    Integer.parseInt(firstLine[3]),
                    osztalyozasiMod
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

