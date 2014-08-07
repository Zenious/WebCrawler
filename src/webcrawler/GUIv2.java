/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import page_utils.Page;
import threadhandle.ExecutorHandler;

/**
 *
 * @author Zheng Wei
 */
public class GUIv2 extends javax.swing.JFrame {

    private int emptyRow = 0;
    public static List<String> seeds = Collections.synchronizedList(new ArrayList<String>());
    public static ExecutorHandler ex;
    public static List<Page> donePages = Collections.synchronizedList(new ArrayList<Page>());
    public static int numberOfURLs = 15;
    public static DefaultTableModel dtm;
    private int noOfDownload = 5;
    private int noOfProcess = 5;

    /**
     * Creates new form GUIv2
     */
    public GUIv2() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        sourceCodePanel = new javax.swing.JScrollPane();
        sourceCodeArea = new javax.swing.JTextArea();
        sourceCodeLabel = new javax.swing.JLabel();
        referencePanel = new javax.swing.JScrollPane();
        referenceList = new javax.swing.JList();
        referenceLabel = new javax.swing.JLabel();
        openInBrowserBtn = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        pageScrollPane = new javax.swing.JScrollPane();
        pageTable = new javax.swing.JTable(){
            public String getToolTipText(MouseEvent event){
                String tip = null;
                java.awt.Point p = event.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if(realColumnIndex == 0){
                    tip = getValueAt(rowIndex, colIndex).toString();
                }else{
                    tip = super.getToolTipText(event);
                }
                return tip;
            }
        };
        buttonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        seedPanel = new javax.swing.JPanel();
        seedInput = new javax.swing.JTextField();
        seedLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        statusCode = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        closeMenu = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        noOfSitesMenu = new javax.swing.JMenuItem();
        downloadThreadMenu = new javax.swing.JMenu();
        processingThreadMenu = new javax.swing.JMenu();

        sourceCodeArea.setEditable(false);
        sourceCodeArea.setColumns(20);
        sourceCodeArea.setLineWrap(true);
        sourceCodeArea.setRows(5);
        sourceCodePanel.setViewportView(sourceCodeArea);

        sourceCodeLabel.setText("Source Code:");

        referenceList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        referencePanel.setViewportView(referenceList);

        referenceLabel.setText("References:");

