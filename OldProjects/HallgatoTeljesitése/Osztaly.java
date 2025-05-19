import java.util.*;

public class Osztaly {
    public static Collection<Hallgato> rendez(Hallgato[] hallgatok) {
        if(hallgatok == null || hallgatok.length == 0) {
            return new ArrayList<>();
        }
        List<Hallgato> sortedHallgatok = new ArrayList<Hallgato>(Arrays.asList(hallgatok));
        sortedHallgatok.sort(new Comparator<Hallgato>() {
            @Override
            public int compare(Hallgato o1, Hallgato o2) {
                int integerCompare = Integer.compare(o2.maxJegy(), o1.maxJegy());
                if(integerCompare != 0) {
                    return integerCompare;
                } else {
                    return o1.getNeptun().compareTo(o2.getNeptun());
                }
            }
        });
        return sortedHallgatok;
    }

    public static void teljesitesNelkuliekTorlese(List<Hallgato> hallgatok) {
        List<Hallgato> toDelte = new ArrayList<>();
        if(hallgatok == null || hallgatok.size() == 0) {
            return;
        } else {
            for(Hallgato hallgato : hallgatok) {
                if(hallgato.getTeljesitesek() == null || hallgato.getTeljesitesek().size() == 0) {
                    toDelte.add(hallgato);
                }
            }
        }

        for(Hallgato hallgato : toDelte) {
            hallgatok.remove(hallgato);
        }
    }

    /**
     * A main metodusban kedved szerint kiprobalhatod a programod, tesztelheted, amihez felhasznalhatod
     * a mar adott hallgatokat, de ujakat is keszithetsz.
     * A main atirasa egyaltalan nem befolyasolja a kiertekeles menetet!
     *
     * @param args
     */
    public static void main(String[] args) {
        Hallgato hallgato1 = new Hallgato("Bela", "Kiss", "NGRSPJ", new HashMap<String, Integer>(){{put("Kalkulus",5); put("Diszkret matek",4); put("Programozas alapjai",3);}});
        Hallgato hallgato2 = new Hallgato("Bela", "Abraham", "XGRSPJ", new HashMap<String, Integer>(){{put("Kalkulus",4); put("Diszkret matek",4); put("Programozas alapjai",4);}});
        Hallgato hallgato3 = new Hallgato("Inez", "Mikola", "JRRAGS", new HashMap<String, Integer>(){{put("Algoritmusok",5); put("Diszkret matek",4);}});

        Hallgato[] hallgatok = new Hallgato[]{hallgato1, hallgato2, hallgato3};
        rendez(hallgatok);
        for (Hallgato adottHallgato : hallgatok) {
            System.out.println(adottHallgato); // Eredmenyek kiirasa
        }
    }
}
