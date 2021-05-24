package Repo;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private String url = "jdbc:sqlite:D:\\\\UBB\\\\ISS\\\\Prototype\\\\teatru.db";

    public List<Spectacol> getSpectacole() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Spectacol> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Spectacole")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    String ora = resultSet.getString("ora");
                    List<Loc> locuri = new ArrayList<>();
                    try (PreparedStatement statement = connection.prepareStatement("select * from Locuri where id_spectacol=?")) {
                        statement.setInt(1, id);
                        ResultSet res = statement.executeQuery();
                        while (res.next()) {
                            int nr = res.getInt("nr");
                            String pozitie = res.getString("pozitie");
                            Float pret = res.getFloat("pret");
                            Status stare;
                            if (res.getString("stare").equals("liber")) {
                                Loc l = new Loc(nr, pozitie, pret);
                                locuri.add(l);
                            }
                        }
                    } catch (SQLException ex) {
                        System.err.println(ex);
                    }
                    Spectacol p = new Spectacol(id, nume, ora, locuri);
                    list.add(p);
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return list;
    }

    public int addSpectator(Spectator spectator) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into Spectatori(nume, tel) values(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, spectator.getNume());
            preparedStatement.setString(2, spectator.getTel());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return -1;
    }

    public void updateLoc(Loc loc) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Locuri set stare=?,id_spectator=? where nr=?")) {
            preparedStatement.setString(1, loc.getStare().toString());
            preparedStatement.setInt(2, loc.getSpectator().getId());
            preparedStatement.setInt(3, loc.getNr());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void deleteSpectacol(int id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Spectacole where id=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public Manager findManager(String name, String password) {
        Connection connection = null;
        Manager m = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement("select * from Manageri where nume=? and password=?")) {
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                m = new Manager(id, name, password);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return m;
    }

    public int addSpectacol(Spectacol spectacol) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into Spectacole(nume, ora) values(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, spectacol.getNume());
            preparedStatement.setString(2, spectacol.getOra());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return id;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return -1;
    }

    public void updateSpectacol(Spectacol spectacol) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Spectacole set nume=?,ora=? where id=?")) {
            preparedStatement.setString(1, spectacol.getNume());
            preparedStatement.setString(2, spectacol.getOra());
            preparedStatement.setInt(3, spectacol.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
