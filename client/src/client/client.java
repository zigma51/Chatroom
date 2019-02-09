package client;

import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import java.util.*;
import Controllers.*;
import Database.*;

public class client extends javax.swing.JFrame {

    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    int port = 8080;
    Boolean isConnected = false;

    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    
    public client() {
        initComponents();
        this.setVisible(true);
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    //--------------------------//
    public void userAdd(String data) {
        users.add(data);
    }

    //--------------------------//
    public void userRemove(String data) {
        ta_chat.append(data + " is now offline.\n");
    }

    //--------------------------//
    public void writeUsers() {
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);
        for (String token : tempList) {
            //users.append(token + "\n");
        }
    }

    //--------------------------//
    public void sendDisconnect() {
        String bye = (LoginController.user  + ": :Disconnect");
        try {
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            ta_chat.append("Could not send Disconnect message.\n");
        }
    }

    //--------------------------//
    public void Disconnect() {
        try {
            ta_chat.append("Disconnected.\n");
            sock.close();
        } catch (Exception ex) {
            ta_chat.append("Failed to disconnect. \n");
        }
        isConnected = false;
    }


    public class IncomingReader implements Runnable {

        @Override
        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {
                    data = stream.split(":");

                    if (data[2].equals(chat)) {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());
                    } else if (data[2].equals(connect)) {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        userRemove(data[0]);
                    } else if (data[2].equals(done)) {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lb_address = new javax.swing.JLabel();
        lb_port = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        tf_port = new javax.swing.JTextField();
        b_connect = new javax.swing.JButton();
        b_disconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_send = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(730, 510));
        setMinimumSize(new java.awt.Dimension(730, 510));
        setPreferredSize(new java.awt.Dimension(730, 510));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lb_address.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lb_address.setForeground(new java.awt.Color(255, 255, 255));
        lb_address.setText("Address");
        getContentPane().add(lb_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 70, -1));

        lb_port.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lb_port.setForeground(new java.awt.Color(240, 240, 240));
        lb_port.setText("Port");
        getContentPane().add(lb_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, 40, -1));

        tf_address.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_address.setForeground(new java.awt.Color(102, 102, 102));
        tf_address.setText("Local Host");
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });
        getContentPane().add(tf_address, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 117, -1));

        tf_port.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_port.setForeground(new java.awt.Color(102, 102, 102));
        tf_port.setText("8080");
        tf_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_portActionPerformed(evt);
            }
        });
        getContentPane().add(tf_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 140, -1));

        b_connect.setBackground(new java.awt.Color(204, 255, 204));
        b_connect.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        b_connect.setText("Connect");
        b_connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_connectActionPerformed(evt);
            }
        });
        getContentPane().add(b_connect, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 180, -1));

        b_disconnect.setBackground(new java.awt.Color(204, 255, 204));
        b_disconnect.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        b_disconnect.setText("Disconnect");
        b_disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_disconnectActionPerformed(evt);
            }
        });
        getContentPane().add(b_disconnect, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 180, -1));

        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        ta_chat.setForeground(new java.awt.Color(102, 102, 102));
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 690, 220));

        tf_chat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tf_chat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_chatActionPerformed(evt);
            }
        });
        tf_chat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_chatKeyPressed(evt);
            }
        });
        getContentPane().add(tf_chat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 500, -1));

        b_send.setBackground(new java.awt.Color(204, 255, 204));
        b_send.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        b_send.setText("Send");
        b_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_sendActionPerformed(evt);
            }
        });
        b_send.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                b_sendKeyPressed(evt);
            }
        });
        getContentPane().add(b_send, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, 167, -1));

        jLabel1.setFont(new java.awt.Font("Imprint MT Shadow", 3, 42)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 240, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 255, 255));
        jLabel3.setText("Message Box");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/11.jpg"))); // NOI18N
        jLabel2.setMaximumSize(new java.awt.Dimension(730, 510));
        jLabel2.setMinimumSize(new java.awt.Dimension(730, 510));
        jLabel2.setPreferredSize(new java.awt.Dimension(730, 510));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 520));

        setSize(new java.awt.Dimension(748, 551));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_addressActionPerformed

    private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_connectActionPerformed
        String n = LoginController.user.toUpperCase();
        jLabel1.setText(n);
        
        if (isConnected == false) {
            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(LoginController.user + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            } catch (Exception ex) {
                ta_chat.append("Cannot Connect! Try Again. \n");
            }

            ListenThread();

        } else if (isConnected == true) {
            ta_chat.append("You are already connected. \n");
        }
    }//GEN-LAST:event_b_connectActionPerformed

    private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_disconnectActionPerformed

        sendDisconnect();
        Disconnect();
    }//GEN-LAST:event_b_disconnectActionPerformed

    private void tf_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_portActionPerformed

    private void b_sendKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_b_sendKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_b_sendKeyPressed

    private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_sendActionPerformed

        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
                writer.println(LoginController.user + ":" + tf_chat.getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }//GEN-LAST:event_b_sendActionPerformed

    private void tf_chatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_chatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            String nothing = "";
            if ((tf_chat.getText()).equals(nothing)) {
                tf_chat.setText("");
                tf_chat.requestFocus();
            } else {
                try {
                    writer.println(LoginController.user  + ":" + tf_chat.getText() + ":" + "Chat");
                    writer.flush(); // flushes the buffer
                } catch (Exception ex) {
                    ta_chat.append("Message was not sent. \n");
                }
                tf_chat.setText("");
                tf_chat.requestFocus();
            }

            tf_chat.setText("");
            tf_chat.requestFocus();
        }
    }//GEN-LAST:event_tf_chatKeyPressed

    private void tf_chatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_chatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_chatActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_connect;
    private javax.swing.JButton b_disconnect;
    private javax.swing.JButton b_send;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_address;
    private javax.swing.JLabel lb_port;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_port;
    // End of variables declaration//GEN-END:variables
}
