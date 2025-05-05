package com.vsu.repository;

import com.vsu.exception.DBException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        /*try{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/java","sys_admin","");}
        catch(SQLException e){
            throw new DBException(e.getCause());
        }*/

        try {
            Context context = new InitialContext();
            Context envContext = (Context) context.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/postgres");
            return ds.getConnection();
        } catch (RuntimeException | NamingException | SQLException e) {
            throw new DBException(e);
        }
    }
}
