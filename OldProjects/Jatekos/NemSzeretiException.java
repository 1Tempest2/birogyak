public class NemSzeretiException extends Exception {
    private final Jatek melyikJatekot;

    public NemSzeretiException(Jatek melyikJatekot) {
        super("A jatekos nem szereti a "+(melyikJatekot != null ? melyikJatekot.getNev() : "null")+" jatekot.");
        this.melyikJatekot = melyikJatekot;
    }

    public Jatek getMelyikJatekot() {
        return melyikJatekot;
    }
}