import java.util.HashMap;
import java.util.Map;

public class Gyartosor implements Gyarto{
    private final String azonosito;
    private Termek mit;
    private Map<String, Integer> alapanyagok;

    public Gyartosor(String azonosito, Termek mit) {
        this.azonosito = azonosito;
        this.mit = mit;
        this.alapanyagok = new HashMap<String, Integer>();
    }

    @Override
    public boolean gyart() {
        for(String alapanyag : mit.getAlapanyagok()) {
            if(alapanyagok.containsKey(alapanyag)) {
                if(alapanyagok.get(alapanyag) < 1) {
                    return false;
                }
            } else {
                throw new SajatException("A gyartosor nem kepes legyartani a termeket.");
            }
        }
        for(String alapanyag : mit.getAlapanyagok()) {
            alapanyagok.put(alapanyag, alapanyagok.get(alapanyag) - 1);
        }
        return true;
    }

    @Override
    public void alapanyagVasarlas(String alapanyag) {
        if(alapanyag != null && !alapanyag.isEmpty()) {
            if(alapanyagok.containsKey(alapanyag)) {
                alapanyagok.put(alapanyag, alapanyagok.get(alapanyag) + 5);
            } else {
                alapanyagok.put(alapanyag, 5);
            }
        }
    }

    public void alapanyagVasarlas(String alapanyag, int mennyit) {
        if(alapanyag != null && !alapanyag.isEmpty()) {
            if(alapanyagok.containsKey(alapanyag)) {
                alapanyagok.put(alapanyag, alapanyagok.get(alapanyag) + mennyit);
            } else {
                alapanyagok.put(alapanyag, mennyit);
            }
        }
    }

    public String getAzonosito() {
        return azonosito;
    }

    public Termek getMit() {
        return mit;
    }

    public void setMit(Termek mit) {
        this.mit = mit;
    }

    public Map<String, Integer> getAlapanyagok() {
        return alapanyagok;
    }

    public void setAlapanyagok(Map<String, Integer> alapanyagok) {
        this.alapanyagok = alapanyagok;
    }
}


