package Model;

import java.util.*;

public class Spectator {

    public int id;
    public String nume;
    public String tel;
    public Set<Loc> rezervari = new HashSet<>();

    public Spectator(int id, String nume, String tel) {
        this.id = id;
        this.nume = nume;
        this.tel = tel;
    }

    public void rezerva(Loc l) {
        rezervari.add(l);
        l.setSpectator(this);
        l.setStare(Status.rezervat);
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getTel() {
        return tel;
    }

    public Set<Loc> getRezervari() {
        return rezervari;
    }

    public void setId(int id) {
        this.id = id;
    }
}