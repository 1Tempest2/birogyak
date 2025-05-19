import java.util.*;

public class Jatekos {
    private String nev;
    private Jatek kedvenc;
    private Map<Jatek, Integer> eddigJatszott = new HashMap<>();

    public Jatekos(String nev) {
        this.nev = nev;
        this.kedvenc = null;
    }

    public void jatszik(Jatek mit, int mennyit) throws NemSzeretiException {
        if (this.kedvenc == null) {
            if (eddigJatszott.containsKey(mit)) {
                eddigJatszott.put(mit, eddigJatszott.get(mit) + mennyit);
            } else {
                eddigJatszott.put(mit, mennyit);
            }

            updateKedvenc();
            return;
        }

        boolean akarJatszani = false;
        if (mit.getGenre() != null && kedvenc.getGenre() != null) {
            for (String genre1 : mit.getGenre()) {
                if (genre1 == null) continue;

                for (String genre2 : kedvenc.getGenre()) {
                    if (genre2 == null) continue;

                    if (genre1.equals(genre2)) {
                        akarJatszani = true;
                        break;
                    }
                }
                if (akarJatszani) break;
            }
        }

        if (!akarJatszani) {
            throw new NemSzeretiException(mit);
        }

        if (eddigJatszott.containsKey(mit)) {
            eddigJatszott.put(mit, eddigJatszott.get(mit) + mennyit);
        } else {
            eddigJatszott.put(mit, mennyit);
        }

        updateKedvenc();
    }

    private void updateKedvenc() {
        Jatek kedvencke = null;
        int maxa = Integer.MIN_VALUE;
        for (Map.Entry<Jatek, Integer> entry : eddigJatszott.entrySet()) {
            if (entry.getValue() > maxa) {
                maxa = entry.getValue();
                kedvencke = entry.getKey();
            }
        }
        this.kedvenc = kedvencke;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Jatek getKedvenc() {
        return kedvenc;
    }

    public void setKedvenc(Jatek kedvenc) {
        this.kedvenc = kedvenc;
    }

    public Map<Jatek, Integer> getEddigJatszott() {
        return eddigJatszott;
    }

    public void setEddigJatszott(Map<Jatek, Integer> eddigJatszott) {
        this.eddigJatszott = eddigJatszott;
    }
}