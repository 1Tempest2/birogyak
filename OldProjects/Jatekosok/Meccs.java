public class Meccs {
    private final Csapat elsoCsapat;
    private final Csapat masodikCsapat;

    public Meccs(Csapat elsoCsapat, Csapat masodikCsapat) {
        this.elsoCsapat = elsoCsapat;
        this.masodikCsapat = masodikCsapat;
    }

    public Csapat getElsoCsapat() {
        return elsoCsapat;
    }

    public Csapat getMasodikCsapat() {
        return masodikCsapat;
    }

    public Csapat gyoztes() {
        if(elsoCsapat.atlag("magassag") > masodikCsapat.atlag("magassag")) {
            return elsoCsapat;
        } else if(elsoCsapat.atlag("magassag") < masodikCsapat.atlag("magassag")) {
            return masodikCsapat;
        } else {
            if(elsoCsapat.atlag("suly") > masodikCsapat.atlag("suly")) {
                return masodikCsapat;
            } else if(elsoCsapat.atlag("suly") < masodikCsapat.atlag("suly")) {
                return elsoCsapat;
            } else {
                return elsoCsapat;
            }
        }
    }

    @Override
    public String toString() {
        return elsoCsapat.getNeve() + ";" + masodikCsapat.getNeve() + ";" + gyoztes().getNeve();
    }
}