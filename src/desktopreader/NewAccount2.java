/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopreader;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author uset
 */
public class NewAccount2 extends javax.swing.JFrame {

    public String username;
    public String password;
    public String location;
    public String age;
    public String rating;
    public static int sayi=10;
    public static int sayfa=1;
    public static int sayac=0;
    public int sayfa_sayisi;
    ArrayList<String> isbnler = new ArrayList<>();
    ArrayList<String> ratingler = new ArrayList<>();
    ArrayList<Integer> id = new ArrayList<>();
    
    baglanti b = new baglanti();
    
    public NewAccount2() {
        initComponents();
    }
    
    public NewAccount2(String username,String password,String location,String age) throws SQLException, IOException {
        initComponents();
        myInit();
        this.username=username;
        this.password=password;
        this.location=location;
        this.age=age;
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void myInit() throws SQLException, MalformedURLException, IOException{
        adet.setText("Oylamanız gereken kitap sayısı :"+sayi);
        tamamla.setVisible(false);
        BufferedImage img;
        ArrayList<Kitap> list = new ArrayList<>();
        
        b.myRs=b.myStat.executeQuery("select * from books");
        Kitap k;
       
        while(b.myRs.next()){
           k = new Kitap(b.myRs.getString("isbn"),
            b.myRs.getString("book_title"),
            b.myRs.getString("book_author"),
            b.myRs.getInt("year_of_publication"),
            b.myRs.getString("publisher"),
            b.myRs.getString("image_url_s"),
            b.myRs.getString("image_url_m"),
            b.myRs.getString("image_url_l"));
           list.add(k);
        }
        
        if(list.size()%30==0){
            sayfa_sayisi=list.size()/30;
        }
        else
            sayfa_sayisi=list.size()/30+1;
        
        Object[][] satir = new Object[30][4];
        
        for(int i=0;i<30;i++){
            img=ImageIO.read(new URL(list.get(i).getImageL()));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
            satir[i][0]=icon;
            satir[i][1]=list.get(i).getTitle();
            satir[i][2]=list.get(i).getAuthor();
            satir[i][3]=list.get(i).getISBN();
        }
        String[] columnName = {"Kapak","Name","Author","ISBN"};
        TheModel model = new TheModel(satir, columnName);
        Tablo.setModel(model);
        Tablo.setRowHeight(200);
        Tablo.getColumnModel().getColumn(0).setPreferredWidth(100);
        
        sayfa_no.setText(""+sayfa);
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tablo = new javax.swing.JTable();
        adet = new javax.swing.JLabel();
        tamamla = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sayfa_no = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 148, 6));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Yeni Üyelik");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("_");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("X");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(1, 50, 60));

