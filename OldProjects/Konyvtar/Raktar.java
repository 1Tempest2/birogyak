
import java.util.*;

public class Raktar implements kozosInterface{
    private final Map<String, Integer> konyvek = new HashMap<>();

    public void konyvVetel(String cim) {
        if (!this.konyvek.containsKey(cim)) {
            throw new SajatException("A raktar nem tartalmaz ilyen konyvet!");
        }

        this.konyvek.remove(cim);
    }

    public int konyvMennyiseg() {
        int sum = 0;
        for(Map.Entry<String, Integer> entry : this.konyvek.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public List<String> getKonyvek() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : this.konyvek.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    public void raktarbaHelyez(String konyv) {
        if(this.konyvek.containsKey(konyv)) {
            konyvek.put(konyv, this.konyvek.get(konyv) + 1);
        } else {
            konyvek.put(konyv, 1);
        }
    }

}

