import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Vasarlo implements KozosInteface {
    public final String nev;
    protected String igazolvanySzam;
    private int penz;

    public Vasarlo(String nev, String igazolvanySzam) {
        this.nev = nev;
        this.igazolvanySzam = igazolvanySzam;
        this.penz = 0;
    }

    public String getNev() {
        return nev;
    }

    public String getIgazolvanySzam() {
        return igazolvanySzam;
    }

    public void setIgazolvanySzam(String igazolvanySzam) {
        this.igazolvanySzam = igazolvanySzam;
    }

    public int getPenz() {
        return penz;
    }

    public void setPenz(int penz) {
        this.penz = penz;
    }

    public void penztKolt(int mennyit) {
        if(this.penz < mennyit) {
            throw new SajatException("Nincs eleg penz!");
        } else {
            this.penz -= mennyit;
        }
    }

    public void vagyontKiszamit(String filename) {
        if(filename != null) {
            int sum = 0;
            try(Scanner sc = new Scanner(new File(filename))) {
                while(sc.hasNextLine()) {
                    String[] split = sc.nextLine().split(";");
                    String tipus = split[1];
                    int osszeg = Integer.parseInt(split[2]);
                    if(tipus.equals("BEVETEL")) {
                        sum += osszeg;
                    } else {
                        sum -= osszeg;
                    }
                }
                this.penz += sum;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
