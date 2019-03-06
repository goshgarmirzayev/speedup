/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.panel.queryresult;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.AutoComplete;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUTableBean;
import com.bsptechs.main.bean.ui.frame.SetQueryLocation;
import com.bsptechs.main.bean.ui.popup.UiPopupQuery;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import com.bsptechs.main.util.ImageUtil;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import com.bsptechs.main.util.LogUtil;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 *
 * @author sarkhanrasullu
 */
public class PanelQueryEditor extends javax.swing.JPanel {

    private static final DatabaseDAOInter db = new DatabaseDAOImpl();
    private static PanelQueryEditor panelQuery = null;
    private LinkedHashSet<AutoComplete> wordlist;

    public PanelQueryEditor(SUConnectionBean connection, SUDatabaseBean database, String queryStr) throws ClassNotFoundException, SQLException {
        initComponents();
        preparePanel(connection, database);
        txtQuery.setText(queryStr);
        setIcon();
        fillWordlist();
    }

    public void setIcon() {
        btnSave.setIcon(ImageUtil.getIconforQueryPanel("querypanel/save.png"));
        btnQueryBuilder.setIcon(ImageUtil.getIconforQueryPanel("querypanel/querybuilder.png"));
        btnBeautfySQL.setIcon(ImageUtil.getIconforQueryPanel("querypanel/beauty.png"));
        btnCodeSnipped.setIcon(ImageUtil.getIconforQueryPanel("querypanel/snippet.png"));
        btnText.setIcon(ImageUtil.getIconforQueryPanel("querypanel/text-document.png"));
        btnExportResult.setIcon(ImageUtil.getIconforQueryPanel("querypanel/export-file.png"));
        btnRun.setIcon(ImageUtil.getIconforQueryPanel("querypanel/play-arrow.png"));
        btnstop.setIcon(ImageUtil.getIconforQueryPanel("querypanel/stop.png"));
        btnexplain.setIcon(ImageUtil.getIconforQueryPanel("querypanel/explain-.png"));
    }

    public void fillWordlist() {
        wordlist = db.getAllKeyWords(getSelectedConnection());

    }
    

    public class SuggestionPanel {

        private JList list;
        private JPopupMenu popupMenu;
        private String subWord;
        private final int insertionPosition;

        public SuggestionPanel(JTextArea textarea, int position, String subWord, Point location) {
            this.insertionPosition = position;
            this.subWord = subWord;
            popupMenu = new JPopupMenu();
            popupMenu.removeAll();
            popupMenu.setOpaque(false);
            popupMenu.setBorder(null);
            popupMenu.add(list = createSuggestionList(position, subWord), BorderLayout.CENTER);
            popupMenu.show(textarea, location.x, textarea.getBaseline(0, 0) + location.y);
        }

        public void hide() {
            popupMenu.setVisible(false);
            if (suggestion == this) {
                suggestion = null;
            }
        }

