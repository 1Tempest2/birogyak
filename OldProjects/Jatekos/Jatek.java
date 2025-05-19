import java.util.Arrays;
import java.util.Objects;

public class Jatek {
    private String nev;
    private String[] genre;

    public Jatek(String nev) {
        this.nev = nev;
        this.genre = new String[3];
    }

    public Jatek(String nev, String... genre) {
        this.nev = nev;
        this.genre = new String[3];

        int count = 0;
        if (genre != null) {
            for (int i = 0; i < genre.length && i < 3; i++) {
                this.genre[i] = genre[i];
                count++;
            }
        }
    }

    public void addGenre(String name) {
        int currLength = 0;
        for (int i = 0; i < this.genre.length; i++) {
            if (this.genre[i] != null) {
                currLength++;
            }
        }

        if (currLength != 3) {
            for (int i = 0; i < this.genre.length; i++) {
                if (this.genre[i] == null) {
                    this.genre[i] = name;
                    break;
                }
            }
        }
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Jatek jatek)) return false;
        return Objects.equals(getNev(), jatek.getNev()) && Objects.deepEquals(getGenre(), jatek.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNev(), Arrays.hashCode(getGenre()));
    }
}