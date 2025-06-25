package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MataKuliah;
import util.DatabaseConnection;

public class MataKuliahController {
    
    public boolean tambahMataKuliah(MataKuliah matakuliah) {
        String sql = "INSERT INTO matakuliah (kode_matakuliah, nama_matakuliah, sks) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, matakuliah.getKodeMatakuliah());
            pstmt.setString(2, matakuliah.getNamaMatakuliah());
            pstmt.setInt(3, matakuliah.getSks());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<MataKuliah> getAllMataKuliah() {
        List<MataKuliah> matakuliahList = new ArrayList<>();
        String sql = "SELECT * FROM matakuliah";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                MataKuliah mk = new MataKuliah();
                mk.setId(rs.getInt("id"));
                mk.setKodeMatakuliah(rs.getString("kode_matakuliah"));
                mk.setNamaMatakuliah(rs.getString("nama_matakuliah"));
                mk.setSks(rs.getInt("sks"));
                
                matakuliahList.add(mk);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return matakuliahList;
    }
    
    public boolean hapusMataKuliah(int id) {
        String sql = "DELETE FROM matakuliah WHERE id = ?";
        
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