        private JList createSuggestionList(final int position, final String subWord) {
            Object[] data = null;
            List<AutoComplete> result = null;
            if (subWord.endsWith(".")) {
                List<SUDatabaseBean> databases = getSelectedConnection().getDatabases();
                SUDatabaseBean typedDatabase = null;
                for (int i = 0; i < databases.size(); i++) {
                    if (databases.get(i).getName().startsWith(subWord)) {
                        typedDatabase = databases.get(i);
                    }

                }
                List<SUTableBean> tables = db.getAllTables(typedDatabase);
                System.out.println(tables);
                data = new Object[tables.size()];
                for (int i = 0; i < tables.size(); i++) {
                    data[i] = tables.get(i).getName();
                }
            } else {
                result = wordlist.stream()
                        .filter(item -> item.getName().startsWith(subWord))
                        .collect(Collectors.toList());
                data = new Object[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    data[i] = result.get(i).getName();
                }
            }
            JList list = new JList(data);
            list.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);
            list.setSize(100, 300);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        insertSelection();
                    }
                }
            });
            return list;
        }

        public boolean insertSelection() {
            if (list.getSelectedValue() != null) {
                try {
                    final String selectedSuggestion = ((String) list.getSelectedValue()).substring(subWord.length());
                    txtQuery.getDocument().insertString(insertionPosition, selectedSuggestion, null);
                    return true;
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                hideSuggestion();
            }
            return false;
        }

        public void moveUp() {
            int index = Math.min(list.getSelectedIndex() - 1, 0);
            selectIndex(index);
        }

        public void moveDown() {
            int index = Math.min(list.getSelectedIndex() + 1, list.getModel().getSize() - 1);
            selectIndex(index);
        }

        private void selectIndex(int index) {
            final int position = txtQuery.getCaretPosition();
            list.setSelectedIndex(index);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    txtQuery.setCaretPosition(position);
                }
            ;
        }

    
    );
        }
    }

    private SuggestionPanel suggestion;

    protected void showSuggestionLater() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showSuggestion();
            }

        });
    }

    protected void showSuggestion() {
        hideSuggestion();
        final int position = txtQuery.getCaretPosition();
        Point location;
        try {
            location = txtQuery.modelToView(position).getLocation();
        } catch (BadLocationException e2) {
            e2.printStackTrace();
            return;
        }
        String text = txtQuery.getText();
        int start = Math.max(0, position - 1);
        while (start > 0) {
            if (!Character.isWhitespace(text.charAt(start))) {
                start--;
            } else {
                start++;
                break;
            }
        }
        if (start > position) {
            return;
        }
        final String subWord = text.substring(start, position);
        if (subWord.length() < 2) {
            return;
        }
        suggestion = new SuggestionPanel(txtQuery, position, subWord, location);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                txtQuery.requestFocusInWindow();
            }
        });
    }

    private void hideSuggestion() {
        if (suggestion != null) {
            suggestion.hide();
        }
    }

    protected void initUI() {
        txtQuery.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (suggestion != null) {
                        if (suggestion.insertSelection()) {
                            e.consume();
                            final int position = txtQuery.getCaretPosition();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        txtQuery.getDocument().remove(position - 1, 1);
                                    } catch (BadLocationException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && suggestion != null) {
                    suggestion.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_UP && suggestion != null) {
                    suggestion.moveUp();
                } else if (Character.isLetterOrDigit(e.getKeyChar())) {
                    showSuggestionLater();
                } else if (Character.isWhitespace(e.getKeyChar())) {
                    hideSuggestion();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });

    }

    public final void preparePanel(SUConnectionBean connection, SUDatabaseBean database) {
//        pnlResult.setVisible(false);
        prepareConnectionCombobox(connection);
        prepareDatabasesCombobox(connection, database);
    }

    public void prepareConnectionCombobox(SUConnectionBean connection) {
        cbConnections.removeAllItems();
        List<SUConnectionBean> list = Main.instance().getConnectionTree().getConnectionBeans();
        if (list.size() == 0) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            cbConnections.addItem(list.get(i));
        }

        if (connection == null) {
            connection = list.get(0);
        }
        cbConnections.setSelectedItem(connection);
    }

    public void prepareDatabasesCombobox(SUConnectionBean connection, SUDatabaseBean database) {
        if (connection == null) {
            return;
        }
        LogUtil.log("prepareDatabasesCombobox=" + database);
        cbDatabases.removeAllItems();
        List<SUDatabaseBean> databases = connection.getDatabases();
        if (databases == null) {
            databases = db.getAllDatabases(connection);
        }
        for (int i = 0; i < databases.size(); i++) {
            cbDatabases.addItem(databases.get(i));
        }
        cbDatabases.setSelectedItem(database);
    }

    public void btnenter(JButton btn) {
        btn.setBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.white));
    }

    public void btnexit(JButton btn) {
        btn.setBorder(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        pnlQuery = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtQuery = new javax.swing.JTextArea();
        pnlControllBtns = new javax.swing.JPanel();
        cbConnections = new javax.swing.JComboBox<>();
        cbDatabases = new javax.swing.JComboBox<>();
        btnRun = new javax.swing.JButton();
        btnstop = new javax.swing.JButton();
        btnexplain = new javax.swing.JButton();
        pnlButtons = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnQueryBuilder = new javax.swing.JButton();
        btnBeautfySQL = new javax.swing.JButton();
        btnCodeSnipped = new javax.swing.JButton();
        btnText = new javax.swing.JButton();
        btnExportResult = new javax.swing.JButton();
        pnlQueryResult = new com.bsptechs.main.bean.ui.panel.queryresult.PanelQueryResult();

        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlQuery.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlQuery.setPreferredSize(new java.awt.Dimension(300, 300));

        txtQuery.setColumns(20);
        txtQuery.setRows(5);
        txtQuery.setMinimumSize(new java.awt.Dimension(0, 0));
        txtQuery.setPreferredSize(new java.awt.Dimension(0, 0));
        txtQuery.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQueryKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQueryKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtQuery);

        cbConnections.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbConnections.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbConnectionsItemStateChanged(evt);
            }
        });
        cbConnections.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbConnectionsActionPerformed(evt);
            }
        });

        cbDatabases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDatabasesActionPerformed(evt);
            }
        });

        btnRun.setText("Run");
        btnRun.setBorder(null);
        btnRun.setContentAreaFilled(false);
        btnRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRunMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRunMouseEntered(evt);
            }
        });
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        btnstop.setText("Stop");
        btnstop.setBorder(null);
        btnstop.setContentAreaFilled(false);
        btnstop.setPreferredSize(new java.awt.Dimension(51, 23));
        btnstop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnstopMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnstopMouseEntered(evt);
            }
        });

        btnexplain.setText("Explain");
        btnexplain.setBorder(null);
        btnexplain.setContentAreaFilled(false);
        btnexplain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnexplainMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnexplainMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlControllBtnsLayout = new javax.swing.GroupLayout(pnlControllBtns);
        pnlControllBtns.setLayout(pnlControllBtnsLayout);
        pnlControllBtnsLayout.setHorizontalGroup(
            pnlControllBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControllBtnsLayout.createSequentialGroup()
                .addComponent(cbConnections, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbDatabases, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRun, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnstop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnexplain, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlControllBtnsLayout.setVerticalGroup(
            pnlControllBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRun, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlControllBtnsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cbConnections)
                .addComponent(cbDatabases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnexplain, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnstop, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlButtons.setBackground(new java.awt.Color(204, 204, 204));

        btnSave.setBackground(new java.awt.Color(102, 102, 255));
        btnSave.setText("Save");
        btnSave.setBorder(null);
        btnSave.setContentAreaFilled(false);
        btnSave.setMaximumSize(new java.awt.Dimension(39, 17));
        btnSave.setMinimumSize(new java.awt.Dimension(39, 17));
        btnSave.setPreferredSize(new java.awt.Dimension(44, 17));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnQueryBuilder.setText("Query bulder");
        btnQueryBuilder.setBorder(null);
        btnQueryBuilder.setContentAreaFilled(false);
        btnQueryBuilder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQueryBuilderMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQueryBuilderMouseEntered(evt);
            }
        });

        btnBeautfySQL.setText("Beautfy SQL");
        btnBeautfySQL.setBorder(null);
        btnBeautfySQL.setContentAreaFilled(false);
        btnBeautfySQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBeautfySQLMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBeautfySQLMouseEntered(evt);
            }
        });
        btnBeautfySQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeautfySQLActionPerformed(evt);
            }
        });

        btnCodeSnipped.setText("Code Snipped");
        btnCodeSnipped.setBorder(null);
        btnCodeSnipped.setContentAreaFilled(false);
        btnCodeSnipped.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCodeSnippedMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCodeSnippedMouseEntered(evt);
            }
        });

        btnText.setText("Text");
        btnText.setBorder(null);
        btnText.setContentAreaFilled(false);
        btnText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTextMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTextMouseEntered(evt);
            }
        });
        btnText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTextActionPerformed(evt);
            }
        });

        btnExportResult.setText("Export Result");
        btnExportResult.setBorder(null);
        btnExportResult.setContentAreaFilled(false);
        btnExportResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExportResultMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExportResultMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQueryBuilder, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBeautfySQL, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCodeSnipped, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportResult, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnQueryBuilder, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBeautfySQL, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCodeSnipped, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnExportResult, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlQueryLayout = new javax.swing.GroupLayout(pnlQuery);
        pnlQuery.setLayout(pnlQueryLayout);
        pnlQueryLayout.setHorizontalGroup(
            pnlQueryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
            .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlControllBtns, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlQueryLayout.setVerticalGroup(
            pnlQueryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQueryLayout.createSequentialGroup()
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlControllBtns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        splitPane.setLeftComponent(pnlQuery);

        pnlQueryResult.setMinimumSize(new java.awt.Dimension(0, 0));
        pnlQueryResult.setPreferredSize(new java.awt.Dimension(0, 0));
        splitPane.setRightComponent(pnlQueryResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbConnectionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbConnectionsItemStateChanged
        SUConnectionBean conn = getSelectedConnection();
        LogUtil.log("selected connnection=" + conn);
        prepareDatabasesCombobox(conn, null);
    }//GEN-LAST:event_cbConnectionsItemStateChanged

    private void cbConnectionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbConnectionsActionPerformed

    }//GEN-LAST:event_cbConnectionsActionPerformed

    private void cbDatabasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDatabasesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDatabasesActionPerformed

    private void btnRunMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRunMouseExited
        btnexit(btnRun);
    }//GEN-LAST:event_btnRunMouseExited

    private void btnRunMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRunMouseEntered
        btnenter(btnRun);
    }//GEN-LAST:event_btnRunMouseEntered

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        runQuery();
    }//GEN-LAST:event_btnRunActionPerformed

    private void btnstopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnstopMouseExited
        btnexit(btnstop);
    }//GEN-LAST:event_btnstopMouseExited

    private void btnstopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnstopMouseEntered
        btnenter(btnstop);
    }//GEN-LAST:event_btnstopMouseEntered

    private void btnexplainMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexplainMouseExited
        btnexit(btnexplain);
    }//GEN-LAST:event_btnexplainMouseExited

    private void btnexplainMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnexplainMouseEntered
        btnenter(btnexplain);
    }//GEN-LAST:event_btnexplainMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        btnexit(btnSave);
    }//GEN-LAST:event_btnSaveMouseExited

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        btnenter(btnSave);
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnQueryBuilderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQueryBuilderMouseExited
        btnexit(btnQueryBuilder);
    }//GEN-LAST:event_btnQueryBuilderMouseExited

    private void btnQueryBuilderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQueryBuilderMouseEntered
        btnenter(btnQueryBuilder);
    }//GEN-LAST:event_btnQueryBuilderMouseEntered

    private void btnBeautfySQLMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBeautfySQLMouseExited
        btnexit(btnBeautfySQL);
    }//GEN-LAST:event_btnBeautfySQLMouseExited

    private void btnBeautfySQLMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBeautfySQLMouseEntered
        btnenter(btnBeautfySQL);
    }//GEN-LAST:event_btnBeautfySQLMouseEntered

    private void btnBeautfySQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeautfySQLActionPerformed
        String s[] = txtQuery.getText().split("\\r?\\n");
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(s));
        LogUtil.log(arrList.size());
    }//GEN-LAST:event_btnBeautfySQLActionPerformed

    private void btnCodeSnippedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCodeSnippedMouseExited
        btnexit(btnCodeSnipped);
    }//GEN-LAST:event_btnCodeSnippedMouseExited

    private void btnCodeSnippedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCodeSnippedMouseEntered
        btnenter(btnCodeSnipped);
    }//GEN-LAST:event_btnCodeSnippedMouseEntered

    private void btnTextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTextMouseExited
        btnexit(btnText);

    }//GEN-LAST:event_btnTextMouseExited

    private void btnTextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTextMouseEntered
        btnenter(btnText);
    }//GEN-LAST:event_btnTextMouseEntered

    private void btnTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTextActionPerformed

    private void btnExportResultMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportResultMouseExited
        btnexit(btnExportResult);
    }//GEN-LAST:event_btnExportResultMouseExited

    private void btnExportResultMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportResultMouseEntered
        btnenter(btnExportResult);
    }//GEN-LAST:event_btnExportResultMouseEntered

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (UiPopupQuery.isDesigning) {
            UiPopupQuery.saveDesignedQuery(txtQuery.getText());
        } else {
            SetQueryLocation queryLocation = new SetQueryLocation();
            queryLocation.setQueryBody(txtQuery.getText());
            queryLocation.setVisible(true);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtQueryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQueryKeyTyped
        initUI();
    }//GEN-LAST:event_txtQueryKeyTyped

    @SuppressWarnings("null")
    private void txtQueryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQueryKeyPressed

    }//GEN-LAST:event_txtQueryKeyPressed
    public SUDatabaseBean getSelectedDatabase() {
        Object obj = cbDatabases.getSelectedItem();
        return (SUDatabaseBean) obj;
    }

    public SUConnectionBean getSelectedConnection() {
        return (SUConnectionBean) cbConnections.getSelectedItem();
    }
