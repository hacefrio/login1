package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sebastian
 */
import java.io.File;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import tryparse.TryParse;

/**
 *
 * @author RLCR
 */
public class db {

    Connection conexion= null;
    Statement sentencia=null;
    ResultSet resultado=null;
    String NOMBREBD="empresa.sqlite";
    String URL="jdbc:sqlite:"+NOMBREBD;
    String DRIVER="org.sqlite.JDBC";
    TryParse tp=new TryParse();
    
    public void crearTablas(){
    try{

            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            String sql="create table usuarios(" +
                "       nombre_usuario varchar(50) primary key," +
                "       password varchar(255)," +
                "       permisos varchar(50)"
                    + ");";
            sentencia.execute(sql);
            sentencia.close();
            conexion.close();
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("ERROR:"+e.getMessage());
        }
    }

    public boolean logear(String nombre, String contra){
        String salida;
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            String sql="select * from usuarios where nombre_usuario='"+nombre+"' and password='"+contra+"';";
            ResultSet resultado= sentencia.executeQuery(sql);
            salida=resultado.getString("nombre_usuario");
            salida=salida+","+resultado.getString("password");
            salida=salida+","+resultado.getString("permisos");
            sentencia.close();
            conexion.close();
            if(salida!=""){
                return true;
            }
        }catch(ClassNotFoundException | SQLException e){
            return false;
        }
        return false;
    }
    
    public String getpermisos(String nombre){
        String salida;
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            String sql="select * from usuarios where nombre_usuario='"+nombre+"';";
            ResultSet resultado= sentencia.executeQuery(sql);
            salida=resultado.getString("permisos");
            sentencia.close();
            conexion.close();
            return salida;
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
        }
        return "";
    }
    public void insertar_usuario(String nombre,String contra,String permisos){
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            String sql="insert into usuarios values ('"+nombre+"','"+contra+"','"+permisos+"');";
            sentencia.execute(sql);
            sentencia.close();
            conexion.close();
            JOptionPane.showMessageDialog (null, "Consulta realizada con exito", "Consulta realizada con exito", JOptionPane.INFORMATION_MESSAGE);
        }catch(ClassNotFoundException | SQLException e){
                        JOptionPane.showMessageDialog(
        null, "error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void mostrarUsuarios(JTable tabla){
    try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            
            String sql="select * from usuarios";
            ResultSet resultado= sentencia.executeQuery(sql);
            int fila=0;
            while(resultado.next()){
                tabla.setValueAt(resultado.getString("nombre_usuario"), fila, 0);
                tabla.setValueAt(resultado.getString("password"), fila, 1);
                tabla.setValueAt(resultado.getString("permisos"), fila, 2);
                fila++;
            }
            resultado.close();
            sentencia.executeUpdate(sql);
            sentencia.close();
            conexion.close();
        }catch(ClassNotFoundException | SQLException e){
                        JOptionPane.showMessageDialog(
        null, "error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarUsuarios(JTable tabla,String entrada){
    try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            
            String sql="select * from usuarios";
            ResultSet resultado= sentencia.executeQuery(sql);
            int fila=0;
            while(resultado.next()){
                if(resultado.getString("nombre_usuario").equals(entrada) || resultado.getString("password").equals(entrada)){
                    tabla.setValueAt(resultado.getString("nombre_usuario"), fila, 0);
                    tabla.setValueAt(resultado.getString("password"), fila, 1);
                    tabla.setValueAt(resultado.getString("permisos"), fila, 2);
                    fila++;
                }
            }
            resultado.close();
            sentencia.executeUpdate(sql);
            sentencia.close();
            conexion.close();
        }catch(ClassNotFoundException | SQLException e){
                        JOptionPane.showMessageDialog(
        null, "error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editarusuario(String entrada, String nombre, int eleccion){
        String sql="";
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            if (eleccion==1){
             sql="UPDATE usuarios "+
                "SET nombre_usuario = '"+entrada+"'" +
                "WHERE nombre_usuario='"+nombre+"';";
            }else
            if (eleccion==2){
             sql="UPDATE usuarios "+
                "SET password = '"+entrada+"'" +
                "WHERE nombre_usuario='"+nombre+"';";
            }
            else{
             sql="UPDATE usuarios "+
                "SET permisos = '"+entrada+"'" +
                "WHERE nombre_usuario='"+nombre+"';";
            }
            sentencia.execute(sql);
            sentencia.close();
            conexion.close();
            JOptionPane.showMessageDialog (null, "Consulta realizada con exito", "Consulta realizada con exito", JOptionPane.INFORMATION_MESSAGE);
        }catch(ClassNotFoundException | SQLException e){
                        JOptionPane.showMessageDialog(
        null, "error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            System.out.println("consulta realizada!");
    }
    
    public void eliminarusuario(String nombre){
        try{
            Class.forName(DRIVER);
            conexion=DriverManager.getConnection(URL);
            sentencia=conexion.createStatement();
            String sql="DELETE " +
                "FROM usuarios " +
                "WHERE nombre_usuario='"+nombre+"';";
            sentencia.execute(sql);
            sentencia.close();
            conexion.close();
            JOptionPane.showMessageDialog (null, "Consulta realizada con exito", "Consulta realizada con exito", JOptionPane.INFORMATION_MESSAGE);
        }catch(ClassNotFoundException | SQLException e){
                        JOptionPane.showMessageDialog(
        null, "error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            System.out.println("consulta realizada!");
    }

}
