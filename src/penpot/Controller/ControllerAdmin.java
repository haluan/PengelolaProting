/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penpot.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import penpot.Koneksi.JembatanLogin;
import penpot.Model.Admin;

/**
 *
 * @author haluan
 */
public class ControllerAdmin {
    private Connection conn = JembatanLogin.getMyLgn().getConnDB();
    private String query;
    
    public Admin insert(Admin a) throws SQLException {
        PreparedStatement st = JembatanLogin.getMyLgn().getConnDB().
                prepareStatement("insert into admin"
                + " values(?,?,?)");
        st.setString(1, a.getNip());
        st.setString(2, a.getNama());
        st.setString(3, a.getPassword());
        
        st.executeUpdate();
        return a;
    }
     public List<Admin> getAll() throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        query = "select nip, nama from admin "
                + "order by nip asc";
        ResultSet rs = st.executeQuery(query);
        List<Admin> listAdm = new ArrayList<Admin>();
        while (rs.next()) {
                Admin a = new Admin();
                a.setNip(rs.getString("nip"));
                a.setNama(rs.getString("nama"));
                listAdm.add(a);
            }
        
        return listAdm;
    }
     public Admin getData(String nip) throws SQLException {
        Statement st = JembatanLogin.getMyLgn().getConnDB().createStatement();
        Admin a = new Admin();
        query = "select nip, nama from admin "
                + "where nip ='"+nip+"'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            
            a.setNip(rs.getString("nip"));
            a.setNama(rs.getString("nama"));
            
        }

        return a;
    }
      public void updatePassAdmin(Admin a) throws SQLException{
          PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "admin set password=? where nip=?");
        
        st.setString(1, a.getPassword());
        st.setString(2, a.getNip());
        st.executeUpdate();
     }
     public void update(Admin a) throws SQLException {
        PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement("update "
                + "admin set nama=? where nip=?");
        
        st.setString(1, a.getNama());
        st.setString(2, a.getNip());
        st.executeUpdate();
    }
     
     public void delete(String nip) throws SQLException {
       PreparedStatement st=JembatanLogin.getMyLgn().getConnDB().prepareStatement
               ("delete from admin where nip=?");
        st.setString(1, nip);
        st.executeUpdate();
    }
    
}
