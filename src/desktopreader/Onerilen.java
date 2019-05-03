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
import java.util.Collections;
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
public class Onerilen extends javax.swing.JFrame {
    public int id;
    baglanti b = new baglanti();
    public Onerilen() {
        initComponents();
    }
    
    public Onerilen(int id) throws SQLException, IOException {
        this.id=id;
        initComponents();
        myInit();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("empty-statement")
    public void myInit() throws SQLException, MalformedURLException, IOException{
        
        ArrayList<Integer> kullanicilar = new ArrayList<>();
        ArrayList<Degerler> deger = new ArrayList<>();
        ArrayList<Kitap> list = new ArrayList<>();
        Object[][] satir = null;
        int i;
        int aktif = this.id;
        boolean isbn;
         
        //Kullanıcının oy verdiği kitaplar kontrol ediliyor
        isbn=isbn_kontrol(aktif);
        
        if(isbn){
            //Aktif kullanıcı ile aynı kitapları oylayan kullanıcılar
            kullanicilar=kullanici_bul(aktif);
            
            if(!kullanicilar.isEmpty()){
                //Benzerlik hesaplanıp arraylist e atılıyor
                for(i=0;i<kullanicilar.size();i++){
                    deger.add(benzerlik(aktif,kullanicilar.get(i)));
                }

                //Bulunan benzerlik oranları sıralanıyor
                Collections.sort(deger);
                System.out.println("");
                
                //Önerilecek kitaplar bulunuyor
                list=oneri_bul(deger,aktif);
                
            }
            else
                JOptionPane.showMessageDialog(null,"Sizin ile aynı kitaplara oy veren kullanıcı bulunmadığı için size benzeyen kullanıcılardan kitap önerilmiştir !");
        }
        else
            JOptionPane.showMessageDialog(null,"Oy verdiğiniz kitap bulunmadığı için size benzeyen kullanıcılardan kitap önerilmiştir !");
        
        System.out.println("ISBN kontrol :"+isbn+" | kullanicilar.size() :"+kullanicilar.size()+" | list.size() :"+list.size());
        
        if(isbn==false || kullanicilar.isEmpty() || list.size()<5){
            //Herhangi biri yanlış ise başka kullanıcılar bulunuyor
            ArrayList<Integer> kullanicilar2 = new ArrayList<>();
            ArrayList<Kitap> list2 = new ArrayList<>();
            Kitap k2;
            
            System.out.println("Benzer kullanıcılar bulunuyor !");
            System.out.println("---------------------------------------");
            kullanicilar2=benzer_kullanici(aktif);
            
            
            //Önerilecek kitaplar bulunuyor
            list2=oneri_bul(kullanicilar2,aktif,"");
            
            if(list.size()>0){
                JOptionPane.showMessageDialog(null, "Benzerlik Oranı ile size önerilen kitap sayısı "+list.size()+" olduğu için son "+(5-list.size())+" adet kitap size benzeyen kullanıcılardan önerilmiştir !");
            }
            int size = list.size();
            for(i=0;i<(5-size);i++){
                list.add(list2.get(i));
            }
            
        }
        
        satir = new Object[list.size()][4];

        for(i=0;i<list.size();i++){
            BufferedImage img = ImageIO.read(new URL(list.get(i).getImageL()));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(150, 220, Image.SCALE_SMOOTH));
            satir[i][0]=icon;
            satir[i][1]=list.get(i).getTitle();
            satir[i][2]=list.get(i).getAuthor();
            satir[i][3]=list.get(i).getISBN();

        }

        String[] columnName = {"Kapak","Name","Author","ISBN"};

        TheModel model = new TheModel(satir,columnName);
        tablom.setModel(model);
        tablom.setRowHeight(200);
        tablom.getColumnModel().getColumn(0).setPreferredWidth(100);

        
      
    }
    
    public boolean isbn_kontrol(int aktif) throws SQLException{
        b.myRs=b.myStat.executeQuery("select * from book_ratings where user_id="+aktif);
        boolean kontrol = b.myRs.next();
        
        return kontrol;
    }
    
    public ArrayList<Integer> kullanici_bul(int aktif) throws SQLException{
        ArrayList<Integer> kullanicilar = new ArrayList<>();
        b.myRs=b.myStat.executeQuery("select distinct user_id from book_ratings where book_rating !=0 and user_id!="+aktif+" and isbn in (select isbn from book_ratings where book_rating !=0 and user_id="+aktif+")");
        
        while(b.myRs.next()){
            kullanicilar.add(b.myRs.getInt("user_id"));
        }
        
        return kullanicilar;
    }
    
