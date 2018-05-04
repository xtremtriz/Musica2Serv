/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musica2;

/**
 *
 * @author Uriel
 */
import java.util.StringTokenizer;

public class CancionesDP{
    // Atributos de la clase
    private String nsong, nombre, compositor, grupoartist;
    
    // Constructores
    public CancionesDP(){
        this.nsong  = "";
        this.nombre = "";
        this.compositor   = "";
        this.grupoartist = "";
    }
    
    public CancionesDP(String datos){
        StringTokenizer st = new StringTokenizer(datos,"_");
        
        this.nsong  = st.nextToken();
        this.nombre = st.nextToken();
        this.compositor   = st.nextToken();
        this.grupoartist  = st.nextToken();
    }
    
    // Accesors o geter's
    public String getNsong(){
        return this.nsong;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getCompositor(){
        return this.compositor;
    }
    
    public String getGrupoartist(){
        return this.grupoartist;
    }
    
    // Mutators o seter's
    public void setNsong(String song){
        this.nsong = song;
    }
    
    public void setNombre(String name){
        this.nombre = name;
    }
    
    public void setCompositor(String compo){
        this.compositor = compo;
    }
    
    public void setGrupoartist(String artist){
        this.grupoartist = artist;
    }
    
    @Override
    public String toString(){
        System.out.println("Cuenta: "+this.nsong+" Nombre: "+this.nombre+" Tipo: "+this.compositor+" Saldo: "+this.grupoartist);
        return this.nsong+"_"+this.nombre+"_"+this.compositor+"_"+this.grupoartist;
    }
    
    public String toStringSql(){
        return "'"+this.nsong+"','"+this.nombre+"','"+this.compositor+"','"+this.grupoartist+"'";
    }
}