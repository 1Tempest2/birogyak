import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Szervezo {
    private List<Jatekos> jatekosok = new ArrayList<>();
    private List<Csapat> csapatok = new ArrayList<>();

    public Szervezo(List<Jatekos> jatekosok, List<Csapat> csapatok) {
        if (jatekosok != null) {
            this.jatekosok = jatekosok;
        }
        if (csapatok != null) {
            this.csapatok = csapatok;
        }
    }

    public Szervezo() {
        this.jatekosok = new ArrayList<>();
        this.csapatok = new ArrayList<>();
    }

    public void addJatekos(Jatekos jatekos) {
        jatekosok.add(jatekos);
    }

    public void addCsapat(Csapat csapat) {
        csapatok.add(csapat);
    }

    public void gyakoroltat(Map<Jatek, Integer> mibolMennyit){
        if (mibolMennyit == null || jatekosok == null || jatekosok.isEmpty()) {
            return;
        }

        for (Jatekos jatekos : jatekosok) {
            if (jatekos == null) continue;

            boolean jatszottLegalabbEggyelSikeresen = false;

            for (Map.Entry<Jatek, Integer> entry : mibolMennyit.entrySet()) {
                Jatek jatek = entry.getKey();
                Integer mennyit = entry.getValue();

                if (jatek == null || mennyit == null) continue;
                try {
                    jatekos.jatszik(jatek, mennyit);
                    jatszottLegalabbEggyelSikeresen = true;
                } catch (NemSzeretiException e) {

                }
            }

            if (!jatszottLegalabbEggyelSikeresen) {
                throw new SecurityException("Az egyik jatekos tul valogatos.");
            }
        }
    }


    public void beoszt() throws Exception {
        if (jatekosok == null || csapatok == null) {
            return;
        }

        for (Jatekos jatekos : jatekosok) {
            if (jatekos == null || jatekos.getKedvenc() == null) {
                continue;
            }

            int kompatibilisCsapatok = 0;
            int marBetelt = 0;
            boolean sikeresHozzaadas = false;

            for (Csapat csapat : csapatok) {
                if (csapat == null) continue;

                try {
                    csapat.jatekostHozzaad(jatekos);
                    sikeresHozzaadas = true;
                    break;
                } catch (IllegalArgumentException e) {
                } catch (IndexOutOfBoundsException e) {
                    kompatibilisCsapatok++;
                    marBetelt++;
                }
            }

            if (!sikeresHozzaadas) {
                if (kompatibilisCsapatok > 0) {
                    throw new Exception("Az egyik jatekosnak " + kompatibilisCsapatok +
                            " csapatot talalt, de mind tele van.");
                } else {
                    throw new Exception("Az egyik jatekosnak nem sikerult megfelelo csapatot talalni.");
                }
            }
        }
    }
}