        Tablo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tablo);

        adet.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        adet.setForeground(new java.awt.Color(255, 255, 255));

        tamamla.setText("Üyeliği Tamamla");
        tamamla.setToolTipText("");
        tamamla.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tamamla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamamlaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-go-back-50.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-back-24.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-more-than-24.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-rewind-26.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-fast-forward-26.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        sayfa_no.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sayfa_no.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addGap(103, 103, 103)
                .addComponent(sayfa_no, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(171, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(adet, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tamamla, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(299, 299, 299))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(adet, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9)
                    .addComponent(sayfa_no, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tamamla, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.setState(JFrame.ICONIFIED);
        sayi=10;
        isbnler.clear();
        ratingler.clear();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void TabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabloMouseClicked
        rating=null;
        
        int index = Tablo.getSelectedRow();
        TableModel model2 = Tablo.getModel();
        String isbn = model2.getValueAt(index, 3).toString();
        rating = JOptionPane.showInputDialog(null,"Puan");
        String[] puanlar = {"1","2","3","4","5","6","7","8","9","10"};
        boolean kontrol = false;
        
        if(rating != null){
            for(int i=0;i<10;i++){
                if(rating.equals(puanlar[i])){
                   sayi--;
                   kontrol=true;
                   break;
                }
            }
        
            if(kontrol==true){
                if(sayi<=1){
                    adet.setText("Üyeliğinizi tamamlayabilirsiniz !");
                    tamamla.setVisible(true);
                }
                else
                    adet.setText("Oylamanız gereken kitap sayısı :"+sayi);
                
                isbnler.add(isbn);
                ratingler.add(rating);
                
                
            }
            else
                JOptionPane.showMessageDialog(null, "Puan 1 ile 10 arasında olmalı !");
        }
        
    }//GEN-LAST:event_TabloMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.setVisible(false);
        sayi=10;
        ratingler.clear();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void tamamlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tamamlaActionPerformed
        
        try {
            int user_id=0,i;
            b.myStat.executeUpdate("insert into users(location,age) values ('"+location+"','"+age+"');");
            b.myStat.executeUpdate("insert into kullanicilar(username,password) values ('"+username+"','"+password+"');");
            
            b.myRs=b.myStat.executeQuery("select id from kullanicilar where username='"+username+"';");
            while(b.myRs.next()){
                user_id=b.myRs.getInt("id");
            }
            
            i=0;
            while(i<isbnler.size()){
                b.myStat.executeUpdate("insert into book_ratings(user_id,isbn,book_rating) values ('"+user_id+"','"+isbnler.get(i)+"','"+ratingler.get(i)+"');");
                i++;
            }
            JOptionPane.showMessageDialog(null, "Üyelik Başarılı !"); 
            this.setVisible(false);
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Üyelik Başarısız !");
            Logger.getLogger(NewAccount2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tamamlaActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        sayac=sayac+30;
        sayfa++;
        sayfa_no.setText(""+sayfa);
        System.out.println("Sayac :"+sayac);
        try {
            BufferedImage img;
            ArrayList<Kitap> list = new ArrayList<>();
            
            b.myRs=b.myStat.executeQuery("select * from books limit "+sayac+",30");
            Kitap k;
            
            while(b.myRs.next()){
                k = new Kitap(b.myRs.getString("isbn"),
                        b.myRs.getString("book_title"),
                        b.myRs.getString("book_author"),
                        b.myRs.getInt("year_of_publication"),
                        b.myRs.getString("publisher"),
                        b.myRs.getString("image_url_s"),
                        b.myRs.getString("image_url_m"),
                        b.myRs.getString("image_url_l"));
                list.add(k);
            }
            
            Object[][] satir = new Object[30][4];
            
            for(int i=0;i<30;i++){
                img=ImageIO.read(new URL(list.get(i).getImageL()));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
                satir[i][0]=icon;
                satir[i][1]=list.get(i).getTitle();
                satir[i][2]=list.get(i).getAuthor();
                satir[i][3]=list.get(i).getISBN();
            }
            String[] columnName = {"Kapak","Name","Author","ISBN"};
            TheModel model = new TheModel(satir, columnName);
            Tablo.setModel(model);
            Tablo.setRowHeight(200);
            Tablo.getColumnModel().getColumn(0).setPreferredWidth(100);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Reading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        if(sayac!=0){
            sayac=sayac-30;
        }
        sayfa--;
        sayfa_no.setText(""+sayfa);
        System.out.println("Sayac :"+sayac);
        if(sayac>=0){
            try {
                BufferedImage img;
                ArrayList<Kitap> list = new ArrayList<>();

                b.myRs=b.myStat.executeQuery("select * from books limit "+sayac+",30");
                Kitap k;

                while(b.myRs.next()){
                    k = new Kitap(b.myRs.getString("isbn"),
                            b.myRs.getString("book_title"),
                            b.myRs.getString("book_author"),
                            b.myRs.getInt("year_of_publication"),
                            b.myRs.getString("publisher"),
                            b.myRs.getString("image_url_s"),
                            b.myRs.getString("image_url_m"),
                            b.myRs.getString("image_url_l"));
                    list.add(k);
                }

                Object[][] satir = new Object[30][4];

                for(int i=0;i<30;i++){
                    img=ImageIO.read(new URL(list.get(i).getImageL()));
                    ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
                    satir[i][0]=icon;
                    satir[i][1]=list.get(i).getTitle();
                    satir[i][2]=list.get(i).getAuthor();
                    satir[i][3]=list.get(i).getISBN();
                }
                String[] columnName = {"Kapak","Name","Author","ISBN"};
                TheModel model = new TheModel(satir, columnName);
                Tablo.setModel(model);
                Tablo.setRowHeight(200);
                Tablo.getColumnModel().getColumn(0).setPreferredWidth(100);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Reading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        try {
            sayfa=1;
            BufferedImage img;
            ArrayList<Kitap> list = new ArrayList<>();
            
            b.myRs=b.myStat.executeQuery("select * from books");
            Kitap k;
            
            while(b.myRs.next()){
                k = new Kitap(b.myRs.getString("isbn"),
                        b.myRs.getString("book_title"),
                        b.myRs.getString("book_author"),
                        b.myRs.getInt("year_of_publication"),
                        b.myRs.getString("publisher"),
                        b.myRs.getString("image_url_s"),
                        b.myRs.getString("image_url_m"),
                        b.myRs.getString("image_url_l"));
                list.add(k);
            }
            
            Object[][] satir = new Object[30][4];
            
            for(int i=0;i<30;i++){
                img=ImageIO.read(new URL(list.get(i).getImageL()));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
                satir[i][0]=icon;
                satir[i][1]=list.get(i).getTitle();
                satir[i][2]=list.get(i).getAuthor();
                satir[i][3]=list.get(i).getISBN();
            }
            String[] columnName = {"Kapak","Name","Author","ISBN"};
            TheModel model = new TheModel(satir, columnName);
            Tablo.setModel(model);
            Tablo.setRowHeight(200);
            Tablo.getColumnModel().getColumn(0).setPreferredWidth(100);
            
            sayfa_no.setText(""+sayfa);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(NewAccount2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if(sayfa_sayisi%30==0){
            sayac=sayfa_sayisi*30;
        }
        else
            sayac=(sayfa_sayisi-1)*30;
        
        sayfa=sayfa_sayisi;
        sayfa_no.setText(""+sayfa);
        System.out.println("Sayac :"+sayac);
        try {
            BufferedImage img;
            ArrayList<Kitap> list = new ArrayList<>();

            b.myRs=b.myStat.executeQuery("select * from books limit "+sayac+",30");
            Kitap k;

            while(b.myRs.next()){
                k = new Kitap(b.myRs.getString("isbn"),
                    b.myRs.getString("book_title"),
                    b.myRs.getString("book_author"),
                    b.myRs.getInt("year_of_publication"),
                    b.myRs.getString("publisher"),
                    b.myRs.getString("image_url_s"),
                    b.myRs.getString("image_url_m"),
                    b.myRs.getString("image_url_l"));
                list.add(k);
            }

            Object[][] satir = new Object[list.size()][4];

            for(int i=0;i<list.size();i++){
                img=ImageIO.read(new URL(list.get(i).getImageL()));
                ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
                satir[i][0]=icon;
                satir[i][1]=list.get(i).getTitle();
                satir[i][2]=list.get(i).getAuthor();
                satir[i][3]=list.get(i).getISBN();
            }
            String[] columnName = {"Kapak","Name","Author","ISBN"};
            TheModel model = new TheModel(satir, columnName);
            Tablo.setModel(model);
            Tablo.setRowHeight(200);
            Tablo.getColumnModel().getColumn(0).setPreferredWidth(100);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Reading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(NewAccount2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewAccount2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewAccount2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewAccount2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewAccount2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tablo;
    private javax.swing.JLabel adet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel sayfa_no;
    private javax.swing.JButton tamamla;
    // End of variables declaration//GEN-END:variables
}
