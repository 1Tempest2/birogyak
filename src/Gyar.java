import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Gyar implements Gyarto {
    private List<Gyartosor> gyartosorok;
    private Map<Termek, Gyartosor> indexMap;
    private List<Megrendeles> megrendelesek;

    @Override
    public boolean gyart() {
        if (gyartosorok.isEmpty() || megrendelesek.isEmpty()) {
            return false;
        }

        boolean sikerult = termekGyartas();

        for (Megrendeles megrendeles : megrendelesek) {
            megrendeles.priority++;
        }

        sortMegrendelesek();

        return sikerult;
    }

    @Override
    public void alapanyagVasarlas(String filename) {
        if (filename != null) {
            try (Scanner sc = new Scanner(new File(filename))) {
                while (sc.hasNextLine()) {
                    String[] split = sc.nextLine().split(", ");
                    if (split.length == 3) {
                        String alapanyag = split[0];
                        int mennyiseg = Integer.parseInt(split[1]);
                        String gyartosorTermek = split[2];
                        Termek temp = new Termek(gyartosorTermek);

                        for (Gyartosor gyartosor : gyartosorok) {
                            if (gyartosor.getMit().getNev().equals(temp.getNev())) {
                                gyartosor.alapanyagVasarlas(alapanyag, mennyiseg);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class Megrendeles {
        private Termek termek;
        private int priority;

        public Megrendeles(Termek termek, int priority) {
            this.termek = termek;
            this.priority = priority;
        }
    }

    public Gyar() {
        gyartosorok = new ArrayList<>();
        megrendelesek = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    public Integer[] lekerdez(Termek termek) {
        if (termek == null) {
            return new Integer[]{null, null};
        } else {
            for (int i = 0; i < megrendelesek.size(); i++) {
                if (termek.getNev().equals(megrendelesek.get(i).termek.getNev())) {
                    return new Integer[]{i, megrendelesek.get(i).priority};
                }
            }
        }
        return new Integer[]{null, null};
    }

    public void megrendelestFogad(Termek termek, int priority) {
        if (termek != null) {
            megrendelesek.add(new Megrendeles(termek, priority));
            sortMegrendelesek();
        }
    }

    private void sortMegrendelesek() {
        megrendelesek.sort(new Comparator<Megrendeles>() {
            @Override
            public int compare(Megrendeles o1, Megrendeles o2) {
                int priorityCompare = Integer.compare(o2.priority, o1.priority);
                if (priorityCompare != 0) {
                    return priorityCompare;
                }
                return o1.termek.getNev().compareTo(o2.termek.getNev());
            }
        });
    }

    public void hozzadGyartoSor(Gyartosor gyartosor) {
        if (gyartosor != null) {
            gyartosorok.add(gyartosor);
            indexMap.put(gyartosor.getMit(), gyartosor);
        }
    }

    public boolean termekGyartas() {
        if (megrendelesek.isEmpty()) {
            return false;
        }

        for (int i = 0; i < megrendelesek.size(); i++) {
            Megrendeles megrendeles = megrendelesek.get(i);
            Termek temp = megrendeles.termek;

            boolean gyartosorExists = false;
            Gyartosor megfeleloGyartosor = null;

            for (Gyartosor gyartosor : gyartosorok) {
                if (gyartosor.getMit().getNev().equals(temp.getNev())) {
                    gyartosorExists = true;
                    megfeleloGyartosor = gyartosor;
                    break;
                }
            }

            if (!gyartosorExists) {
                megrendeles.priority = 0;
                continue;
            }

            try {
                boolean sikerult = megfeleloGyartosor.gyart();
                if (!sikerult) {
                    megrendeles.priority = 0;
                    continue;
                } else {
                    megrendelesek.remove(i);
                    return true;
                }
            } catch (SajatException e) {
                megrendeles.priority = 0;
                continue;
            }
        }
        return false;
    }
}