//    public static void viewTable(TableTreeNode table) {
//        runQuery("select * from " + table.getName());
//    }

    public PanelQueryResult getPanelQueryResult() {
        return (PanelQueryResult) pnlQueryResult;
    }

    public void runQuery() {
        String queryStr = txtQuery.getText();
        SUConnectionBean connection = getSelectedConnection();
        SUDatabaseBean database = getSelectedDatabase();
        PanelQueryResult pnl = getPanelQueryResult();
        pnl.runQuery(new SUQueryBean(connection, database, queryStr));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBeautfySQL;
    private javax.swing.JButton btnCodeSnipped;
    private javax.swing.JButton btnExportResult;
    private javax.swing.JButton btnQueryBuilder;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnText;
    private javax.swing.JButton btnexplain;
    private javax.swing.JButton btnstop;
    private javax.swing.JComboBox<com.bsptechs.main.bean.server.SUConnectionBean> cbConnections;
    private javax.swing.JComboBox<com.bsptechs.main.bean.server.SUDatabaseBean> cbDatabases;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlControllBtns;
    private javax.swing.JPanel pnlQuery;
    private com.bsptechs.main.bean.ui.panel.queryresult.PanelQueryResult pnlQueryResult;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTextArea txtQuery;
    // End of variables declaration//GEN-END:variables
}