        openInBrowserBtn.setText("Open in browser");
        openInBrowserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openInBrowserBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sourceCodePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sourceCodeLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jFrame1Layout.createSequentialGroup()
                                .addComponent(referenceLabel)
                                .addGap(0, 74, Short.MAX_VALUE))
                            .addComponent(referencePanel)))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addComponent(openInBrowserBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sourceCodeLabel)
                    .addComponent(referenceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(referencePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(sourceCodePanel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openInBrowserBtn)
                .addGap(14, 14, 14))
        );

        FileFilter filter = new FileNameExtensionFilter(".crawl Files", "crawl");
        jFileChooser1.setFileFilter(filter);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WebCrawler - Java");

        pageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "URL", " ", "Status", "No Of References"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        pageTable.getColumn (

            " ").setCellRenderer(new ProgressBarRender());
        pageTable.setFillsViewportHeight (

            true);
        pageScrollPane.setViewportView(pageTable);
        pageTable.addMouseListener ( new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    try {
                        sourceCodeArea.setText(donePages.get(row).getContent().toString());
                        DefaultListModel model = new DefaultListModel();
                        for (String ref : donePages.get(row).getReferences()) {
                            model.addElement(ref);
                        }
                        referenceList.setModel(model);
                    } catch (InterruptedException error) {
                    }
                    jFrame1.pack();
                    jFrame1.setTitle("Source Code & References - " + donePages.get(row).getLink());
                    jFrame1.setVisible(true);
                }
            }
        }

    );

    addButton.setLabel("Add");
    addButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            addButtonActionPerformed(evt);
        }
    });

    submitButton.setLabel("Start");
    submitButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            submitButtonActionPerformed(evt);
        }
    });

    clearBtn.setText("Clear");
    clearBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            clearBtnActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
    buttonPanel.setLayout(buttonPanelLayout);
    buttonPanelLayout.setHorizontalGroup(
        buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(buttonPanelLayout.createSequentialGroup()
            .addGap(8, 8, 8)
            .addComponent(addButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(6, 6, 6))
        .addGroup(buttonPanelLayout.createSequentialGroup()
            .addGap(35, 35, 35)
            .addComponent(clearBtn)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    buttonPanelLayout.setVerticalGroup(
        buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(buttonPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(submitButton)
                .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(clearBtn))
    );

    seedInput.setText("Enter Website...");
    seedInput.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            seedInputActionPerformed(evt);
        }
    });
    seedInput.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            seedInputFocusGained(evt);
        }
        public void focusLost(java.awt.event.FocusEvent evt) {
            seedInputFocusLost(evt);
        }
    });

    seedLabel.setText("Seed :");

    statusLabel.setText("Status : ");

    statusCode.setForeground(new java.awt.Color(0, 255, 0));
    statusCode.setText("Ready!");

    javax.swing.GroupLayout seedPanelLayout = new javax.swing.GroupLayout(seedPanel);
    seedPanel.setLayout(seedPanelLayout);
    seedPanelLayout.setHorizontalGroup(
        seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, seedPanelLayout.createSequentialGroup()
            .addGroup(seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(statusLabel)
                .addComponent(seedLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(seedInput, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(statusCode))
            .addGap(8, 8, 8))
    );
    seedPanelLayout.setVerticalGroup(
        seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(seedPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(seedInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(seedLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
            .addGroup(seedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(statusLabel)
                .addComponent(statusCode)))
    );

    fileMenu.setText("File");

    openMenu.setText("Open");
    openMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            openMenuActionPerformed(evt);
        }
    });
    fileMenu.add(openMenu);

    saveMenu.setText("Save");
    saveMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            saveMenuActionPerformed(evt);
        }
    });
    fileMenu.add(saveMenu);

    closeMenu.setText("Close");
    closeMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            closeMenuActionPerformed(evt);
        }
    });
    fileMenu.add(closeMenu);

    menuBar.add(fileMenu);

    editMenu.setText("Edit");

    noOfSitesMenu.setText("Set Number of Websites to Crawl");
    noOfSitesMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            noOfSitesMenuActionPerformed(evt);
        }
    });
    editMenu.add(noOfSitesMenu);

    downloadThreadMenu.setText("Number of Download Threads");
    editMenu.add(downloadThreadMenu);
    downloadThreadBG = new ButtonGroup();
    ArrayList<JCheckBoxMenuItem> downloadThreadChoices = new ArrayList<JCheckBoxMenuItem>();
    for(int i = 1; i<11; i++){
        downloadThreadChoices.add(new JCheckBoxMenuItem(String.valueOf(i)));
    }
    for(JCheckBoxMenuItem checkbox : downloadThreadChoices){
        if (checkbox.getText().equals(String.valueOf(noOfDownload))){
            checkbox.setSelected(true);
        }
        checkbox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent){
                JCheckBoxMenuItem checkbox = (JCheckBoxMenuItem)changeEvent.getSource();
                if (checkbox.isSelected()) noOfDownload = Integer.parseInt(checkbox.getText());
            }
        });
        downloadThreadMenu.add(checkbox);
        downloadThreadBG.add(checkbox);
    }

    processingThreadMenu.setText("Number of Processing Threads");
    editMenu.add(processingThreadMenu);
    ButtonGroup processThreadBG = new ButtonGroup();

    ArrayList<JCheckBoxMenuItem> processThreadChoices = new ArrayList<JCheckBoxMenuItem>();
    for(int i = 1; i<11; i++){
        processThreadChoices.add(new JCheckBoxMenuItem(String.valueOf(i)));
    }
    for(JCheckBoxMenuItem checkbox : processThreadChoices){
        if (checkbox.getText().equals(String.valueOf(noOfProcess))){
            checkbox.setSelected(true);
        }
        checkbox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent){
                JCheckBoxMenuItem checkbox = (JCheckBoxMenuItem)changeEvent.getSource();
                if (checkbox.isSelected()) noOfProcess = Integer.parseInt(checkbox.getText());
            }
        });
        processingThreadMenu.add(checkbox);
        processThreadBG.add(checkbox);
    }

    menuBar.add(editMenu);

    setJMenuBar(menuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(pageScrollPane)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(seedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pageScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(seedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        if (seedInput.getText().matches("http://(\\w+\\.)+(\\w+)")) {
            if (seeds.contains(seedInput.getText())) {
                JOptionPane.showMessageDialog(pageScrollPane, "Url already inside list!", "Duplicated url", JOptionPane.INFORMATION_MESSAGE);
            } else {
                seeds.add(seedInput.getText());
                dtm = (DefaultTableModel) pageTable.getModel();
                dtm.addRow(new Object[][]{null, null, null, null});
                while (dtm.getValueAt(emptyRow, 0) != null) {
                    emptyRow++;
                }
                dtm.setValueAt(seedInput.getText(), emptyRow, 0);
                dtm.setValueAt(0, emptyRow, 1);
                dtm.setValueAt("Queued", emptyRow, 2);
            }
            seedInput.setText("Enter Website...");
        } else if (seedInput.getText().equals("Enter Website...")) {
            JOptionPane.showMessageDialog(pageScrollPane, "Please Enter a url.", "No Url", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(pageScrollPane, "Please Enter a valid url.", "Invalid url", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void seedInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seedInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_seedInputActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        ex = new ExecutorHandler(noOfDownload, noOfProcess, seeds);
        ex.start();
        statusCode.setText("Crawling...");
        statusCode.setForeground(Color.red);
        //DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        //  for (int i = 0; i < dtm.getRowCount(); i++) {
        //    if(dtm.getValueAt(i, 2).toString().equals("Queued")){
        //      UrlHanderWorker uhw = new UrlHanderWorker(dtm, jTextField1.getText(), 0);
        //      uhw.execute();
        //    }
        //}

    }//GEN-LAST:event_submitButtonActionPerformed

    private void seedInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seedInputFocusGained
        // TODO add your handling code here:
        if (seedInput.getText().equals("Enter Website...")) {
            seedInput.setText("");
        }
    }//GEN-LAST:event_seedInputFocusGained

    private void seedInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seedInputFocusLost
        // TODO add your handling code here:
        if (seedInput.getText().isEmpty()) {
            seedInput.setText("Enter Website...");
        }
    }//GEN-LAST:event_seedInputFocusLost

    private void closeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuActionPerformed
        // TODO add your handling code here:
        System.exit(ABORT);
    }//GEN-LAST:event_closeMenuActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // TODO add your handling code here:
        seeds.clear();
        donePages.clear();
        dtm.setRowCount(0);
    }//GEN-LAST:event_clearBtnActionPerformed

    private void noOfSitesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noOfSitesMenuActionPerformed
        // TODO add your handling code here:
        int urlsSize = 0;
        do {
            String input = JOptionPane.showInputDialog("Enter number of website to crawl.");
            try {
                urlsSize = Integer.parseInt(input);
                if (urlsSize <= 0) {
                    JOptionPane.showMessageDialog(pageScrollPane, "Number of Urls cannot be less than 1.");
                    urlsSize = 0;
                }
            } catch (NumberFormatException e) {
                urlsSize = 0;
            }
        } while (urlsSize == 0);
        numberOfURLs = urlsSize;

    }//GEN-LAST:event_noOfSitesMenuActionPerformed

    private void openInBrowserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openInBrowserBtnActionPerformed
        // TODO add your handling code here:
        if (Desktop.isDesktopSupported()) {
            try {
                int hyperlinkIndex = jFrame1.getTitle().lastIndexOf(" ");
                Desktop.getDesktop().browse(new URI(jFrame1.getTitle().substring(hyperlinkIndex + 1)));
            } catch (URISyntaxException ex) {
                Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openInBrowserBtnActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        // TODO add your handling code here:
        int returnVal = jFileChooser1.showOpenDialog(openMenu);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser1.getSelectedFile();
            if (file.isFile()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    donePages = (List< Page>) ois.readObject();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            for (Page page : donePages){
                dtm = (DefaultTableModel) pageTable.getModel();
                dtm.addRow(new Object[][]{null, null, null, null});
                while (dtm.getValueAt(emptyRow, 0) != null) {
                    emptyRow++;
                }
                dtm.setValueAt(page.getLink(), emptyRow, 0);
                dtm.setValueAt(100, emptyRow, 1);
                dtm.setValueAt("Downloaded", emptyRow, 2);
                dtm.setValueAt(page.getReferences().size(), emptyRow, 3);
            }
        }
    }//GEN-LAST:event_openMenuActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        // TODO add your handling code here:
        int returnVal = jFileChooser1.showDialog(openMenu, "Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            FileOutputStream fos = null;
            try {
                File file = jFileChooser1.getSelectedFile();
                System.out.println("File selected : " + file.getName());
                fos = new FileOutputStream(file + ".crawl");
                try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(donePages);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(GUIv2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_saveMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIv2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIv2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIv2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIv2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIv2().setVisible(true);
            }
        });
    }

    public class ProgressBarRender extends JProgressBar implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int progress = 0;
            if (value instanceof Float) {
                progress = Math.round((float) value / 100);
                setValue(progress);
            } else if (value instanceof Integer) {
                progress = (int) value;
                setValue(progress);
            } else if (value instanceof String) {
                setString((String) value);
            }
            setStringPainted(true);
            return this;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearBtn;
    private javax.swing.JMenuItem closeMenu;
    private javax.swing.JMenu downloadThreadMenu;
    private javax.swing.ButtonGroup downloadThreadBG;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem noOfSitesMenu;
    private javax.swing.JButton openInBrowserBtn;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JScrollPane pageScrollPane;
    private javax.swing.JTable pageTable;
    private javax.swing.JMenu processingThreadMenu;
    private javax.swing.JLabel referenceLabel;
    private javax.swing.JList referenceList;
    private javax.swing.JScrollPane referencePanel;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JTextField seedInput;
    private javax.swing.JLabel seedLabel;
    private javax.swing.JPanel seedPanel;
    private javax.swing.JTextArea sourceCodeArea;
    private javax.swing.JLabel sourceCodeLabel;
    private javax.swing.JScrollPane sourceCodePanel;
    public static javax.swing.JLabel statusCode;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