    public Degerler benzerlik(int aktif_id,int user_id) throws SQLException{
        Degerler d = null;
        double sonuc=0;
        int count=0;
        double katsayi=0;
        b.myRs=b.myStat.executeQuery("select count(A.isbn),(sum(A.book_rating*B.book_rating))/(sqrt(sum(A.book_rating*A.book_rating))*sqrt(sum(B.book_rating)*B.book_rating)) as SumOfProductions from book_ratings as A inner join book_ratings as B on A.isbn where A.isbn=B.isbn and A.user_id="+aktif_id+" and B.user_id="+user_id+" and A.book_rating !=0 and  B.book_rating !=0;");
        while(b.myRs.next()){
            sonuc=b.myRs.getDouble("SumOfProductions"); //Benzerlik Oranı
            count=b.myRs.getInt("count(A.isbn)"); //Ortak kitap sayıları
            katsayi=sonuc*count; //Kendimizce belirlediğimiz oran
            d=new Degerler(sonuc,user_id,count,katsayi);
            if(sonuc<=1){
                System.out.println("Aktif Kullanıcı ile user"+user_id+" arasındaki Benzerlik Oranı :"+sonuc+" | Ortak Kitap Sayısı :"+count);
            }
        }
        
        return d;
    }
    
    public ArrayList<Kitap> oneri_bul(ArrayList<Degerler> deger,int aktif) throws SQLException{
        ArrayList<String> oneri_isbn = new ArrayList<>();
        ArrayList<Kitap> list = new ArrayList<>();
        Kitap kitap;
        int i,j;
        boolean kontrol=false;
        
        for(i=0;i<deger.size();i++){
            if(oneri_isbn.size()==5){
                break;
            }
            
            b.myRs=b.myStat.executeQuery("select * from book_ratings as A where book_rating !=0 and A.user_id="+deger.get(i).id+" and isbn not in (select isbn from book_ratings as B where book_rating !=0 and B.user_id="+aktif+") order by book_rating desc");
            if(b.myRs.next()){
                if(oneri_isbn.isEmpty()){
                   b.myRs.beforeFirst();
                   while(b.myRs.next()){
                        oneri_isbn.add(b.myRs.getString("A.isbn"));
                        System.out.println("İlk Eklenen Öneri ISBN :"+b.myRs.getString("A.isbn")+" | Öneren Kullanıcı : user"+deger.get(i).id);
                        System.out.println("oneri_isbn.size :"+oneri_isbn.size());
                        System.out.println("");
                        break;
                    } 
                }
                else{
                    b.myRs.beforeFirst();
                    kontrol=false;
                    while(b.myRs.next()){
                        for(j=0;j<oneri_isbn.size();j++){
                            if(b.myRs.getString("A.isbn").equals(oneri_isbn.get(j))){
                               kontrol=true;
                               break;
                            }
                        }
                        if(!kontrol){
                            oneri_isbn.add(b.myRs.getString("A.isbn"));
                            System.out.println("Eklenen Öneri ISBN :"+b.myRs.getString("A.isbn")+" | Öneren Kullanıcı : user"+deger.get(i).id);
                            System.out.println("oneri_isbn.size :"+oneri_isbn.size());
                            break;
                        }
                    }
                }
                
            }
            else
                System.out.println("user"+deger.get(i).id+" kullanıcısının oy verdiği tüm kitaplar aktif kullanıcı ile aynı !!");
             
        }
        
        

        for(i=0;i<oneri_isbn.size();i++){
            b.myRs=b.myStat.executeQuery("select * from books where isbn='"+oneri_isbn.get(i)+"'");
            while(b.myRs.next()){
                kitap = new Kitap(b.myRs.getString("isbn"),
                 b.myRs.getString("book_title"),
                 b.myRs.getString("book_author"),
                 b.myRs.getInt("year_of_publication"),
                 b.myRs.getString("publisher"),
                 b.myRs.getString("image_url_s"),
                 b.myRs.getString("image_url_m"),
                 b.myRs.getString("image_url_l"));
                list.add(kitap);
            }
        }
        
        return list;
    }
    
