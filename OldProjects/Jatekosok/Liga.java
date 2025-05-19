import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Liga {
    private List<Csapat> csapatok;
    private List<Meccs> meccsek;
    public List<Meccs> osszesMeccsek = new ArrayList<>();

    public Liga(List<Csapat> csapatok, List<Meccs> meccsek) {
        this.csapatok = csapatok;
        this.meccsek = meccsek;
        this.osszesMeccsek = new ArrayList<>();
    }

    public Liga(List<Csapat> csapatok) {
        this.csapatok = csapatok;
        this.meccsek = new ArrayList<>();
        this.osszesMeccsek = new ArrayList<>();
    }

    public Liga(String directoryName) {
        File folder = new File(directoryName);
        File[] csvFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        List<Csapat> csapatok = new ArrayList<>();
        if (csvFiles != null) {
            for (File file : csvFiles) {
                try (Scanner sc = new Scanner(file)) {
                    String fileName = file.getName();
                    Csapat temp = new Csapat(fileName.substring(0, fileName.lastIndexOf('.')));
                    temp.beolvas(file);
                    csapatok.add(temp);
                } catch (FileNotFoundException e) {
                    System.err.println("File not found: " + file.getName());
                }
            }
        }
        this.csapatok = csapatok;
        this.meccsek = new ArrayList<>();
        this.osszesMeccsek = new ArrayList<>();
    }

    public List<Csapat> getCsapatok() {
        return csapatok;
    }

    public void setCsapatok(List<Csapat> csapatok) {
        this.csapatok = csapatok;
    }

    public List<Meccs> getMeccsek() {
        return meccsek;
    }

    public List<Csapat> hianyosCsapatok() {
        List<Csapat> missingTeams = new ArrayList<>();
        for(Csapat csapat : csapatok) {
            if(!csapat.hianyzoPoziciok().isEmpty()) {
                missingTeams.add(csapat);
            }
        }
        return missingTeams;
    }

    public int csapatokKizarasa() {
        List<Csapat> missingTeams = hianyosCsapatok();
        csapatok.removeAll(missingTeams);
        return missingTeams.size();
    }

    public Csapat jatek() {
        List<Csapat> teams = new ArrayList<>(csapatok);
        meccsek.clear();

        while (teams.size() > 1) {
            int numMatches = teams.size() / 2;
            List<Csapat> nextRound = new ArrayList<>();

            for (int i = 0; i < numMatches; i++) {
                Csapat team1 = teams.get(i*2);
                Csapat team2 = teams.get(i*2+1);
                Meccs temp = new Meccs(team1, team2);
                meccsek.add(temp);
                osszesMeccsek.add(temp);
                Csapat winner = temp.gyoztes();
                nextRound.add(winner);
            }

            if (teams.size() % 2 == 1) {
                nextRound.add(teams.get(teams.size() - 1));
            }

            teams = nextRound;
        }

        return teams.isEmpty() ? null : teams.get(0);
    }

    public void eredmenyTabla(String fajlNev) {
        try (FileWriter fw = new FileWriter(fajlNev + ".csv")) {
            fw.write("ElsoCsapat;MasodikCsapat;GyoztesCsapat\n");
            for (Meccs m : osszesMeccsek) {
                Csapat elso = m.getElsoCsapat();
                Csapat masodik = m.getMasodikCsapat();
                Csapat gyoztes = m.gyoztes();
                fw.write(elso.getNeve() + ";" + masodik.getNeve() + ";" + gyoztes.getNeve() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}