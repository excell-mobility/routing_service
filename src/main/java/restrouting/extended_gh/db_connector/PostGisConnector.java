package restrouting.extended_gh.db_connector;

import java.sql.*;
import java.util.*;
import java.lang.*;

import org.postgis.*;

/**
 * Created by Manal on 16.09.2016.
 */
public class PostGisConnector {

    private static final String dbUrl = "jdbc:postgresql://localhost:5432/vamos";
    private static final String dbUsername = "postgres";
    private static final String dbPassword = "master";


    public static void main(String[] args) {

        Connection connection = null;
        Statement stmt = null;

        try {
    /*
    * Load the JDBC driver and establish a connection.
    */
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);


    /*
    * Add the geometry types to the connection. Note that you
    * must cast the connection to the pgsql-specific connection
    * implementation before calling the addDataType() method.
    */

    /*
    * Create a statement and execute a select query.
    */
            System.out.println("Opened database successfully");

            stmt = connection.createStatement();
            String createTable = "CREATE TABLE sections_geoPoints" +
                    " (id INT , "+
                    " sid_sections character varying(68), "+
                    " \"label\" character varying(68), "+
                    " l numeric, "+
                    "sperr numeric, "+
                    "t numeric, "+
                    "t_rout numeric, "+
                    " geoId numeric , "+
                    "geo_label character varying, " +
                    "latitude float, " +
                    "longitutde float, " +
                    "timpestamp date)";

            stmt.executeUpdate(createTable);
            System.out.println("Table created successfully");

            //insert data to DB
            String insertData = "COPY sections_geopoints " +
                    "(id, sid_sections, " +
                    "\"label\"," +
                    " l, " +
                    "sperr, " +
                    "t, " +
                    "t_rout, " +
                    "geoId ," +
                    "geo_label, " +
                    "latitude, " +
                    "longitutde, " +
                    "timpestamp)\n" +
                    "FROM 'D:\\Arbeit\\workspace\\restrouting\\data-converted\\csv\\nodes_sections_geopointsBig.csv'\n" +
                    "WITH DELIMITER E'\\t'\n" +
                    "CSV HEADER";
            stmt.executeUpdate(insertData);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Records created successfully");

    }

    private static void createTable(){

    }

}
