package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement readSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteSt;
    private PreparedStatement listAllSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection
                .prepareStatement("INSERT INTO clients (name) VALUES ?");
        readSt = connection
                .prepareStatement("SELECT id, name FROM people WHERE id = ?");
        updateSt = connection
                .prepareStatement("UPDATE clients SET name = ? WHERE id = ?");
        deleteSt = connection
                .prepareStatement("DELETE FROM clients WHERE id = ?");
        listAllSt = connection
                .prepareStatement("SELECT id, name FROM clients");

    }

    public long create(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("No empty name allowed");
        }

        createSt.setString(1, name);
        createSt.executeUpdate();

        ResultSet rs = createSt.getGeneratedKeys();
        if(rs.next()){
            return rs.getLong(1);
        }
        else{
            throw new SQLException("Unsuccessful client creation");
        }
    }

    public String getById(long id) throws SQLException {
        readSt.setLong(1, id);
        ResultSet rs = readSt.executeQuery();

        if(rs.next()){
            return rs.getString("name");
        }
        else return null;
    }

    public void setName(long id, String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("No empty name allowed");
        }

        updateSt.setString(1, name);
        updateSt.setLong(2, id);

        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteSt.setLong(1, id);
        deleteSt.executeUpdate();
    }

    public List<Client> listAll() throws SQLException {
        List<Client> clients = new ArrayList<>();

        ResultSet rs = listAllSt.executeQuery();

        while(rs.next()){
            long id = rs.getLong("id");
            String name = rs.getString("name");

            Client client = new Client(id, name);
            clients.add(client);
        }
        return clients;
    }


}
