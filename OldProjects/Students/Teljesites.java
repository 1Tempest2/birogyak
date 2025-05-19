public class Teljesites {
    private Kurzus kurzus;
    private int pontszam;

    public Teljesites(Kurzus kurzus, int pontszam) {
        this.kurzus = kurzus;
        this.pontszam = pontszam;
    }


    public Kurzus getKurzus() {
        return kurzus;
    }

    public void setKurzus(Kurzus kurzus) {
        this.kurzus = kurzus;
    }

    public int getPontszam() {
        return pontszam;
    }

    public void setPontszam(int pontszam) {
        this.pontszam = pontszam;
    }

    public int erdemjegy() {
        return kurzus.getOsztalyozasiMod().erdemjegy(pontszam);
    }
}
