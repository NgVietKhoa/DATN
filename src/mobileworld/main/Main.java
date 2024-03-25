package mobileworld.main;

import java.awt.BorderLayout;
import static java.awt.Color.decode;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import mobileworld.component.Menu;
import mobileworld.form.ViewBanHang;
import mobileworld.form.ViewSanPham;
import mobileworld.form.ViewHoaDon;
import mobileworld.form.ViewGiamGia1;
import mobileworld.form.ViewKhachHangNew;
import mobileworld.form.ViewNhanVienNew;
import mobileworld.form.ViewThongKe;
import mobileworld.model.ModelMenu;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {

    private final Menu menu = new Menu();
    private final JPanel main = new JPanel();
    private MigLayout layout;
    private Animator animator;
    private boolean menuShow;

    public Main() {
        initComponents();
        init();
        SessionStorage.getInstance().getUsername();
        jPanel1.setBackground(decode("#11998e"));
        ImageIcon icon = new ImageIcon("E:\\mobileWorld\\src\\mobileworld\\icon\\Logomb.png");
        setIconImage(icon.getImage());
    }

    private void init() {
        layout = new MigLayout("fill", "0[]10[]5", "0[fill]0");
        body.setLayout(layout);
        main.setOpaque(false);
        main.setLayout(new BorderLayout());

        menu.addEventLogOut((ActionEvent e) -> {

            Login lg = new Login();
            lg.setVisible(true);
            dispose();
        });

        menu.addEventMenu((ActionEvent e) -> {
            if (!animator.isRunning()) {
                animator.start();
            }
        });

        ViewHoaDon viewHoaDon = new ViewHoaDon(this, menu);

        menu.setEvent((int index) -> {
            switch (index) {
                case 0 ->
                    showForm(new ViewBanHang());
                case 1 ->
                    showForm(new ViewSanPham());
                case 2 ->
                    showForm(viewHoaDon);
                case 3 ->
                    showForm(new ViewGiamGia1());
                case 4 ->
                    showForm(new ViewNhanVienNew());
                case 5 ->
                    showForm(new ViewKhachHangNew());
                case 6 ->
                    showForm(new ViewThongKe());
                default -> {
                }
            }
        });

        menu.addMenu(new ModelMenu("Bán Hàng", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-sell-24 (1).png"))));
        menu.addMenu(new ModelMenu("Sản Phẩm", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-product-24 (1).png"))));
        menu.addMenu(new ModelMenu("Hóa Đơn", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-bill-24 (1).png"))));
        menu.addMenu(new ModelMenu("Khuyến Mãi", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-sale-24 (1).png"))));
        menu.addMenu(new ModelMenu("Nhân Viên", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-staff-24 (1).png"))));
        menu.addMenu(new ModelMenu("Khách Hàng", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-client-24 (1).png"))));
        menu.addMenu(new ModelMenu("Thống Kê", new ImageIcon(getClass().getResource("/mobileworld/icon/icons8-circle-chart-24 (1).png"))));

        body.add(menu, "w 50!");
        body.add(main, "w 100%");

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuShow) {
                    width = 50 + (150 * (1f - fraction));
                    menu.setAlpha(1f - fraction);
                } else {
                    width = 50 + (150 * fraction);
                    menu.setAlpha(fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!");
                body.revalidate();
            }

            @Override
            public void end() {
                menuShow = !menuShow;
            }

        };
        animator = new Animator(400, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        showForm(new ViewBanHang());
    }

    public final void showForm(Component com) {
        main.removeAll();
        main.add(com);
        main.repaint();
        main.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 772, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-exit-24.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mobileworld/icon/icons8-minimize-24.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(1233, 1233, 1233)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addComponent(jLabel1)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        int state = Main.this.getExtendedState();

        if ((state & Frame.ICONIFIED) != 0) {

            Main.this.setExtendedState(state & ~Frame.ICONIFIED);
        } else {

            Main.this.setExtendedState(state | Frame.ICONIFIED);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

//    public static void main(String args[]) throws UnsupportedLookAndFeelException {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Main().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