    public ArrayList<Kitap> oneri_bul(ArrayList<Integer> kullanicilar,int aktif,String bos) throws SQLException{
        ArrayList<String> oneri_isbn = new ArrayList<>();
        ArrayList<Kitap> list = new ArrayList<>();
        Kitap kitap;
        int i,j;
        boolean kontrol=false;
        
        for(i=0;i<kullanicilar.size();i++){
            if(oneri_isbn.size()==5){
                break;
            }
            b.myRs=b.myStat.executeQuery("select * from book_ratings where book_rating !=0 and user_id="+kullanicilar.get(i)+" and isbn not in (select isbn from book_ratings where book_rating !=0 and user_id="+aktif+") order by book_rating desc");
            if(b.myRs.next()){
                if(oneri_isbn.isEmpty()){
                   b.myRs.beforeFirst();
                   while(b.myRs.next()){
                        oneri_isbn.add(b.myRs.getString("isbn"));
                        System.out.println("Eklenen Öneri ISBN :"+b.myRs.getString("isbn")+" | Öneren Kullanıcı : user"+kullanicilar.get(i));
                        System.out.println("oneri_isbn.size :"+oneri_isbn.size());
                        System.out.println("");
                        break;
                    } 
                }
                else{
                    b.myRs.beforeFirst();
                    kontrol=false;
                    while(b.myRs.next()){
                        for(j=0;j<oneri_isbn.size();j++){
                            if(b.myRs.getString("isbn").equals(oneri_isbn.get(j))){
                               kontrol=true;
                               break;
                            }
                        }
                        if(!kontrol){
                            oneri_isbn.add(b.myRs.getString("isbn"));
                            System.out.println("Eklenen Öneri ISBN :"+b.myRs.getString("isbn")+" | Öneren Kullanıcı : user"+kullanicilar.get(i));
                            System.out.println("oneri_isbn.size :"+oneri_isbn.size());
                            break;
                        }
                    }
                }
                
            }
            else
                System.out.println("user"+kullanicilar.get(i)+" kullanıcısının oy verdiği tüm kitaplar aktif kullanıcı ile aynı !!");
             
        }
        
        

        for(i=0;i<oneri_isbn.size();i++){
            b.myRs=b.myStat.executeQuery("select * from books where isbn='"+oneri_isbn.get(i)+"'");
            while(b.myRs.next()){
                kitap = new Kitap(b.myRs.getString("isbn"),
                 b.myRs.getString("book_title"),
                 b.myRs.getString("book_author"),
                 b.myRs.getInt("year_of_publication"),
                 b.myRs.getString("publisher"),
                 b.myRs.getString("image_url_s"),
                 b.myRs.getString("image_url_m"),
                 b.myRs.getString("image_url_l"));
                list.add(kitap);
            }
        }
        
        return list;
    }
    
    public ArrayList<Integer> benzer_kullanici(int aktif) throws SQLException{
        ArrayList<Integer> benzer_kullanicilar = new ArrayList<>();
        String location=null;
        int age=-1;
        
        b.myRs=b.myStat.executeQuery("select * from users where user_id="+aktif);
        while(b.myRs.next()){
            location=b.myRs.getString("location");
            age=b.myRs.getInt("age");
        }
        
        String[] loc = location.split(",");
        
        String sql="select * from users where location like '%"+loc[2]+"%'";
        
        if(age!=0){
            sql=sql+" and age="+age;
        }
        
        System.out.println(sql);
        System.out.println("---------------------------");
        b.myRs=b.myStat.executeQuery(sql);
        while(b.myRs.next()){
            benzer_kullanicilar.add(b.myRs.getInt("user_id"));
        }
        return benzer_kullanicilar;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablom = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(248, 148, 6));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Masaüstü Kitap Okuma");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(1, 50, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Önerilen Kitaplar");

        tablom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablomMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablom);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-go-back-50.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 308, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void tablomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablomMouseClicked
        int index = tablom.getSelectedRow();
        TableModel model2 = tablom.getModel();
        String kitapAdi = model2.getValueAt(index, 3).toString();
        ReadingInfo r;
        try {
            r = new ReadingInfo(kitapAdi,id);
        } catch (SQLException ex) {
            Logger.getLogger(Onerilen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tablomMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(Onerilen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Onerilen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Onerilen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Onerilen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Onerilen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablom;
    // End of variables declaration//GEN-END:variables
}
