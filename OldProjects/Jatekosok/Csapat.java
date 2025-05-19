import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Csapat {
    private final String neve;
    private Map<String, List<Jatekos>> jatekosok;

    public Csapat(String neve) {
        this.neve = neve;
        this.jatekosok = new HashMap<String, List<Jatekos>>();
    }

    public String getNeve() {
        return neve;
    }

    public Map<String, List<Jatekos>> getJatekosok() {
        return jatekosok;
    }

    public void setJatekosok(Map<String, List<Jatekos>> jatekosok) {
        this.jatekosok = jatekosok;
    }

    public void beolvas(File fajlnev) {
        try(Scanner sc = new Scanner(fajlnev)) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(";");
                String nev = line[0];
                String poz =  line[1];
                int kor = Integer.valueOf(line[2]);
                int magassag =  Integer.valueOf(line[3]);
                int suly = Integer.valueOf(line[4]);
                Jatekos temp = new Jatekos(nev, kor, magassag, suly);
                if(jatekosok.containsKey(poz)) {
                    jatekosok.get(poz).add(temp);
                } else {
                    ArrayList<Jatekos> liste = new ArrayList<>();
                    liste.add(temp);
                    jatekosok.put(poz, liste);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> hianyzoPoziciok() {
        List<String> positions = Arrays.asList(
                "Starting Pitcher", "First Baseman", "Shortstop", "Third Baseman",
                "Designated Hitter", "Catcher", "Second Baseman",
                "Relief Pitcher", "Outfielder"
        );
        List<String> missingPositions = new ArrayList<>();

        for(String poz : positions) {
            if(!jatekosok.containsKey(poz)) {
                missingPositions.add(poz);
            } else if(jatekosok.get(poz).isEmpty()) {
                missingPositions.add(poz);
            }
        }
        return missingPositions;
    }

    public float atlag(String melyikAtlag) {
        if(!melyikAtlag.equals("magassag") && !melyikAtlag.equals("suly")) {
            return 0;
        } else {
            return calculateAttribute(melyikAtlag);
        }
    }

    private float calculateAttribute(String melyikAtlag) {
        List<Jatekos> temp = new ArrayList<>();
        for(Map.Entry<String, List<Jatekos>> entry : jatekosok.entrySet()) {
            temp.addAll(entry.getValue());
        }
        float sum = 0;
        for(Jatekos j : temp) {
            if(melyikAtlag.equals("magassag")) {
                sum += j.getMagassag();
            } else if(melyikAtlag.equals("suly")) {
                sum += j.getSuly();
            }
        }
        return sum/temp.size();
    }

    @Override
    public String toString() {
        return neve;
    }
}