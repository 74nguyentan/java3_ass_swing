/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asignment.view;

import Asignment.dao.DAO;
import Asignment.model.grade;
import Asignment.model.student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Van Tan Exfick
 */
public class Quan_li extends javax.swing.JFrame {

    /**
     * Creates new form Quan_li
     */
   // DAO db;
    ArrayList<grade> list = new ArrayList<>();
    DefaultTableModel model;
    private Connection conn;
    int index = 0;
    public Quan_li() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        model = (DefaultTableModel) tblQLDSV.getModel();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL;", "FPL", "fplfplfpl");
        } catch (Exception e) {
            System.out.println(e);
        }
        list = getListGrade();
        loadDbToTable();
        loadDataToCbo();
      //  db = new DAO();
        btnSeach.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\search.png"));
        btnNexxt_Lefft.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\nextll.png"));
        btnNext_L.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\next1.png"));
        btnNext_R.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\next2.png"));
        btnNexxt_Right.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\nextr.png"));
        btnNew.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\cong.png"));
        BtnSave.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\save.png"));
        btnDelete.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\delette.png"));
        btnUpDate.setIcon(new ImageIcon("D:\\Users\\NetBeansProjects\\Nguyen_Van_Tan\\src\\Asignment\\Image\\up.png"));
    }

    public void loadDataToCbo() {
        try {
            String sql = "SELECT MaSV FROM students";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cboMasv.addItem(rs.getString("MaSV"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean selectItem() {
        try {
            String sql = "SELECT * FROM students WHERE MaSV = ?";
            String ma = (String) cboMasv.getSelectedItem();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lblID.setText(ma);
                lblName.setText(rs.getString("Hoten"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public ArrayList<grade> getListGrade() {
        try {
            String sql = "SELECT TOP 3 students.MaSV,Hoten,Tienganh,Tinhoc,GDTC,(Tienganh+Tinhoc+GDTC)/3 AS TBM FROM students JOIN GRADE\n"
                    + " ON students.MaSV = GRADE.MaSV ORDER BY TBM DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grade sv = new grade();
                sv.setMaSV(rs.getString("MaSV"));
                sv.setName(rs.getString("Hoten"));
                sv.setTiengAnh(Double.parseDouble(rs.getString("Tienganh")));
                sv.setTinHoc(Double.parseDouble(rs.getString("Tinhoc")));
                sv.setGDTC(Double.parseDouble(rs.getString("GDTC")));
                sv.setAvg(Double.parseDouble(rs.getString("TBM")));
                list.add(sv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void fillTable() {
        model.setRowCount(0);
        for (grade sv : list) {
            sv.MaSV = lblID.getText();
            sv.Name = lblName.getText();
            Object[] row = new Object[]{sv.MaSV, sv.Name, sv.TiengAnh, sv.TinHoc, sv.GDTC, sv.getAvg()};
            model.addRow(row);
        }
    }

    public void showDetail(int index) {
        lblName.setText(list.get(index).getName());
        lblID.setText(list.get(index).getMaSV());
        txtTiengAnh.setText(String.valueOf(list.get(index).getTiengAnh()));
        txtTinHoc.setText(String.valueOf(list.get(index).getTinHoc()));
        txtGDTC.setText(String.valueOf(list.get(index).getGDTC()));
        lblDiemTB.setText(String.valueOf(list.get(index).getAvg()));
    }

    public void loadDbToTable() {
        try {
            model.setRowCount(0);
            String sql = "SELECT TOP 3 students.MaSV,Hoten,TiengAnh,TinHoc,GDTC,(TiengAnh+TinHoc+GDTC)/3 AS TBM FROM students JOIN GRADE\n"
                    + " ON students.MaSV = GRADE.MaSV ORDER BY TBM DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("MASV"));
                row.add(rs.getString("HOTEN"));
                row.add(Double.parseDouble(rs.getString("Tienganh")));
                row.add(Double.parseDouble(rs.getString("Tinhoc")));
                row.add(Double.parseDouble(rs.getString("GDTC")));
                row.add(Double.parseDouble(rs.getString("TBM")));
                model.addRow(row);
            }
            tblQLDSV.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean deleteGrade() {
        try {
            String ma = lblID.getText();
            String sql = "DELETE FROM GRADE WHERE MaSV = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateGrade() {

        try {
            String id = lblID.getText();
            String sql = "UPDATE GRADE SET Tienganh = ?, Tinhoc = ?, GDTC = ? WHERE MaSV = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtTiengAnh.getText());
            ps.setString(2, txtTinHoc.getText());
            ps.setString(3, txtGDTC.getText());
            ps.setString(4, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblQLDSV = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblMaSV = new javax.swing.JLabel();
        btnSeach = new javax.swing.JButton();
        cboMasv = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        lblIDsv = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTiengAnh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTinHoc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGDTC = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblDiemTB = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        btnNew = new javax.swing.JButton();
        BtnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpDate = new javax.swing.JButton();
        btnNexxt_Lefft = new javax.swing.JButton();
        btnNext_L = new javax.swing.JButton();
        btnNext_R = new javax.swing.JButton();
        btnNexxt_Right = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQLDSV = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lí Điểm Sinh Viên");

        lblQLDSV.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lblQLDSV.setForeground(new java.awt.Color(0, 0, 255));
        lblQLDSV.setText("Quản Lí Điểm Sinh Viên ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13))); // NOI18N

        lblMaSV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMaSV.setText("Mã SV :");

        btnSeach.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSeach.setForeground(new java.awt.Color(0, 0, 255));
        btnSeach.setText("Seach");
        btnSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeachActionPerformed(evt);
            }
        });

        cboMasv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã sinh viên" }));
        cboMasv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMasvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblMaSV)
                .addGap(35, 35, 35)
                .addComponent(cboMasv, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaSV)
                    .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMasv, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHoTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblHoTen.setText("Họ Và Tên SV:");

        lblIDsv.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblIDsv.setText("Mã SV:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Tiếng anh:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Tin học:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Giáo dục TC:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Điểm TB");

        lblDiemTB.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblDiemTB.setForeground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiemTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDiemTB, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHoTen)
                    .addComponent(lblIDsv)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtGDTC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                .addComponent(txtTinHoc, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTiengAnh, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(18, 18, 18)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblHoTen))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIDsv)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtTiengAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTinHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        BtnSave.setText("Save");
        BtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpDate.setText("UpDate");
        btnUpDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpDateActionPerformed(evt);
            }
        });

        btnNexxt_Lefft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNexxt_LefftActionPerformed(evt);
            }
        });

        btnNext_L.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext_LActionPerformed(evt);
            }
        });

        btnNext_R.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNext_RActionPerformed(evt);
            }
        });

        btnNexxt_Right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNexxt_RightActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setText(" 3 SV có điểm cao nhất :");

        tblQLDSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã SV", "Họ tên", "Tiếng anh", "Tin học", "GDTC", "Điểm TB"
            }
        ));
        tblQLDSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLDSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblQLDSV);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnNext_L, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(btnNext_R, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(btnNexxt_Lefft, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(153, 153, 153)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNexxt_Right, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
                            .addComponent(BtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpDate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblQLDSV)
                .addGap(170, 170, 170))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblQLDSV)
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpDate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNext_R, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNexxt_Right, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNexxt_Lefft, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(btnNext_L, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeachActionPerformed
this.selectItem();
    }//GEN-LAST:event_btnSeachActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
         index = tblQLDSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(rootPane, "Bạn chưa chọn hàng cần xóa");
        } else {
            this.deleteGrade();
            list.remove(index);
            fillTable();
            this.btnNewActionPerformed(evt);
            JOptionPane.showMessageDialog(rootPane, "Đã xóa thành công!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpDateActionPerformed
        // TODO add your handling code here:
         index = tblQLDSV.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hàng cần cập nhật");
        } else {
            list.remove(index);
            this.updateGrade();
            grade sv = new grade();
            sv.TiengAnh = Double.parseDouble(txtTiengAnh.getText());
            sv.TinHoc = Double.parseDouble(txtTinHoc.getText());
            sv.GDTC = Double.parseDouble(txtGDTC.getText());
            lblDiemTB.setText(String.valueOf(sv.getAvg()));
            list.add(sv);
            fillTable();
            JOptionPane.showMessageDialog(rootPane, "Cập nhật thành công!");
        }
    }//GEN-LAST:event_btnUpDateActionPerformed

    private void btnNext_LActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext_LActionPerformed
        // TODO add your handling code here:
         index++;
        if (index >= getListGrade().size()) {
            index = getListGrade().size() - 1;
        }
        showDetail(index);
    }//GEN-LAST:event_btnNext_LActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
       // txtMaTimKiem.setText("");
        //txtName.setText("");
        lblName.setText("");
        lblID.setText("");
        txtGDTC.setText("");
        txtTiengAnh.setText("");
        txtTinHoc.setText("");
        cboMasv.setSelectedIndex(0);
        lblDiemTB.setText("");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnNext_RActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNext_RActionPerformed
        // TODO add your handling code here:
        index--;
        if (index < 0) {
            index = 0;
        }
        showDetail(index);
    }//GEN-LAST:event_btnNext_RActionPerformed

    private void BtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSaveActionPerformed
        // TODO add your handling code here:
        grade sv = new grade();
       // sv.MaSV = lblMaSV.getText();
       // sv.Name = lblName.getText();
        sv.TiengAnh = Double.parseDouble(txtTiengAnh.getText());
        sv.TinHoc = Double.parseDouble(txtTinHoc.getText());
        sv.GDTC = Double.parseDouble(txtGDTC.getText()); 
        lblDiemTB.setText(String.valueOf(sv.getAvg()));
        list.add(sv);
        try {
            String sql = "INSERT INTO GRADE(MaSV, Tienganh, Tinhoc, GDTC) VALUES ( ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lblID.getText());
            ps.setDouble(2, sv.TiengAnh);
            ps.setDouble(3, sv.TinHoc);
            ps.setDouble(4, sv.GDTC);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Lưu thành công!");
        } catch (Exception e) {
            System.out.println(e);
        }
        fillTable();
    }//GEN-LAST:event_BtnSaveActionPerformed

    private void tblQLDSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLDSVMouseClicked
        // TODO add your handling code here:
        index = tblQLDSV.getSelectedRow();
        this.showDetail(index);
    }//GEN-LAST:event_tblQLDSVMouseClicked

    private void btnNexxt_RightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNexxt_RightActionPerformed
        // TODO add your handling code here:
        index = getListGrade().size() - 1;
        showDetail(index);
    }//GEN-LAST:event_btnNexxt_RightActionPerformed

    private void btnNexxt_LefftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNexxt_LefftActionPerformed
        // TODO add your handling code here:
        showDetail(0);
    }//GEN-LAST:event_btnNexxt_LefftActionPerformed

    private void cboMasvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMasvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMasvActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Quan_li.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Quan_li.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Quan_li.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Quan_li.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Quan_li().setVisible(true);
            }
        });
        // ket noi co so du lieu
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection ketnoi = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=FPL;", "FPL", "fplfplfpl");
            // System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSave;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext_L;
    private javax.swing.JButton btnNext_R;
    private javax.swing.JButton btnNexxt_Lefft;
    private javax.swing.JButton btnNexxt_Right;
    private javax.swing.JButton btnSeach;
    private javax.swing.JButton btnUpDate;
    private javax.swing.JComboBox<String> cboMasv;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiemTB;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDsv;
    private javax.swing.JLabel lblMaSV;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblQLDSV;
    private javax.swing.JTable tblQLDSV;
    private javax.swing.JTextField txtGDTC;
    private javax.swing.JTextField txtTiengAnh;
    private javax.swing.JTextField txtTinHoc;
    // End of variables declaration//GEN-END:variables
}
