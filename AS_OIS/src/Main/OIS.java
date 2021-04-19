package Main;

import ActiveEntity.AECashier;
import ActiveEntity.AECustomer;import ActiveEntity.AEManager;
import SACorridor.ICorridor_Cashier;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Manager;
import SACorridorHall.SACorridorHall;
import SAEntranceHall.IEntranceHall_Customer;
import SAEntranceHall.IEntranceHall_Manager;
import SAEntranceHall.SAEntranceHall;
;
import SAIdle.IIdle_Customer;
import SAIdle.SAIdle;
import SAOutsideHall.IOutsideHall_Customer;
import SAOutsideHall.IOutsideHall_Manager;
import SAOutsideHall.SAOutsideHall;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.SAPaymentPoint;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * @author omp
 */
public class OIS extends javax.swing.JFrame {

    
    public OIS() {
        initComponents();
        initOIS();
    }
    private void initOIS() {
        final int MAX_CUSTOMERS = 99;
        final int cto = 1;
        final int sto = 1000;
        final int N_CORRIDOR_HALL = 3;
        final int N_CORRIDOR = 3;
        final int SIZE_ENTRANCE_HALL = 6;
        final int SIZE_CORRIDOR_HALL = 3;
        
        final SAIdle idle = new SAIdle();
        final SAOutsideHall outsideHall =  new SAOutsideHall( MAX_CUSTOMERS );
        final SAEntranceHall entranceHall = new SAEntranceHall (SIZE_ENTRANCE_HALL); //fifo com 6 espaços
        final SACorridorHall[] corridorHalls = new SACorridorHall[N_CORRIDOR_HALL];
        final SACorridor[] corridors = new SACorridor[N_CORRIDOR];
        final SAPaymentHall[] paymentHalls = new SAPaymentHall[N_CORRIDOR];
        final SAPaymentPoint[] paymentPoints = new SAPaymentPoint[N_CORRIDOR];
        
        final AECustomer[] aeCustomer = new AECustomer[ MAX_CUSTOMERS ];
        final AEManager aeManager = new AEManager(sto,(IOutsideHall_Manager) outsideHall, (IEntranceHall_Manager) entranceHall, corridorHalls); //sto
        final AECashier[] aeCashier = new AECashier[3];
        
        for(int i = 0; i < N_CORRIDOR_HALL; i++)
        {
           corridorHalls[i] = new SACorridorHall(SIZE_CORRIDOR_HALL);
           corridors[i] = new SACorridor(10,corridorHalls[i].getFifo());
           paymentHalls[i] = new SAPaymentHall(2);
           paymentPoints[i] = new SAPaymentPoint(1);
           aeCashier[i] = new AECashier(corridors[i],paymentHalls[i],paymentPoints[i],cto);
           aeCashier[i].start();
        }
        
        
        //Começar thread manager
        aeManager.start();

        
        for ( int i = 0; i < MAX_CUSTOMERS; i++ ) {
            aeCustomer[ i ] = new AECustomer( i,cto,(IIdle_Customer) idle,(IOutsideHall_Customer) outsideHall, (IEntranceHall_Customer) entranceHall, corridorHalls,  corridors, paymentHalls, paymentPoints);
            aeCustomer[ i ].start();
        }

        for(int i = 0 ;i < 3; i++)
        {
            try {
                aeCashier[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(OIS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
           aeManager.join();
        } catch ( Exception ex ) {}    
        
        try {
            for ( int i = 0; i < MAX_CUSTOMERS; i++ )
                aeCustomer[ i ].join();
        } catch ( Exception ex ) {}    
            
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OIS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
