import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OsztalyozasiMod {
    private final int[] erdemjegyek;

    public OsztalyozasiMod(int kettes, int harmas, int negyes, int otos) {
        this.erdemjegyek = new int[]{kettes, harmas, negyes, otos};
    }

    public OsztalyozasiMod() {
        this.erdemjegyek = new int[4];
    }

    public int erdemjegy(int pontszam) {
        if(pontszam < erdemjegyek[0]) {
            return 1;
        } else if (erdemjegyek[0] <= pontszam && pontszam < erdemjegyek[1]) {
            return 2;
        } else if (erdemjegyek[1] <= pontszam && pontszam < erdemjegyek[2]) {
            return 3;
        } else if (erdemjegyek[2] <= pontszam && pontszam < erdemjegyek[3]) {
            return 4;
        } else if(pontszam >= erdemjegyek[3]) {
            return 5;
        } else {
            return 2;
        }
    }

    public static OsztalyozasiMod beolvas(String nev) {
        List<Integer> osztalyzatok = new ArrayList<>();
        try(Scanner sc = new Scanner(new File(nev))) {
            while(sc.hasNext()) {
                osztalyzatok.add(Integer.valueOf(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new OsztalyozasiMod(osztalyzatok.get(0), osztalyzatok.get(1), osztalyzatok.get(2), osztalyzatok.get(3));
    }
}
