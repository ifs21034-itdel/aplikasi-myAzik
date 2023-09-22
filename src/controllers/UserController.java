package controllers;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import models.User;
import utils.DatabaseUtil;

public class UserController {
    private DatabaseUtil koneksi;
    private ResultSet rs;
    private PreparedStatement pre;
    
    public UserController(){
        koneksi = new DatabaseUtil();
    }
    
    public ArrayList<User> getAll(){
        ArrayList<User> result = new ArrayList<User>();
        rs = koneksi.executeSelect("SELECT * FROM USER");
        if(rs != null){
            try {
                while(rs.next()){
                    Timestamp timestamp = rs.getTimestamp(9);
                    User pengguna = new User(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8),
                                    timestamp.toLocalDateTime());
                    result.add(pengguna);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }

    public User getById(String username){
        ArrayList<String> data = new ArrayList<String>();
        data.add(username);
        User pengguna = null;
        
        rs = koneksi.executeSelect("SELECT * FROM USER WHERE Username = ?", data);
        if(rs != null){
            try{
                while(rs.next()){
                    Timestamp timestamp = rs.getTimestamp(9);
                    pengguna = new User(Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8),
                                    timestamp.toLocalDateTime());
                    break;
                }
            }catch (Exception ex){
                
            }
        }
        return pengguna;
    }
    /*
    public Boolean update(String user_id, String name, String username, String password){
        ArrayList<String> data = new ArrayList<String>();
        data.add(name);
        data.add(username);
        data.add(password);
        data.add(user_id);
        if(koneksi.execute("UPDATE users SET name = ?, username = ?,"
                +"password = ? WHERE id = ?", data)){
            return true;
        }else{
            return false;
        }    
    }
    
    public Boolean delete(String user_id){
        ArrayList<String> data = new ArrayList<String>();
        data.add(user_id);
        if(koneksi.execute("Delete from users WHERE id = ?", data)){
            return true;
        }else{
            return false;
        }
    }
    */
    public Boolean insert(User pengguna){
        ArrayList<String> data =  new ArrayList<String>();
        if(pengguna.getId() != 0){
            data.add(String.valueOf(pengguna.getId()));
        }
        data.add(pengguna.getRole());
        data.add(pengguna.getNama());
        data.add(pengguna.getProdi());
        data.add(pengguna.getKelas());
        data.add(pengguna.getAsrama());
        data.add(pengguna.getUsername());
        data.add(pengguna.getPassword());
        
        if(pengguna.getId() != 0){
            if(koneksi.execute("INSERT into user (id, ROLE, nama, PRODI, Kelas, Asrama, Username, PASSWORD) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", data)){
                return true;
            }else{
                return false;
            }
        }else{
            if(koneksi.execute("INSERT into user (ROLE, nama, PRODI, Kelas, Asrama, Username, PASSWORD)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)", data)){
                return true;
            }else{
                return false;
            }
        }
    }

    public User getLogin(String role, String username, String password){
        ArrayList<String> data = new ArrayList<String>();
        data.add(String.valueOf(role));
        data.add(String.valueOf(username));
        data.add(String.valueOf(password));
        User pengguna = null;
        
        rs = koneksi.executeSelect("SELECT * FROM user WHERE ROLE = ? AND Username = ? AND Password = ?", data);
        if(rs != null){
            try{
                while(rs.next()){
                    Timestamp timestamp = rs.getTimestamp(9);
                    pengguna = new User(Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8),
                                    timestamp.toLocalDateTime());
                    break;
                }
            }catch (Exception ex){
                
            }
        }
        return pengguna;
    }
    public void close(){
        koneksi.stop();
    }

}
