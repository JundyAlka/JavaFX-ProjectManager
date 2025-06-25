package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pengguna;
import util.DatabaseConnection;

public class PenggunaController {
    
    public boolean tambahPengguna(Pengguna pengguna) {
        String sql = "INSERT INTO pengguna (nama_lengkap, nim, no_hp, email, jenis_kelamin) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, pengguna.getNamaLengkap());
            pstmt.setString(2, pengguna.getNim());
            pstmt.setString(3, pengguna.getNoHp());
            pstmt.setString(4, pengguna.getEmail());
            pstmt.setString(5, pengguna.getJenisKelamin());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Pengguna> getAllPengguna() {
        List<Pengguna> penggunaList = new ArrayList<>();
        String sql = "SELECT * FROM pengguna";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Pengguna p = new Pengguna();
                p.setId(rs.getInt("id"));
                p.setNamaLengkap(rs.getString("nama_lengkap"));
                p.setNim(rs.getString("nim"));
                p.setNoHp(rs.getString("no_hp"));
                p.setEmail(rs.getString("email"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                
                penggunaList.add(p);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return penggunaList;
    }
    
    public boolean hapusPengguna(int id) {
        String sql = "DELETE FROM pengguna WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
