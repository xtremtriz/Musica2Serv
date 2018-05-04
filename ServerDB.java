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
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerDB extends JFrame{
    private JTextArea taDatos;
    private JPanel panel;
    
    private ServerSocket ServerDB;
    private Socket		 socket;
    
    private BufferedReader bufferEntrada;
    private PrintWriter    bufferSalida;
    
    private CancionesADjdbc cancionad  = new CancionesADjdbc();

    public ServerDB()
    {
        super("Message ServerDB");
        
        // 1. Crear objetos de los atributos
        taDatos = new JTextArea();
        panel   = new JPanel();
        
        // 2. Definir Layout para el panel
        panel.setLayout(new GridLayout(1,1));
        
        // 3. Poner los objetos en el panel
        panel.add(new JScrollPane(taDatos));
        
        // 4. Adicionar el panel al JFrame y lo hacemos visible
        add(panel);
        setSize(400,300);
        setVisible(true);
    }
    
    private String recibirDatos()
    {
        String datos="";
        
        try
        {
            datos = bufferEntrada.readLine();
        }
        catch(IOException ioe)
        {
            System.out.println("Error: "+ioe);
        }
        
        return datos;
    }
    
    private void enviarDatos(String datos)
    {
        bufferSalida.println(datos);
        bufferSalida.flush();
    }
    
    private void cerrarConexion()
    {
        try
        {
            bufferEntrada.close();
            bufferSalida.close();
            socket.close();
        }
        catch(IOException ioe)
        {
            System.out.println("Error: "+ioe);
        }
    }
    
    private void iniciarServer()
    {
        String datos = "";
        String transaccion ="";
        
        try
        {
            // 1. Inicializar el Serve y ponerlo en estado listen()
            ServerDB = new ServerSocket(5005,5);
            
            while(true)
            {
                taDatos.append("\nServer Banco: estado listen()\n Esperando peticiones de conexion...\n");
                
                // 2. Al escuchar una peticion de conexion hacer el accept()
                socket = ServerDB.accept();
                
                // taDatos.append("Message ServerDB: Se recibio peticion de conexxion\n se hizo el accept()...\n");
                
                // 3. Recibir la transacción a realizar
                bufferEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferSalida  = new PrintWriter(socket.getOutputStream());
                bufferSalida.flush();
                
                // 4. Recibir datos o mensaje del cliente
                transaccion = recibirDatos();
                
                taDatos.append("Mensaje recibido: " + transaccion);
                
                // 5. Preparar mensaje a enviar al cliente
                System.out.println("transaccion: " + transaccion);
                
                if(transaccion.equals("consultarCancion"))
                {
                    // 5.1 Obtener el número de cta
                    String cancion = recibirDatos();

                    // 5.2 Realizar transacción con la DB
                    datos = cancionad.consultarCancion(cancion);

                    // 5.3 Enviar datosde la consulta al cliente
                    enviarDatos(datos);
                }
                
                if (transaccion.equals("consultarCatalogo")) {
                    // 5.4 Obtener el número de cta
                    datos = cancionad.consultarCatalogo();

                    // 5.5 Enviar datosde la consulta al cliente
                    enviarDatos(datos);
                }


                taDatos.append("Transacción realizada: " + transaccion);

                // 6. Cerrar conexion
                cerrarConexion();

                // 7. Desplegar transacción y datos enviados al cliente

                taDatos.append("Transaccion realizada: "+transaccion);
                System.out.println("Datos: " + transaccion);
            }
            
        }
        catch(IOException ioe)
        {
            System.out.println("Error: "+ioe);
        }
    }
    
    public static void main(String args[])
    {
        ServerDB serverApp = new ServerDB();
        
        serverApp.iniciarServer();
    }
}
