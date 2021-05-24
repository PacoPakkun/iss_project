package Model;

import java.util.*;

public class Manager {

    public int id;
    public String nume;
    public String password;
    public Set<Spectacol> spectacole = new HashSet<>();

    public String getNume() {
        return nume;
    }

    public String getPassword() {
        return password;
    }

    public Manager(int id, String nume, String password) {
        this.id = id;
        this.nume = nume;
        this.password = password;
    }

}