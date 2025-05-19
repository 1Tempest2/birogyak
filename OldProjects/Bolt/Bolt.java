import java.util.*;

public class Bolt {
    private final String tipus;
    private Map<String, Integer> aruAr;
    private Map<String, Integer> aruKeszlet;

    public Bolt(boolean boltTipus) {
        if(boltTipus) {
            this.tipus = "nagykereskedes";
        } else {
            this.tipus = "kiskereskedes";
        }
        this.aruAr = new HashMap<>();
        this.aruKeszlet = new HashMap<>();
    }

    public Integer[] lekerdez(String termekNev) {
        if(termekNev != null) {
            if(!aruAr.containsKey(termekNev)) {
                return new Integer[]{null, null};
            } else {
                return new Integer[]{aruAr.get(termekNev), aruKeszlet.get(termekNev)};
            }
        }
        else return new Integer[]{null, null};
    }

    public void szallitmany(String termekNev, int mennyiseg) {
        if(termekNev != null) {
            if(!aruAr.containsKey(termekNev)) {
                aruAr.put(termekNev, 1000);
                aruKeszlet.put(termekNev, mennyiseg);
            } else {
                aruKeszlet.put(termekNev, aruKeszlet.get(termekNev)+mennyiseg);
            }
        }
    }

    public void aratBeallit(String termekNev, int minimum, int ajanlott) {
        if(termekNev != null) {
            if(tipus.equals("nagykereskedes")) {
                if(aruAr.containsKey(termekNev)) {
                    aruAr.put(termekNev, minimum);
                }
            } else if (tipus.equals("kiskereskedes")) {
                if(aruAr.containsKey(termekNev)) {
                    aruAr.put(termekNev, ajanlott);
                }
            }
        }
    }

    public String[] termekek(boolean ABC) {
        List<String> termekNevek = new ArrayList<>(aruAr.keySet());

        Comparator<String> comparatorABC = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        };

        Comparator<String> comparatorAr = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(aruAr.get(a), aruAr.get(b));
            }
        };

        termekNevek.sort(ABC ? comparatorAr : comparatorABC);

        return termekNevek.toArray(new String[0]);
    }

    public boolean vasarol(Vasarlo vasarlo, Map<String, Integer> bevasarloLista) {
        if (vasarlo == null || bevasarloLista == null) {
            return false;
        }

        Map<String, Integer> vasarolhato = new HashMap<>();
        boolean mindenMegvan = true;
        int osszAr = 0;

        for (Map.Entry<String, Integer> entry : bevasarloLista.entrySet()) {
            String nev = entry.getKey();
            int kivantMenny = entry.getValue();

            if (!aruKeszlet.containsKey(nev) || !aruAr.containsKey(nev)) {
                mindenMegvan = false;
                continue;
            }

            int keszleten = aruKeszlet.get(nev);
            int valosMenny = Math.min(keszleten, kivantMenny);

            if (valosMenny < kivantMenny) {
                mindenMegvan = false;
            }

            if (valosMenny > 0) {
                vasarolhato.put(nev, valosMenny);
                osszAr += valosMenny * aruAr.get(nev);
            }
        }

        if (vasarlo.getPenz() < osszAr) {
            return false;
        }

        for (Map.Entry<String, Integer> entry : vasarolhato.entrySet()) {
            String nev = entry.getKey();
            int menny = entry.getValue();
            aruKeszlet.put(nev, aruKeszlet.get(nev) - menny);
        }

        vasarlo.penztKolt(osszAr);
        return mindenMegvan;
    }
}
