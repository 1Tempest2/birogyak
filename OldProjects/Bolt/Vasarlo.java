import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Vasarlo implements kozosInterface {
    public final String nev;
    protected String igazolvanySzam;
    private int penz;

    public Vasarlo(String nev, String igazolvanySzam) {
        this.nev = nev;
        this.igazolvanySzam = igazolvanySzam;
        this.penz = 0;
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
        if(mennyit > penz) {
            throw new SajatException("Nincs eleg penz!");
        } else if(penz >= mennyit) {
            penz -= mennyit;
        }
    }

    public void vagyontKiszamit(String filename) {
        int sum = 0;
        try(Scanner sc = new Scanner(new File(filename))){
            while(sc.hasNextLine()) {
                String[] split =  sc.nextLine().split(";");
                if(split[1].equals("BEVETEL")) {
                    sum += Integer.parseInt(split[2]);
                } else {
                    sum -= Integer.parseInt(split[2]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.penz += sum;
    }
}
