package controllers;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import models.Daftar;
import utils.ConstUtil;
import utils.DatabaseUtil;

public class DaftarController {
    private DatabaseUtil koneksi;
    private ResultSet rs;
    private PreparedStatement pre;
    
    public DaftarController(){
        koneksi = new DatabaseUtil();
    }
    
    public ArrayList<Daftar> getAll(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
    public ArrayList<Daftar> getAllbyUsername(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik WHERE username = '"+ConstUtil.auth.getUsername()+"'");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
    public ArrayList<Daftar> getAllbyApproved(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik WHERE username =  '"+ConstUtil.auth.getUsername()+"' AND izin_dosen = 'approved' AND izin_keasramaan = 'approved'");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
    public ArrayList<Daftar> getAllbyDisapproved(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik WHERE username =  '"+ConstUtil.auth.getUsername()+"' AND (izin_dosen = 'not approved' || izin_keasramaan = 'not approved')");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
     public ArrayList<Daftar> getAllbyKelas(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik INNER JOIN user ON daftarik.username = user.Username  WHERE Kelas = '"+ConstUtil.auth.getKelas()+"' AND izin_dosen = 'requesting'");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
     
     public ArrayList<Daftar> getAllbyAsrama(){
        ArrayList<Daftar> result = new ArrayList<Daftar>();
        rs = koneksi.executeSelect("SELECT * FROM daftarik INNER JOIN user ON daftarik.username = user.Username WHERE Asrama = '"+ConstUtil.auth.getAsrama()+"' AND izin_keasramaan = 'requesting'");
        if(rs != null){
            try {
                while(rs.next()){
                    Daftar daftar = new Daftar(
                                    Integer.parseInt(rs.getString(1)),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(7),
                                    rs.getString(8));
                    result.add(daftar);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
     public Boolean delete(String daftar_id){
        ArrayList<String> data = new ArrayList<String>();
        data.add(daftar_id);
        if(koneksi.execute("Delete from daftarik WHERE nomor = ?", data)){
            return true;
        }else{
            return false;
        }
    }
    
    public Boolean insert(Daftar daftar){
        ArrayList<String> data =  new ArrayList<String>();
        if(daftar.getId() != 0){
            data.add(String.valueOf(daftar.getId()));
        }
        data.add(daftar.getUsername());
        data.add(daftar.getWaktuPergi());
        data.add(daftar.getWaktuKembali());
        data.add(daftar.getTujuan());
        data.add(daftar.getAlasan());
        
        if(daftar.getId() != 0){
            if(koneksi.execute("INSERT into daftarik (nomor, username, waktu_pergi, waktu_kembali, tujuan, alasan) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", data)){
                return true;
            }else{
                return false;
            }
        }else{
            if(koneksi.execute("INSERT into daftarik (username, waktu_pergi, waktu_kembali, tujuan, alasan) "
                    + "VALUES (?, ?, ?, ?, ?)", data)){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public Boolean updateApprove(String kode){
        ArrayList<String> data = new ArrayList<String>();
        data.add(kode);
        if(koneksi.execute("UPDATE daftarik SET izin_dosen = 'approved'"
                + "WHERE nomor = ?", data)){
            return true;
        }else{
            return false;
        }    
    }
    
    public Boolean updateDisapprove(String kode){
        ArrayList<String> data = new ArrayList<String>();
        data.add(kode);
        if(koneksi.execute("UPDATE daftarik SET izin_dosen = 'not approved'"
                + "WHERE nomor = ?", data)){
            return true;
        }else{
            return false;
        }    
    }
    
    public Boolean updateApproveK(String kode){
        ArrayList<String> data = new ArrayList<String>();
        data.add(kode);
        if(koneksi.execute("UPDATE daftarik SET izin_keasramaan = 'approved'"
                + "WHERE nomor = ?", data)){
            return true;
        }else{
            return false;
        }    
    }
    
    public Boolean updateDisapproveK(String kode){
        ArrayList<String> data = new ArrayList<String>();
        data.add(kode);
        if(koneksi.execute("UPDATE daftarik SET izin_keasramaan = 'not approved'"
                + "WHERE nomor = ?", data)){
            return true;
        }else{
            return false;
        }    
    }
}
