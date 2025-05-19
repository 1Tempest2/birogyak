import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Olvaso implements kozosInterface {
    private final Map<String, Boolean> konyvek;
    private String nev;
    private int olvasoSzam;
    private int penz;

    public Olvaso(String nev, int olvasoSzam, int penz) {
        this.konyvek = new HashMap<String, Boolean>();
        this.nev = nev;
        this.olvasoSzam = olvasoSzam;
        this.penz = penz;
    }

    public Map<String, Boolean> getKonyvek() {
        return konyvek;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getOlvasoSzam() {
        return olvasoSzam;
    }

    public void setOlvasoSzam(int olvasoSzam) {
        this.olvasoSzam = olvasoSzam;
    }

    public int getPenz() {
        return penz;
    }

    public void setPenz(int penz) {
        this.penz = penz;
    }

    public void konyvVetel(String cim) {
        konyvek.put(cim, true);
    }

    public int konyvMennyiseg() {
        return konyvek.size();
    }

    public void fizet(int osszeg) {
        if(penz < osszeg) {
            throw new SajatException("Nincs eleg penz!");
        } else {
            penz -= osszeg;
        }
    }

    public void konyvekBeolvasasa(String filename) {
        if(filename != null) {
            try(Scanner sc = new Scanner(new File(filename))) {
                while(sc.hasNextLine()) {
                    String[] split = sc.nextLine().split(";");
                    if(split.length == 2) {
                        int tulajdona = Integer.parseInt(split[1]);
                        boolean megvan = false;
                        if(tulajdona == 1) {
                            megvan = true;
                        }
                        konyvek.put(split[0], megvan);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

