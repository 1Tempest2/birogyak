
import java.util.*;

public class Termek {
    private String nev;
    private Set<String> alapanyagok = new HashSet<String>();
    public Termek(String nev){
        this.nev = nev;
    }

    public void alapanyagHozzaadasa(String mit){
        alapanyagok.add(mit);
    }

    public String getNev() {
        return nev;
    }

    public Set<String> getAlapanyagok() {
        return alapanyagok;
    }

    @Override
    public boolean equals(Object o) {
        Termek temp = (Termek) o;
        if(this.nev.equals(temp.getNev()) && this.alapanyagok.equals(temp.getAlapanyagok())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(nev, alapanyagok);
    }
}