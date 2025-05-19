
import java.util.*;

public class Vallalkozas implements kozosInterface {
    private final List<Integer> befektetesek = new ArrayList<>();

    public int getPenz() {
        int osszeg = 0;

        for (int penz: befektetesek) {
            osszeg += penz;
        }

        return osszeg;
    }

    public void penztKolt(int mennyit) {
        if (getPenz() < mennyit) {
            throw new SajatException("Nincs eleg penz!");
        }

        Iterator<Integer> iterator = befektetesek.iterator();
        while (iterator.hasNext() && mennyit > 0) {
            int elem = iterator.next();
            mennyit -= elem;
            iterator.remove();
        }

        befektetesek.add(-mennyit);
    }
}
