import java.util.ArrayList;
import java.util.List;

public class Csapat {
    private final Jatek celJatek;
    private final int maxLetszam;
    private List<Jatekos> jatekosok = new ArrayList<>();

    public Csapat(Jatek celJatek, int maxLetszam) {
        this.celJatek = celJatek;
        this.maxLetszam = maxLetszam;
    }

    public void jatekostHozzaad(Jatekos jatekos) {
        if (jatekos == null || jatekos.getKedvenc() == null) {
            throw new IllegalArgumentException("Ezt a jatekost nem lehet a csapathoz adni!");
        }

        if (celJatek == null || !jatekos.getKedvenc().equals(celJatek)) {
            throw new IllegalArgumentException("Ezt a jatekost nem lehet a csapathoz adni!");
        }
        else if (jatekosok.size() >= maxLetszam) {
            throw new IndexOutOfBoundsException("Ide mar nem fer jatekos!");
        } else {
            jatekosok.add(jatekos);
        }
    }
}