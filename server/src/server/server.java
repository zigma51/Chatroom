package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class server extends javax.swing.JFrame {

    ArrayList clientOutputStreams;
    ArrayList<String> users;

    public class ClientHandler implements Runnable {

        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ta_chat.append("Unexpected error... \n");
            }

        }

        @Override
        public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    ta_chat.append("Received: " + message + "\n");
                    data = message.split(":");

                    for (String token : data) {
                        ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    } else if (data[2].equals(chat)) {
                        tellEveryone(message);
                    } else {
                        ta_chat.append("No Conditions were met. \n");
                    }
                }
            } catch (Exception ex) {
                ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }

    public server() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_start = new javax.swing.JButton();
        b_end = new javax.swing.JButton();
        b_users = new javax.swing.JButton();
        b_clear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setMaximumSize(new java.awt.Dimension(690, 510));
        setMinimumSize(new java.awt.Dimension(690, 510));
        setPreferredSize(new java.awt.Dimension(690, 510));
        setResizable(false);
        getContentPane().setLayout(null);

        ta_chat.setColumns(20);
        ta_chat.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 70, 629, 290);

        b_start.setBackground(new java.awt.Color(204, 255, 204));
        b_start.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        b_start.setText("START");
        b_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_startActionPerformed(evt);
            }
        });
        getContentPane().add(b_start);
        b_start.setBounds(30, 390, 120, 39);

        b_end.setBackground(new java.awt.Color(204, 255, 204));
        b_end.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        b_end.setText("END");
        b_end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_endActionPerformed(evt);
            }
        });
        getContentPane().add(b_end);
        b_end.setBounds(30, 450, 120, 35);

        b_users.setBackground(new java.awt.Color(204, 255, 204));
        b_users.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        b_users.setText("Online Users");
        b_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usersActionPerformed(evt);
            }
        });
        getContentPane().add(b_users);
        b_users.setBounds(507, 390, 150, 39);

        b_clear.setBackground(new java.awt.Color(204, 255, 204));
        b_clear.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        b_clear.setText("Clear");
        b_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_clearActionPerformed(evt);
            }
        });
        getContentPane().add(b_clear);
        b_clear.setBounds(510, 450, 150, 35);

        jLabel2.setFont(new java.awt.Font("Imprint MT Shadow", 3, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SERVER");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(200, 0, 270, 60);

        setSize(new java.awt.Dimension(710, 559));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_clearActionPerformed
        ta_chat.setText("");   // TODO add your handling code here:
    }//GEN-LAST:event_b_clearActionPerformed

    private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_usersActionPerformed
        ta_chat.append("\n Online users : \n");
        for (String current_user : users) {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_b_usersActionPerformed

    private void b_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_startActionPerformed
        Thread starter = new Thread(new ServerStart());
        starter.start();

        ta_chat.append("Server started...\n");        // TODO add your handling code here:
    }//GEN-LAST:event_b_startActionPerformed

    private void b_endActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_endActionPerformed
        try {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        ta_chat.append("Server stopping... \n");

        ta_chat.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_b_endActionPerformed

    public class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();

            try {
                ServerSocket serverSock = new ServerSocket(8080);

                while (true) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    ta_chat.append("Got a connection. \n");
                }
            } catch (Exception ex) {
                ta_chat.append("Error making a connection. \n");
            }
        }
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ta_chat.append("Before " + name + " added. \n");
        users.add(name);
        ta_chat.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                ta_chat.append("Sending: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } catch (Exception ex) {
                ta_chat.append("Error telling everyone. \n");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_clear;
    private javax.swing.JButton b_end;
    private javax.swing.JButton b_start;
    private javax.swing.JButton b_users;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration//GEN-END:variables
}
