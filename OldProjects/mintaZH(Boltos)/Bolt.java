import java.util.*;

public class Bolt {
    private String tipus;
    private Map<String, Termek> termekMap = new HashMap<>();
    private static class Termek {
        String nev;
        int ar;
        int darabszam;

        public Termek(String nev, int ar, int darabszam) {
            this.nev = nev;
            this.ar = ar;
            this.darabszam = darabszam;
        }
    }

    public Bolt(boolean milyen) {
        this.tipus = milyen ? "nagykereskedes" : "kiskereskedes";
    }

    public Integer[] lekerdez(String nev) {
        if(nev != null && !nev.isEmpty()) {
            if(termekMap.containsKey(nev)) {
                Termek temp = termekMap.get(nev);
                return new Integer[]{temp.ar, temp.darabszam};
            } else {
                return new Integer[]{null, null};
            }
        } else {
            return new Integer[]{null, null};
        }
    }

    public void szallitmany(String nev, int mennyiseg) {
        if(nev != null && !nev.isEmpty()) {
            if(termekMap.containsKey(nev)) {
                Termek temp = termekMap.get(nev);
                temp.darabszam += mennyiseg;
                termekMap.put(nev, temp);
            } else {
                Termek temp = new Termek(nev, 1000, mennyiseg);
                termekMap.put(nev, temp);
            }
        }
    }

    public void aratBeallit(String nev, int minimumAr, int ajanlottAr) {
        if(nev != null && !nev.isEmpty()) {
            if(termekMap.containsKey(nev)) {
                Termek temp = termekMap.get(nev);
                if(this.tipus.equals("nagykereskedes")) {
                    temp.ar = minimumAr;
                    termekMap.put(nev, temp);
                } else {
                    temp.ar = ajanlottAr;
                    termekMap.put(nev, temp);
                }
            }
        }
    }

    public String[] termekek(boolean miSzerint) {
        List<String> list = new ArrayList<>(termekMap.keySet());
        if(miSzerint) {
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return Integer.compare(termekMap.get(o1).ar, termekMap.get(o2).ar);
                }
            });
        } else {
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }
        return list.toArray(new String[0]);
    }

    public boolean vasarol(Vasarlo vasarlo, Map<String, Integer> bevasarloLista) {
        if(vasarlo == null || bevasarloLista == null) {
            return false;
        } else {
            boolean sikerult = true;
            Map<String, Integer> megvasarolhatoMap = new HashMap<>();
            for(Map.Entry<String, Integer> entry : bevasarloLista.entrySet()) {
                String kivantTermek = entry.getKey();
                int mennyiseg = entry.getValue();

                if(termekMap.containsKey(kivantTermek)) {
                    Termek temp = termekMap.get(kivantTermek);
                    if(temp.darabszam < mennyiseg) {
                        sikerult = false;
                        megvasarolhatoMap.put(kivantTermek, temp.darabszam);
                    } else {
                        megvasarolhatoMap.put(kivantTermek, mennyiseg);
                    }
                } else {
                    sikerult = false;
                }
            }

            //vasarlas
            int osszKoltseg = 0;
            for(Map.Entry<String, Integer> entry : megvasarolhatoMap.entrySet()) {
                osszKoltseg += entry.getValue() * termekMap.get(entry.getKey()).ar;
            }

            if(vasarlo.getPenz() >= osszKoltseg) {
                vasarlo.penztKolt(osszKoltseg);
                for(Map.Entry<String, Integer> entry : megvasarolhatoMap.entrySet()) {
                    String termekNev = entry.getKey();
                    int mennyiseg = entry.getValue();
                    Termek temp = termekMap.get(termekNev);
                    temp.darabszam -= mennyiseg;
                }
                return sikerult;
            } else {
                return false;
            }
        }
    }
}
