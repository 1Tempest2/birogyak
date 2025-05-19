
import java.util.Map;

/**
 * Hallgato osztaly
 */
public class Hallgato implements Comparable<Hallgato> {
    /**
     * A hallgato keresztneve, a peldaban csak egy darab lehet.
     */
    private String keresztNev;
    /**
     * A hallgato csaladneve.
     */
    private String csaladNev;
    /**
     * A hallgato Neptun kodja.
     */
    private String neptun;

    /**
     * Hallgatoi teljesitesek az alabbi formatumban:
     * - a kulcs a tantargy neve, a hozza tartozo ertel pedig a hallgato osztalyzata
     * - Pelda kulcs: "Programozas 1.", ertek 5 (teljesitesek.put("Programozas 1.", 5))
     */
    private Map<String, Integer> teljesitesek;

    public Hallgato(String keresztNev, String csaladNev, String neptun, Map<String, Integer> teljesitesek) {
        this.keresztNev = keresztNev;
        this.csaladNev = csaladNev;
        this.neptun = neptun;
        this.teljesitesek = teljesitesek;
    }

    @Override
    public String toString() {
        return "Hallgato{" +
                "keresztNev='" + keresztNev + '\'' +
                ", csaladNev='" + csaladNev + '\'' +
                ", neptun='" + neptun + '\'' +
                ", teljesitesek=" + teljesitesek +
                '}';
    }

    /**
     * A teljesitesek lekerese
     *
     * @return
     */
    public Map<String, Integer> getTeljesitesek() {
        return teljesitesek;
    }

    public int maxJegy() {
        int maxa = Integer.MIN_VALUE;
        for(Map.Entry<String, Integer> entry : teljesitesek.entrySet()) {
            if(entry.getValue() > maxa) {
                maxa = entry.getValue();
            }
        }
        return maxa;
    }

    public String getKeresztNev() {
        return keresztNev;
    }

    public void setKeresztNev(String keresztNev) {
        this.keresztNev = keresztNev;
    }

    public String getCsaladNev() {
        return csaladNev;
    }

    public void setCsaladNev(String csaladNev) {
        this.csaladNev = csaladNev;
    }

    public String getNeptun() {
        return neptun;
    }

    public void setNeptun(String neptun) {
        this.neptun = neptun;
    }

    public void setTeljesitesek(Map<String, Integer> teljesitesek) {
        this.teljesitesek = teljesitesek;
    }

    @Override
    public int compareTo(Hallgato o) {
        int integerCompare = Integer.compare(o.maxJegy(), this.maxJegy());
        if(integerCompare == 0) {
            return this.neptun.compareTo(o.neptun);
        } else {
            return integerCompare;
        }
    }
}
