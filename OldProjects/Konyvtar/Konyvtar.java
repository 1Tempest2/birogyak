import java.util.*;

public class Konyvtar {
    private Map<String, KonyvData> konyvtar;
    private Raktar raktar;

    public Konyvtar(Raktar raktar) {
        this.raktar = raktar;
        this.konyvtar = new HashMap<String, KonyvData>();
    }

    private static class KonyvData {
        String cim;
        int oldalszam;
        int darabszam;

        public KonyvData(String cim, int odalszam, int darabszam) {
            this.cim = cim;
            this.oldalszam = odalszam;
            this.darabszam = darabszam;
        }
    }

    public int konyvMennyiseg() {
        int sum = 0;
        for(Map.Entry<String, KonyvData> entry : this.konyvtar.entrySet()) {
            sum += entry.getValue().darabszam;
        }
        return sum;
    }

    public Integer[] lekerdez(String cim) {
        if(cim == null || cim.isEmpty()) {
            return new Integer[]{null, null};
        } else {
            if(konyvtar.containsKey(cim)) {
                KonyvData kd = konyvtar.get(cim);
                return new Integer[]{kd.oldalszam, kd.darabszam};
            } else {
                return new Integer[]{null, null};
            }
        }
    }

    public void konyvetHozzaad(String konyvnev, int konyvoldal, int peldanyszam) {
        if(konyvnev != null && !konyvnev.isEmpty()) {
            KonyvData temp = new KonyvData(konyvnev, konyvoldal, peldanyszam);
            if(konyvtar.containsKey(temp.cim)) {
                KonyvData kd = konyvtar.get(temp.cim);
                kd.darabszam += temp.darabszam;
                konyvtar.put(temp.cim, kd);
            } else {
                konyvtar.put(temp.cim, temp);
            }
        }
    }

    public List<String> torol(int oldalszam) {
        List<String> list = new ArrayList<String>();
        if(!konyvtar.isEmpty()) {
            for(Map.Entry<String, KonyvData> entry : konyvtar.entrySet()) {
                KonyvData kd = entry.getValue();
                if(kd.oldalszam <= oldalszam) {
                    // athelyez a raktarba ha van hely majd torol
                    for(int i = 0; i < kd.darabszam; i++) {
                        if (raktar.konyvMennyiseg() < 100) {
                            raktar.raktarbaHelyez(kd.cim);
                        }
                    }
                    list.add(kd.cim);
                }
            }
        }
        if(!list.isEmpty()) {
            for(String cim : list) {
                konyvtar.remove(cim);
            }
        }
        return list;
    }

    public String legtobbKonyv() {
        String legtobbKonyvCime = "";
        if(konyvtar.isEmpty() || konyvMennyiseg() == 0) {
            return "Nincs ilyen!";
        } else {
            int maxa = Integer.MIN_VALUE;

            for(Map.Entry<String, KonyvData> entry : konyvtar.entrySet()) {
                KonyvData kd = entry.getValue();
                if(kd.darabszam > maxa) {
                    maxa = kd.darabszam;
                   legtobbKonyvCime = kd.cim;
                }
            }
        }
        return legtobbKonyvCime;
    }

    public List<String> rendez(boolean miSzerint) {
        List<String> rendezettCimek = new ArrayList<String>();
        for(Map.Entry<String, KonyvData> entry : konyvtar.entrySet()) {
            rendezettCimek.add(entry.getValue().cim);
        }
        if(rendezettCimek.isEmpty()) {
            return rendezettCimek;
        }
        if(miSzerint) {
            rendezettCimek.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    KonyvData kd1 = konyvtar.get(o1);
                    KonyvData kd2 = konyvtar.get(o2);
                    return Integer.compare(kd1.oldalszam, kd2.oldalszam);
                }
            });
        } else {
            rendezettCimek.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
        }
        return rendezettCimek;
    }

    public int elad(Olvaso olvaso, Map<String, Integer> konyvekArai) {
        int darabszam = 0;
        if(olvaso != null ||  konyvekArai != null) {
            for(Map.Entry<String, Integer> entry : konyvekArai.entrySet()) {
                String konyvCim = entry.getKey();
                int mennyibeKerul = entry.getValue();
                if(konyvtar.containsKey(konyvCim)) {
                    KonyvData kd = konyvtar.get(konyvCim);
                    try {
                        if (kd.darabszam > 5 && olvaso.getPenz() >= mennyibeKerul) {
                            darabszam++;
                            olvaso.fizet(mennyibeKerul);
                            olvaso.konyvVetel(konyvCim);
                            kd.darabszam--;
                        }
                    } catch (Exception e) {
                        throw new SajatException("Nincs ilyen!");
                    }
                }
            }
            return darabszam;
        } else {
            return 0;
        }
    }

}
