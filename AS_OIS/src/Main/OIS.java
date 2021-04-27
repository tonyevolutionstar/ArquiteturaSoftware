/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ActiveEntity.AECashier;
import ActiveEntity.AEControl;
import ActiveEntity.AECustomer;
import ActiveEntity.AEManager;
import Communication.CClient;
import Communication.ListeningChanges;
import SACorridor.SACorridor;
import SACorridorHall.SACorridorHall;
import SAEntranceHall.IEntranceHall_Customer;
import SAEntranceHall.IEntranceHall_Manager;
import SAEntranceHall.SAEntranceHall;
import java.io.IOException;
import SAIdle.IIdle_Customer;
import SAIdle.SAIdle;
import SAOutsideHall.IOutsideHall_Customer;
import SAOutsideHall.IOutsideHall_Manager;
import SAOutsideHall.SAOutsideHall;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.SAPaymentPoint;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OIS extends javax.swing.JFrame {

    /**
     * Creates new form OIS
     */

    public static int MAX_CUSTOMERS;
    static int cto;
    static int sto;

    public OIS() {
        initComponents();
        try {
            //obtain data from occ
            CClient client = new CClient(60013);
            String[] variables = client.getMessage().split(";");
            // System.out.println(variables);
            MAX_CUSTOMERS = Integer.parseInt(variables[0]);
            cto = Integer.parseInt(variables[1]);
            sto = Integer.parseInt(variables[3]);

            initOIS();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initOIS() {
        final int N_CORRIDOR_HALL = 3;
        final int N_CORRIDOR = 3;
        final int SIZE_ENTRANCE_HALL = 6;
        final int SIZE_CORRIDOR_HALL = 3;

        SAIdle idle = new SAIdle(MAX_CUSTOMERS);
        SAOutsideHall outsideHall = new SAOutsideHall(MAX_CUSTOMERS);
        SAEntranceHall entranceHall = new SAEntranceHall(SIZE_ENTRANCE_HALL); //fifo com 6 espaços
        SACorridorHall[] corridorHalls = new SACorridorHall[N_CORRIDOR_HALL];
        SACorridor[] corridors = new SACorridor[N_CORRIDOR];
        SAPaymentHall[] paymentHalls = new SAPaymentHall[N_CORRIDOR];
        SAPaymentPoint[] paymentPoints = new SAPaymentPoint[N_CORRIDOR];

        AECustomer[] aeCustomer = new AECustomer[MAX_CUSTOMERS];

        AEManager aeManager = new AEManager(sto, (IOutsideHall_Manager) outsideHall, (IEntranceHall_Manager) entranceHall, corridorHalls); //sto
        AECashier[] aeCashier = new AECashier[3];
        AEControl aeControl = new AEControl(aeCustomer, aeManager, aeCashier, idle, outsideHall, entranceHall);

        synchronized (ListeningChanges.class) {
            try {
                ListeningChanges.getInstance().open_server(6003);
            } catch (IOException ex) {
                Logger.getLogger(OIS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i = 0; i < N_CORRIDOR_HALL; i++) {
            corridorHalls[i] = new SACorridorHall(SIZE_CORRIDOR_HALL);
            corridors[i] = new SACorridor(10, corridorHalls[i].getFifo());
            paymentHalls[i] = new SAPaymentHall(2);
            paymentPoints[i] = new SAPaymentPoint(1);
            aeCashier[i] = new AECashier(corridors[i], paymentHalls[i], paymentPoints[i], cto);
            aeCashier[i].start();
        }

        for (int i = 0; i < MAX_CUSTOMERS; i++) {
            aeCustomer[i] = new AECustomer(i, cto, (IIdle_Customer) idle, (IOutsideHall_Customer) outsideHall, (IEntranceHall_Customer) entranceHall, corridorHalls, corridors, paymentHalls, paymentPoints);
            aeCustomer[i].start();
        }

        //Começar thread manager
        aeManager.start();
        aeControl.start();

        for (int i = 0; i < 3; i++) {
            try {
                aeCashier[i].join();
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(OIS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            aeControl.join();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            aeManager.join();
        } catch (InterruptedException ex) {
        }

        try {
            for (int i = 0; i < MAX_CUSTOMERS; i++) {
                aeCustomer[i].join();
            }
        } catch (InterruptedException ex) {
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OIS = new javax.swing.JLabel();
        OutSideLabel = new javax.swing.JLabel();
        OutSidePanel = new javax.swing.JPanel();
        EntranceHallLabel = new javax.swing.JLabel();
        EntranceHallPanel1 = new javax.swing.JPanel();
        EntranceHallPanel2 = new javax.swing.JPanel();
        EntranceHallPanel3 = new javax.swing.JPanel();
        EntranceHallPanel4 = new javax.swing.JPanel();
        EntranceHallPanel5 = new javax.swing.JPanel();
        EntranceHallPanel6 = new javax.swing.JPanel();
        CorridorHallLabel = new javax.swing.JLabel();
        CorridorHallPanel1 = new javax.swing.JPanel();
        CorridorHallPanel2 = new javax.swing.JPanel();
        CorridorHallPanel3 = new javax.swing.JPanel();
        CorridorLabel = new javax.swing.JLabel();
        CorridorPanel1_1 = new javax.swing.JPanel();
        CorridorPanel1_2 = new javax.swing.JPanel();
        CorridorPanel1_3 = new javax.swing.JPanel();
        CorridorPanel1_4 = new javax.swing.JPanel();
        CorridorPanel1_5 = new javax.swing.JPanel();
        CorridorPanel1_6 = new javax.swing.JPanel();
        CorridorPanel1_7 = new javax.swing.JPanel();
        CorridorPanel1_8 = new javax.swing.JPanel();
        CorridorPanel1_9 = new javax.swing.JPanel();
        CorridorPanel1_10 = new javax.swing.JPanel();
        CorridorPanel2_1 = new javax.swing.JPanel();
        CorridorPanel2_2 = new javax.swing.JPanel();
        CorridorPanel2_3 = new javax.swing.JPanel();
        CorridorPanel2_4 = new javax.swing.JPanel();
        CorridorPanel2_5 = new javax.swing.JPanel();
        CorridorPanel2_6 = new javax.swing.JPanel();
        CorridorPanel2_7 = new javax.swing.JPanel();
        CorridorPanel2_8 = new javax.swing.JPanel();
        CorridorPanel2_9 = new javax.swing.JPanel();
        CorridorPanel2_10 = new javax.swing.JPanel();
        CorridorPanel3_1 = new javax.swing.JPanel();
        CorridorPanel3_2 = new javax.swing.JPanel();
        CorridorPanel3_3 = new javax.swing.JPanel();
        CorridorPanel3_4 = new javax.swing.JPanel();
        CorridorPanel3_5 = new javax.swing.JPanel();
        CorridorPanel3_6 = new javax.swing.JPanel();
        CorridorPanel3_7 = new javax.swing.JPanel();
        CorridorPanel3_8 = new javax.swing.JPanel();
        CorridorPanel3_9 = new javax.swing.JPanel();
        CorridorPanel3_10 = new javax.swing.JPanel();
        PaymentHallLabel = new javax.swing.JLabel();
        PaymentHallPanel1 = new javax.swing.JPanel();
        PaymentHallPanel2 = new javax.swing.JPanel();
        PaymentPointLabel = new javax.swing.JLabel();
        PaymentPointPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        OIS.setBackground(new java.awt.Color(51, 255, 255));
        OIS.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        OIS.setForeground(new java.awt.Color(0, 153, 255));
        OIS.setText("OIS GUI");

        OutSideLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        OutSideLabel.setText("OutSideHall");

        OutSidePanel.setBackground(new java.awt.Color(255, 255, 255));
        OutSidePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        OutSidePanel.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout OutSidePanelLayout = new javax.swing.GroupLayout(OutSidePanel);
        OutSidePanel.setLayout(OutSidePanelLayout);
        OutSidePanelLayout.setHorizontalGroup(
            OutSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        OutSidePanelLayout.setVerticalGroup(
            OutSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        EntranceHallLabel.setText("EntranceHall ");

        EntranceHallPanel1.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout EntranceHallPanel1Layout = new javax.swing.GroupLayout(EntranceHallPanel1);
        EntranceHallPanel1.setLayout(EntranceHallPanel1Layout);
        EntranceHallPanel1Layout.setHorizontalGroup(
            EntranceHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel1Layout.setVerticalGroup(
            EntranceHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallPanel2.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel2.setPreferredSize(new java.awt.Dimension(61, 52));
        EntranceHallPanel2.setRequestFocusEnabled(false);

        javax.swing.GroupLayout EntranceHallPanel2Layout = new javax.swing.GroupLayout(EntranceHallPanel2);
        EntranceHallPanel2.setLayout(EntranceHallPanel2Layout);
        EntranceHallPanel2Layout.setHorizontalGroup(
            EntranceHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel2Layout.setVerticalGroup(
            EntranceHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallPanel3.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout EntranceHallPanel3Layout = new javax.swing.GroupLayout(EntranceHallPanel3);
        EntranceHallPanel3.setLayout(EntranceHallPanel3Layout);
        EntranceHallPanel3Layout.setHorizontalGroup(
            EntranceHallPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel3Layout.setVerticalGroup(
            EntranceHallPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallPanel4.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel4.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout EntranceHallPanel4Layout = new javax.swing.GroupLayout(EntranceHallPanel4);
        EntranceHallPanel4.setLayout(EntranceHallPanel4Layout);
        EntranceHallPanel4Layout.setHorizontalGroup(
            EntranceHallPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel4Layout.setVerticalGroup(
            EntranceHallPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallPanel5.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel5.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout EntranceHallPanel5Layout = new javax.swing.GroupLayout(EntranceHallPanel5);
        EntranceHallPanel5.setLayout(EntranceHallPanel5Layout);
        EntranceHallPanel5Layout.setHorizontalGroup(
            EntranceHallPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel5Layout.setVerticalGroup(
            EntranceHallPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        EntranceHallPanel6.setBackground(new java.awt.Color(255, 255, 255));
        EntranceHallPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        EntranceHallPanel6.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout EntranceHallPanel6Layout = new javax.swing.GroupLayout(EntranceHallPanel6);
        EntranceHallPanel6.setLayout(EntranceHallPanel6Layout);
        EntranceHallPanel6Layout.setHorizontalGroup(
            EntranceHallPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        EntranceHallPanel6Layout.setVerticalGroup(
            EntranceHallPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorHallLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CorridorHallLabel.setText("CorridorHall");

        CorridorHallPanel1.setBackground(new java.awt.Color(255, 255, 255));
        CorridorHallPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorHallPanel1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorHallPanel1Layout = new javax.swing.GroupLayout(CorridorHallPanel1);
        CorridorHallPanel1.setLayout(CorridorHallPanel1Layout);
        CorridorHallPanel1Layout.setHorizontalGroup(
            CorridorHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorHallPanel1Layout.setVerticalGroup(
            CorridorHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorHallPanel2.setBackground(new java.awt.Color(255, 255, 255));
        CorridorHallPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorHallPanel2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorHallPanel2Layout = new javax.swing.GroupLayout(CorridorHallPanel2);
        CorridorHallPanel2.setLayout(CorridorHallPanel2Layout);
        CorridorHallPanel2Layout.setHorizontalGroup(
            CorridorHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorHallPanel2Layout.setVerticalGroup(
            CorridorHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorHallPanel3.setBackground(new java.awt.Color(255, 255, 255));
        CorridorHallPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorHallPanel3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorHallPanel3Layout = new javax.swing.GroupLayout(CorridorHallPanel3);
        CorridorHallPanel3.setLayout(CorridorHallPanel3Layout);
        CorridorHallPanel3Layout.setHorizontalGroup(
            CorridorHallPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorHallPanel3Layout.setVerticalGroup(
            CorridorHallPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        CorridorLabel.setText("Corridor");

        CorridorPanel1_1.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout CorridorPanel1_1Layout = new javax.swing.GroupLayout(CorridorPanel1_1);
        CorridorPanel1_1.setLayout(CorridorPanel1_1Layout);
        CorridorPanel1_1Layout.setHorizontalGroup(
            CorridorPanel1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_1Layout.setVerticalGroup(
            CorridorPanel1_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_2.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_2Layout = new javax.swing.GroupLayout(CorridorPanel1_2);
        CorridorPanel1_2.setLayout(CorridorPanel1_2Layout);
        CorridorPanel1_2Layout.setHorizontalGroup(
            CorridorPanel1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_2Layout.setVerticalGroup(
            CorridorPanel1_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_3.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_3Layout = new javax.swing.GroupLayout(CorridorPanel1_3);
        CorridorPanel1_3.setLayout(CorridorPanel1_3Layout);
        CorridorPanel1_3Layout.setHorizontalGroup(
            CorridorPanel1_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_3Layout.setVerticalGroup(
            CorridorPanel1_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_4.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_4.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_4Layout = new javax.swing.GroupLayout(CorridorPanel1_4);
        CorridorPanel1_4.setLayout(CorridorPanel1_4Layout);
        CorridorPanel1_4Layout.setHorizontalGroup(
            CorridorPanel1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_4Layout.setVerticalGroup(
            CorridorPanel1_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_5.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_5.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_5Layout = new javax.swing.GroupLayout(CorridorPanel1_5);
        CorridorPanel1_5.setLayout(CorridorPanel1_5Layout);
        CorridorPanel1_5Layout.setHorizontalGroup(
            CorridorPanel1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_5Layout.setVerticalGroup(
            CorridorPanel1_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_6.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_6.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_6Layout = new javax.swing.GroupLayout(CorridorPanel1_6);
        CorridorPanel1_6.setLayout(CorridorPanel1_6Layout);
        CorridorPanel1_6Layout.setHorizontalGroup(
            CorridorPanel1_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_6Layout.setVerticalGroup(
            CorridorPanel1_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_7.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_7.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_7Layout = new javax.swing.GroupLayout(CorridorPanel1_7);
        CorridorPanel1_7.setLayout(CorridorPanel1_7Layout);
        CorridorPanel1_7Layout.setHorizontalGroup(
            CorridorPanel1_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_7Layout.setVerticalGroup(
            CorridorPanel1_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_8.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_8.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_8Layout = new javax.swing.GroupLayout(CorridorPanel1_8);
        CorridorPanel1_8.setLayout(CorridorPanel1_8Layout);
        CorridorPanel1_8Layout.setHorizontalGroup(
            CorridorPanel1_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_8Layout.setVerticalGroup(
            CorridorPanel1_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_9.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_9.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel1_9Layout = new javax.swing.GroupLayout(CorridorPanel1_9);
        CorridorPanel1_9.setLayout(CorridorPanel1_9Layout);
        CorridorPanel1_9Layout.setHorizontalGroup(
            CorridorPanel1_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_9Layout.setVerticalGroup(
            CorridorPanel1_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel1_10.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel1_10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel1_10.setPreferredSize(new java.awt.Dimension(61, 52));
        CorridorPanel1_10.setRequestFocusEnabled(false);

        javax.swing.GroupLayout CorridorPanel1_10Layout = new javax.swing.GroupLayout(CorridorPanel1_10);
        CorridorPanel1_10.setLayout(CorridorPanel1_10Layout);
        CorridorPanel1_10Layout.setHorizontalGroup(
            CorridorPanel1_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel1_10Layout.setVerticalGroup(
            CorridorPanel1_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_1.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_1Layout = new javax.swing.GroupLayout(CorridorPanel2_1);
        CorridorPanel2_1.setLayout(CorridorPanel2_1Layout);
        CorridorPanel2_1Layout.setHorizontalGroup(
            CorridorPanel2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_1Layout.setVerticalGroup(
            CorridorPanel2_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_2.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_2Layout = new javax.swing.GroupLayout(CorridorPanel2_2);
        CorridorPanel2_2.setLayout(CorridorPanel2_2Layout);
        CorridorPanel2_2Layout.setHorizontalGroup(
            CorridorPanel2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_2Layout.setVerticalGroup(
            CorridorPanel2_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_3.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_3Layout = new javax.swing.GroupLayout(CorridorPanel2_3);
        CorridorPanel2_3.setLayout(CorridorPanel2_3Layout);
        CorridorPanel2_3Layout.setHorizontalGroup(
            CorridorPanel2_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_3Layout.setVerticalGroup(
            CorridorPanel2_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_4.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_4.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_4Layout = new javax.swing.GroupLayout(CorridorPanel2_4);
        CorridorPanel2_4.setLayout(CorridorPanel2_4Layout);
        CorridorPanel2_4Layout.setHorizontalGroup(
            CorridorPanel2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_4Layout.setVerticalGroup(
            CorridorPanel2_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_5.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_5.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_5Layout = new javax.swing.GroupLayout(CorridorPanel2_5);
        CorridorPanel2_5.setLayout(CorridorPanel2_5Layout);
        CorridorPanel2_5Layout.setHorizontalGroup(
            CorridorPanel2_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_5Layout.setVerticalGroup(
            CorridorPanel2_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_6.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_6.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_6Layout = new javax.swing.GroupLayout(CorridorPanel2_6);
        CorridorPanel2_6.setLayout(CorridorPanel2_6Layout);
        CorridorPanel2_6Layout.setHorizontalGroup(
            CorridorPanel2_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_6Layout.setVerticalGroup(
            CorridorPanel2_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_7.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_7.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_7Layout = new javax.swing.GroupLayout(CorridorPanel2_7);
        CorridorPanel2_7.setLayout(CorridorPanel2_7Layout);
        CorridorPanel2_7Layout.setHorizontalGroup(
            CorridorPanel2_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_7Layout.setVerticalGroup(
            CorridorPanel2_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_8.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_8.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_8Layout = new javax.swing.GroupLayout(CorridorPanel2_8);
        CorridorPanel2_8.setLayout(CorridorPanel2_8Layout);
        CorridorPanel2_8Layout.setHorizontalGroup(
            CorridorPanel2_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_8Layout.setVerticalGroup(
            CorridorPanel2_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_9.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_9.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_9Layout = new javax.swing.GroupLayout(CorridorPanel2_9);
        CorridorPanel2_9.setLayout(CorridorPanel2_9Layout);
        CorridorPanel2_9Layout.setHorizontalGroup(
            CorridorPanel2_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_9Layout.setVerticalGroup(
            CorridorPanel2_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel2_10.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel2_10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel2_10.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel2_10Layout = new javax.swing.GroupLayout(CorridorPanel2_10);
        CorridorPanel2_10.setLayout(CorridorPanel2_10Layout);
        CorridorPanel2_10Layout.setHorizontalGroup(
            CorridorPanel2_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel2_10Layout.setVerticalGroup(
            CorridorPanel2_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_1.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_1Layout = new javax.swing.GroupLayout(CorridorPanel3_1);
        CorridorPanel3_1.setLayout(CorridorPanel3_1Layout);
        CorridorPanel3_1Layout.setHorizontalGroup(
            CorridorPanel3_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_1Layout.setVerticalGroup(
            CorridorPanel3_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_2.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_2Layout = new javax.swing.GroupLayout(CorridorPanel3_2);
        CorridorPanel3_2.setLayout(CorridorPanel3_2Layout);
        CorridorPanel3_2Layout.setHorizontalGroup(
            CorridorPanel3_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_2Layout.setVerticalGroup(
            CorridorPanel3_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_3.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_3Layout = new javax.swing.GroupLayout(CorridorPanel3_3);
        CorridorPanel3_3.setLayout(CorridorPanel3_3Layout);
        CorridorPanel3_3Layout.setHorizontalGroup(
            CorridorPanel3_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_3Layout.setVerticalGroup(
            CorridorPanel3_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_4.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_4.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_4Layout = new javax.swing.GroupLayout(CorridorPanel3_4);
        CorridorPanel3_4.setLayout(CorridorPanel3_4Layout);
        CorridorPanel3_4Layout.setHorizontalGroup(
            CorridorPanel3_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_4Layout.setVerticalGroup(
            CorridorPanel3_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_5.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_5.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_5Layout = new javax.swing.GroupLayout(CorridorPanel3_5);
        CorridorPanel3_5.setLayout(CorridorPanel3_5Layout);
        CorridorPanel3_5Layout.setHorizontalGroup(
            CorridorPanel3_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_5Layout.setVerticalGroup(
            CorridorPanel3_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_6.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_6.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_6Layout = new javax.swing.GroupLayout(CorridorPanel3_6);
        CorridorPanel3_6.setLayout(CorridorPanel3_6Layout);
        CorridorPanel3_6Layout.setHorizontalGroup(
            CorridorPanel3_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_6Layout.setVerticalGroup(
            CorridorPanel3_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_7.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_7.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_7Layout = new javax.swing.GroupLayout(CorridorPanel3_7);
        CorridorPanel3_7.setLayout(CorridorPanel3_7Layout);
        CorridorPanel3_7Layout.setHorizontalGroup(
            CorridorPanel3_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_7Layout.setVerticalGroup(
            CorridorPanel3_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_8.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_8.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_8Layout = new javax.swing.GroupLayout(CorridorPanel3_8);
        CorridorPanel3_8.setLayout(CorridorPanel3_8Layout);
        CorridorPanel3_8Layout.setHorizontalGroup(
            CorridorPanel3_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_8Layout.setVerticalGroup(
            CorridorPanel3_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_9.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_9.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_9Layout = new javax.swing.GroupLayout(CorridorPanel3_9);
        CorridorPanel3_9.setLayout(CorridorPanel3_9Layout);
        CorridorPanel3_9Layout.setHorizontalGroup(
            CorridorPanel3_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_9Layout.setVerticalGroup(
            CorridorPanel3_9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        CorridorPanel3_10.setBackground(new java.awt.Color(255, 255, 255));
        CorridorPanel3_10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        CorridorPanel3_10.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout CorridorPanel3_10Layout = new javax.swing.GroupLayout(CorridorPanel3_10);
        CorridorPanel3_10.setLayout(CorridorPanel3_10Layout);
        CorridorPanel3_10Layout.setHorizontalGroup(
            CorridorPanel3_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        CorridorPanel3_10Layout.setVerticalGroup(
            CorridorPanel3_10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        PaymentHallLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PaymentHallLabel.setText("PaymentHall");

        PaymentHallPanel1.setBackground(new java.awt.Color(255, 255, 255));
        PaymentHallPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PaymentHallPanel1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout PaymentHallPanel1Layout = new javax.swing.GroupLayout(PaymentHallPanel1);
        PaymentHallPanel1.setLayout(PaymentHallPanel1Layout);
        PaymentHallPanel1Layout.setHorizontalGroup(
            PaymentHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        PaymentHallPanel1Layout.setVerticalGroup(
            PaymentHallPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        PaymentHallPanel2.setBackground(new java.awt.Color(255, 255, 255));
        PaymentHallPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PaymentHallPanel2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout PaymentHallPanel2Layout = new javax.swing.GroupLayout(PaymentHallPanel2);
        PaymentHallPanel2.setLayout(PaymentHallPanel2Layout);
        PaymentHallPanel2Layout.setHorizontalGroup(
            PaymentHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        PaymentHallPanel2Layout.setVerticalGroup(
            PaymentHallPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        PaymentPointLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PaymentPointLabel.setText("PaymentPoint");

        PaymentPointPanel.setBackground(new java.awt.Color(255, 255, 255));
        PaymentPointPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PaymentPointPanel.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout PaymentPointPanelLayout = new javax.swing.GroupLayout(PaymentPointPanel);
        PaymentPointPanel.setLayout(PaymentPointPanelLayout);
        PaymentPointPanelLayout.setHorizontalGroup(
            PaymentPointPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        PaymentPointPanelLayout.setVerticalGroup(
            PaymentPointPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setPreferredSize(new java.awt.Dimension(61, 52));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(OIS)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PaymentPointLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CorridorHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OutSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CorridorHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CorridorHallPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(CorridorHallLabel)
                    .addComponent(OutSideLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PaymentHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PaymentHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PaymentHallLabel)
                    .addComponent(PaymentPointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(EntranceHallLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EntranceHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(CorridorPanel2_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CorridorPanel3_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CorridorPanel3_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(CorridorPanel2_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(CorridorPanel1_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(CorridorPanel1_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CorridorPanel1_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(CorridorPanel2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CorridorPanel2_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(CorridorPanel2_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CorridorPanel2_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(CorridorPanel3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CorridorPanel3_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(CorridorPanel3_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(CorridorPanel3_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel1_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(CorridorLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel1_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel1_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel1_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel2_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel2_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel2_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel2_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel3_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel3_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(CorridorPanel3_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(CorridorPanel3_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EntranceHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EntranceHallPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EntranceHallPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EntranceHallPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EntranceHallPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(OIS)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OutSideLabel)
                    .addComponent(EntranceHallLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EntranceHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OutSidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EntranceHallPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EntranceHallPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EntranceHallPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EntranceHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EntranceHallPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CorridorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CorridorPanel1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CorridorPanel1_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel1_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CorridorPanel2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CorridorPanel2_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel2_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CorridorPanel3_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel3_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel3_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorPanel3_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CorridorHallLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CorridorHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorHallPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CorridorHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(PaymentHallLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(PaymentHallPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PaymentHallPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PaymentPointLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PaymentPointPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CorridorPanel3_10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CorridorPanel3_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CorridorPanel3_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CorridorPanel3_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CorridorPanel3_9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OIS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CorridorHallLabel;
    private javax.swing.JPanel CorridorHallPanel1;
    private javax.swing.JPanel CorridorHallPanel2;
    private javax.swing.JPanel CorridorHallPanel3;
    private javax.swing.JLabel CorridorLabel;
    private javax.swing.JPanel CorridorPanel1_1;
    private javax.swing.JPanel CorridorPanel1_10;
    private javax.swing.JPanel CorridorPanel1_2;
    private javax.swing.JPanel CorridorPanel1_3;
    private javax.swing.JPanel CorridorPanel1_4;
    private javax.swing.JPanel CorridorPanel1_5;
    private javax.swing.JPanel CorridorPanel1_6;
    private javax.swing.JPanel CorridorPanel1_7;
    private javax.swing.JPanel CorridorPanel1_8;
    private javax.swing.JPanel CorridorPanel1_9;
    private javax.swing.JPanel CorridorPanel2_1;
    private javax.swing.JPanel CorridorPanel2_10;
    private javax.swing.JPanel CorridorPanel2_2;
    private javax.swing.JPanel CorridorPanel2_3;
    private javax.swing.JPanel CorridorPanel2_4;
    private javax.swing.JPanel CorridorPanel2_5;
    private javax.swing.JPanel CorridorPanel2_6;
    private javax.swing.JPanel CorridorPanel2_7;
    private javax.swing.JPanel CorridorPanel2_8;
    private javax.swing.JPanel CorridorPanel2_9;
    private javax.swing.JPanel CorridorPanel3_1;
    private javax.swing.JPanel CorridorPanel3_10;
    private javax.swing.JPanel CorridorPanel3_2;
    private javax.swing.JPanel CorridorPanel3_3;
    private javax.swing.JPanel CorridorPanel3_4;
    private javax.swing.JPanel CorridorPanel3_5;
    private javax.swing.JPanel CorridorPanel3_6;
    private javax.swing.JPanel CorridorPanel3_7;
    private javax.swing.JPanel CorridorPanel3_8;
    private javax.swing.JPanel CorridorPanel3_9;
    private javax.swing.JLabel EntranceHallLabel;
    private javax.swing.JPanel EntranceHallPanel1;
    private javax.swing.JPanel EntranceHallPanel2;
    private javax.swing.JPanel EntranceHallPanel3;
    private javax.swing.JPanel EntranceHallPanel4;
    private javax.swing.JPanel EntranceHallPanel5;
    private javax.swing.JPanel EntranceHallPanel6;
    private javax.swing.JLabel OIS;
    private javax.swing.JLabel OutSideLabel;
    private javax.swing.JPanel OutSidePanel;
    private javax.swing.JLabel PaymentHallLabel;
    private javax.swing.JPanel PaymentHallPanel1;
    private javax.swing.JPanel PaymentHallPanel2;
    private javax.swing.JLabel PaymentPointLabel;
    private javax.swing.JPanel PaymentPointPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
