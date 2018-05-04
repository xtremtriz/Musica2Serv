/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musica2;

/**
 *
 * @author Ariana
 */
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.util.Date;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancionesADjdbc{
    private BufferedReader archivoIn;
    private PrintWriter    archivoOut;
    
    private Connection conexion;
    private Statement  statement;
    private Statement  statement2;
    
    private CancionesDP cancionesdp = new CancionesDP();

    // static Date date;
    // static String strDate = "";
    // static String strTime = "";

    
    public CancionesADjdbc()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/musica?user=root");
            System.out.println("Conexion exitosa con la BD...");
        }
        catch(ClassNotFoundException cnfe){
            System.out.println("Error 1: "+cnfe);
        }
        catch(InstantiationException ie){
            System.out.println("Error 2: "+ie);
        }
        catch(IllegalAccessException iae){
            System.out.println("Error 3: "+iae);
        }
        catch(SQLException sqle){
            System.out.println("Error 4: "+sqle);
        }
    }
    

    public String consultarCatalogo() {
        String datos = "";
        ResultSet tr;
        //String cta, nombre, tipo;
        //int saldo;

        String query = "SELECT * FROM Canciones";

        try {
            // 1. Abrir el archivo de datos o BD
            //archivoIn = new BufferedReader(new FileReader("Clientes.txt"));
            statement = conexion.createStatement();

            // Ejecutar Query
            tr = statement.executeQuery(query);

            // 2. Procesar los datos en el archivo

            cancionesdp = new CancionesDP();
            while (tr.next()) {

                cancionesdp.setNsong(tr.getString("nsong"));
                cancionesdp.setNombre(tr.getString("nombre"));
                cancionesdp.setCompositor(tr.getString(3));
                cancionesdp.setGrupoartist(tr.getString(4));

                datos = datos + cancionesdp.toString() + "*";
            }

            // 3. Cerrar la conexión
            //archivoIn.close();
            statement.close();

            // System.out.println(query);
        } catch (SQLException sqle) {
            datos = "Error: " + sqle;
        }

        return datos;
    }
    
    public String consultarCancion(String cancion){
        String datos="";
        ResultSet tr;
        //String cta, nombre, tipo;
        //int saldo;
        
        String query = "SELECT * FROM Canciones where nombre='"+cancion+"'";
        
        try{
            // 1. Abrir el archivo de datos o BD
            //archivoIn = new BufferedReader(new FileReader("Clientes.txt"));
            statement = conexion.createStatement();
            
            // Ejecutar Query
            tr = statement.executeQuery(query);
            
            // 2. Procesar los datos en el archivo
            /*while(archivoIn.ready())
             {
             datos = datos + archivoIn.readLine() + "\n";
             }*/
            
            cancionesdp = new CancionesDP();
            if(tr.next()){
                /*cta = tr.getString("nocta");
                 nombre = tr.getString("nombre");
                 tipo = tr.getString(3);
                 saldo = tr.getInt("saldo");
                 */
                //datos = datos + cta+"_"+nombre+"_"+tipo+"_"+saldo+"\n";
                
                cancionesdp.setNsong(tr.getString("nsong"));
                cancionesdp.setNombre(tr.getString("nombre"));
                cancionesdp.setCompositor(tr.getString(3));
                cancionesdp.setGrupoartist(tr.getString(4));
                
                datos = datos + cancionesdp.toString();
            }
            else
                datos = "NO_LOCALIZADO";
            
            // 3. Cerrar el archivo
            //archivoIn.close();
            statement.close();
            
            // System.out.println(query);
        }
        catch(SQLException sqle){
            datos = "Error: "+sqle;
        }
        
        return datos;
    }

    
}