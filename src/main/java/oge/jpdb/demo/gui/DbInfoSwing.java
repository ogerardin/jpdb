/*
    JPDB, a Java library to read/write Palm OS database file formats.
    Copyright (C) 2005 Olivier Gérardin

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package oge.jpdb.demo.gui;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import oge.jpdb.PalmDatabase;
import oge.jpdb.PdbFile;
import oge.jpdb.PrcFile;


/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class DbInfoSwing extends javax.swing.JFrame {

	/**
     * 
     */
    private static final long serialVersionUID = -2677947208010607104L;

    {
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


    private JMenuItem jMenuItemHelp;
    private JMenu jMenuHelp;
    private JPanel jPanelPropertiesTab;
    private JTabbedPane jTabbedPane;
    private JFormattedTextField jTextFieldCreationDate;
    private JLabel jLabel2;
    private JPanel jPanelDates;
    private JPanel jPanelGeneral;
    private JLabel jLabel1;
    private JTextField jTextFieldName;
    private JTextArea jTextAreaSortInfo;
    private JTextArea jTextAreaAppInfo;
    private JCheckBox jCheckBoxAppInfoDirty;
    private JCheckBox jCheckBoxBackup;
    private JCheckBox jCheckBoxReadOnly;
    private JCheckBox jCheckBoxResDb;
    private JMenuItem jMenuItemFileExit;
    private JSeparator jSeparator2;
    private JTextField jTextFieldFileSize;
    private JLabel jLabel3;
    private JPanel jPanelSortInfoTab;
    private JPanel jPanelAppInfoTab;
    private JCheckBox jCheckBoxOpen;
    private JCheckBox jCheckBoxBundle;
    private JCheckBox jCheckBoxRecyclable;
    private JCheckBox jCheckBoxLaunchableData;
    private JCheckBox jCheckBoxHidden;
    private JCheckBox jCheckBoxStream;
    private JCheckBox jCheckBoxCopyPrevention;
    private JCheckBox jCheckBoxResetAfterInstall;
    private JCheckBox jCheckBoxOkToInstallNewer;
    private JLabel jLabel4;
    private JTextField jTextFieldCreator;
    private JLabel jLabel8;
    private JPanel jPanelAttributes;
    private JMenuItem jMenuItemAbout;
    private JTable jTableDatabaseRecords;
    private JScrollPane jScrollPaneRecords;
    private JFormattedTextField jTextFieldModificationDate;
    private JFormattedTextField jTextFieldLastBackupDate;
    private JLabel jLabel7;
    private JTextField jTextFieldType;
    private JLabel jLabel6;
    private JLabel jLabel5;
    private JTextField jTextFieldRecords;
    private JMenuItem jMenuItemFileClose;
    private JMenuItem jMenuItemFileSaveAs;
    private JMenuItem jMenuItemFileSave;
    private JMenuItem jMenuItemFileOpen;
    private JMenuItem jMenuItemFileNew;
    private JMenu jMenuFile;
    private JMenuBar jMenuBar;
    private PalmDatabase palmDatabase;

    /**
    * Auto-generated main method to display this JFrame
    */
    public static void main(String[] args) {
        DbInfoSwing inst = new DbInfoSwing();
        inst.setVisible(true);
    }
    
    public DbInfoSwing() {
        super();
        initGUI();
        setLocationRelativeTo(null); // center on screen
    }
    
    private void initGUI() {
        try {
            BorderLayout thisLayout = new BorderLayout();
            this.getContentPane().setLayout(thisLayout);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setTitle("Palm Database Info");
            this.setSize(446, 440);
            {
                jMenuBar = new JMenuBar();
                setJMenuBar(jMenuBar);
                {
                    jMenuFile = new JMenu();
                    jMenuBar.add(jMenuFile);
                    jMenuFile.setText("File");
                    {
                        jMenuItemFileNew = new JMenuItem();
                        jMenuFile.add(jMenuItemFileNew);
                        jMenuItemFileNew.setText("New");
                    }
                    {
                        jMenuItemFileOpen = new JMenuItem();
                        jMenuFile.add(jMenuItemFileOpen);
                        jMenuItemFileOpen.setText("Open");
                        jMenuItemFileOpen
                            .addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                jMenuItemFileOpenActionPerformed(evt);
                            }
                            });
                    }
                    {
                        jMenuItemFileSave = new JMenuItem();
                        jMenuFile.add(jMenuItemFileSave);
                        jMenuItemFileSave.setText("Save");
                    }
                    {
                        jMenuItemFileSaveAs = new JMenuItem();
                        jMenuFile.add(jMenuItemFileSaveAs);
                        jMenuItemFileSaveAs.setText("Save As ...");
                        jMenuItemFileSaveAs
                            .addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                jMenuItemFileSaveAsActionPerformed(evt);
                            }
                            });
                    }
                    {
                        jMenuItemFileClose = new JMenuItem();
                        jMenuFile.add(jMenuItemFileClose);
                        jMenuItemFileClose.setText("Close");
                        jMenuItemFileClose
                            .addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                jMenuItemFileCloseActionPerformed(evt);
                            }
                            });
                    }
                    {
                        jSeparator2 = new JSeparator();
                        jMenuFile.add(jSeparator2);
                    }
                    {
                        jMenuItemFileExit = new JMenuItem();
                        jMenuFile.add(jMenuItemFileExit);
                        jMenuItemFileExit.setText("Exit");
                        jMenuItemFileExit.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                jMenuItemFileExitActionPerformed(evt);
                            }
                        });
                    }
                }
                {
                    jMenuHelp = new JMenu();
                    jMenuBar.add(jMenuHelp);
                    jMenuHelp.setText("Help");
                    {
                        jMenuItemHelp = new JMenuItem();
                        {
                            jTabbedPane = new JTabbedPane();
                            this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);
                            jTabbedPane.setPreferredSize(new java.awt.Dimension(392, 357));
                            {
                                jPanelPropertiesTab = new JPanel();
                                jTabbedPane.addTab("General", null, jPanelPropertiesTab, null);
                                BoxLayout jPanel3Layout = new BoxLayout(
                                    jPanelPropertiesTab,
                                    javax.swing.BoxLayout.Y_AXIS);
                                jPanelPropertiesTab.setLayout(jPanel3Layout);
                                {
                                    jPanelGeneral = new JPanel();
                                    jPanelPropertiesTab.add(jPanelGeneral);
                                    GridBagLayout jPanel1Layout = new GridBagLayout();
                                    jPanel1Layout.columnWidths = new int[] {0,0};
                                    jPanelGeneral.setLayout(jPanel1Layout);
                                    jPanelGeneral.setBorder(BorderFactory.createTitledBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false), "General", TitledBorder.LEADING, TitledBorder.TOP));
                                    {
                                        jLabel1 = new JLabel();
                                        jPanelGeneral.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel1.setText("Name: ");
                                    }
                                    {
                                        jTextFieldName = new JTextField();
                                        jPanelGeneral.add(jTextFieldName, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                                        jTextFieldName.setBounds(-167, -89, -10, -33);
                                    }
                                    {
                                        jLabel3 = new JLabel();
                                        jPanelGeneral.add(jLabel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel3.setText("File size: ");
                                    }
                                    {
                                        jTextFieldFileSize = new JTextField();
                                        jPanelGeneral.add(
                                            jTextFieldFileSize,
                                            new GridBagConstraints(
                                                1,
                                                1,
                                                2,
                                                1,
                                                0.0,
                                                0.0,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0),
                                                0,
                                                0));
                                        jTextFieldFileSize.setEditable(false);
                                    }
                                    {
                                        jLabel4 = new JLabel();
                                        jPanelGeneral.add(jLabel4, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel4.setText("Records: ");
                                    }
                                    {
                                        jTextFieldRecords = new JTextField();
                                        jPanelGeneral.add(jTextFieldRecords, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                                        jTextFieldRecords.setEditable(false);
                                    }
                                    {
                                        jLabel5 = new JLabel();
                                        jPanelGeneral.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel5.setText("Creator: ");
                                    }
                                    {
                                        jLabel6 = new JLabel();
                                        jPanelGeneral.add(jLabel6, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel6.setText(" Type: ");
                                    }
                                    {
                                        jTextFieldType = new JTextField();
                                        jPanelGeneral.add(
                                            jTextFieldType,
                                            new GridBagConstraints(
                                                3,
                                                4,
                                                1,
                                                1,
                                                0.0,
                                                0.0,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0,
                                                0));
                                        jTextFieldType.setPreferredSize(new java.awt.Dimension(50, 20));
                                        jTextFieldType.setSize(200, 20);
                                        jTextFieldType.setMinimumSize(new java.awt.Dimension(50, 20));
                                    }
                                    {
                                        jTextFieldCreator = new JTextField();
                                        jPanelGeneral.add(jTextFieldCreator, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jTextFieldCreator.setMinimumSize(new java.awt.Dimension(50, 20));
                                        jTextFieldCreator.setSize(50, 20);
                                        jTextFieldCreator.setPreferredSize(new java.awt.Dimension(50, 20));
                                    }
                                }
                                {
                                    jPanelDates = new JPanel();
                                    jPanelPropertiesTab.add(jPanelDates);
                                    GridBagLayout jPanel2Layout = new GridBagLayout();
                                    jPanelDates.setLayout(jPanel2Layout);
                                    jPanelDates.setBorder(BorderFactory.createTitledBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false), "Dates", TitledBorder.LEADING, TitledBorder.TOP));
                                    {
                                        jLabel2 = new JLabel();
                                        jPanelDates.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel2.setText("Creation: ");
                                    }
                                    {
                                        jTextFieldCreationDate = new JFormattedTextField();
                                        jPanelDates.add(jTextFieldCreationDate, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                                        jTextFieldCreationDate.setMinimumSize(new java.awt.Dimension(200, 20));
                                        jTextFieldCreationDate.setPreferredSize(new java.awt.Dimension(200, 20));
                                    }
                                    {
                                        jLabel7 = new JLabel();
                                        jPanelDates.add(jLabel7, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel7.setText("Modification: ");
                                    }
                                    {
                                        jLabel8 = new JLabel();
                                        jPanelDates.add(jLabel8, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jLabel8.setText("Last backup: ");
                                    }
                                    {
                                        jTextFieldLastBackupDate = new JFormattedTextField();
                                        jPanelDates.add(jTextFieldLastBackupDate, new GridBagConstraints(1, -2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
                                        jTextFieldLastBackupDate.setPreferredSize(new java.awt.Dimension(200, 20));
                                    }
                                    {
                                        jTextFieldModificationDate = new JFormattedTextField();
                                        jPanelDates.add(
                                            jTextFieldModificationDate,
                                            new GridBagConstraints(
                                                1,
                                                1,
                                                1,
                                                1,
                                                0.0,
                                                0.0,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0),
                                                0,
                                                0));
                                        jTextFieldModificationDate.setPreferredSize(new java.awt.Dimension(200, 20));
                                    }
                                }
                                {
                                    jPanelAttributes = new JPanel();
                                    jPanelPropertiesTab.add(jPanelAttributes);
                                    GridBagLayout jPanelAttributesLayout = new GridBagLayout();
                                    jPanelAttributes.setLayout(jPanelAttributesLayout);
                                    jPanelAttributes.setBorder(BorderFactory.createTitledBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false), "Attributes", TitledBorder.LEADING, TitledBorder.TOP));
                                    {
                                        jCheckBoxResDb = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxResDb, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxResDb.setText("Resource DB");
                                        jCheckBoxResDb.setToolTipText("The database is a resource database, i.e. it contains resources (code, forms, bitmaps, etc.) and not generic records.");
                                    }
                                    {
                                        jCheckBoxReadOnly = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxReadOnly, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxReadOnly.setText("Read-only");
                                        jCheckBoxReadOnly.setToolTipText("The database can not be deleted or written to.");
                                    }
                                    {
                                        jCheckBoxBackup = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxBackup, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxBackup.setText("Backup");
                                        jCheckBoxBackup.setToolTipText("Set if database should be backed up to PC if no app-specific synchronization conduit has been supplied.");
                                    }
                                    {
                                        jCheckBoxAppInfoDirty = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxAppInfoDirty, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxAppInfoDirty.setText("AppInfo dirty");
                                        jCheckBoxAppInfoDirty.setToolTipText("Set if Application Info block is dirty. Optionally supported by an App's conduit.");
                                    }
                                    {
                                        jCheckBoxOkToInstallNewer = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxOkToInstallNewer, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxOkToInstallNewer
                                            .setText("OK to install newer");
                                        jCheckBoxOkToInstallNewer.setToolTipText("This tells the backup conduit that it's OK for it to install a newer version of this database with a different name if the current database is open. This mechanism is used to update the Graffiti Shortcuts database, for example.");
                                    }
                                    {
                                        jCheckBoxResetAfterInstall = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxResetAfterInstall, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxResetAfterInstall.setText("Reset after install");
                                        jCheckBoxResetAfterInstall.setToolTipText("Device requires a reset after this database is installed.");
                                    }
                                    {
                                        jCheckBoxCopyPrevention = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxCopyPrevention, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxCopyPrevention.setText("Copy prevention");
                                        jCheckBoxCopyPrevention.setToolTipText("This database should not be copied.");
                                    }
                                    {
                                        jCheckBoxStream = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxStream, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxStream.setText("Stream");
                                        jCheckBoxStream.setToolTipText("The database is was generated by using the \"streams\" API.");
                                    }
                                    {
                                        jCheckBoxHidden = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxHidden, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxHidden.setText("Hidden");
                                        jCheckBoxHidden.setToolTipText("This database should generally be hidden from view used to hide some apps from the main view of the launcher for example. For data (non-resource) databases, this hides the record count within the launcher info screen.");
                                    }
                                    {
                                        jCheckBoxLaunchableData = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxLaunchableData, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxLaunchableData.setText("Launchable data");
                                        jCheckBoxLaunchableData.setToolTipText("This data database (not applicable for executables) can be \"launched\" by passing its name to its owner app ('appl' database with same creator) using the sysAppLaunchCmdOpenNamedDB action code. ");
                                    }
                                    {
                                        jCheckBoxRecyclable = new JCheckBox();
                                        jPanelAttributes.add(
                                            jCheckBoxRecyclable,
                                            new GridBagConstraints(
                                                2,
                                                4,
                                                1,
                                                1,
                                                0.0,
                                                0.0,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0),
                                                0,
                                                0));
                                        jCheckBoxRecyclable.setText("Recyclable");
                                        jCheckBoxRecyclable.setToolTipText("This database (resource or record) is recyclable: <br>\nit will be deleted Real Soon Now, generally the next time the <br>\ndatabase is closed. ");
                                    }
                                    {
                                        jCheckBoxBundle = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxBundle, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxBundle.setText("Bundle");
                                        jCheckBoxBundle.setToolTipText("This database (resource or record) is associated with the application with the same creator. It will be beamed and copied along with the application.");
                                    }
                                    {
                                        jCheckBoxOpen = new JCheckBox();
                                        jPanelAttributes.add(jCheckBoxOpen, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
                                        jCheckBoxOpen.setText("Open");
                                        jCheckBoxOpen.setToolTipText("Database not closed properly.");
                                    }
                                }
                            }
                            {
                                jPanelAppInfoTab = new JPanel();
                                jTabbedPane.addTab("AppInfo block", null, jPanelAppInfoTab, null);
                                BorderLayout jPanel1Layout1 = new BorderLayout();
                                jPanelAppInfoTab.setLayout(jPanel1Layout1);
                                {
                                    jTextAreaAppInfo = new JTextArea();
                                    jPanelAppInfoTab.add(
                                        jTextAreaAppInfo,
                                        BorderLayout.CENTER);
                                    jTextAreaAppInfo.setEditable(false);
                                }
                            }
                            {
                                jPanelSortInfoTab = new JPanel();
                                jTabbedPane.addTab("SortInfo block", null, jPanelSortInfoTab, null);
                                BorderLayout jPanel1Layout2 = new BorderLayout();
                                jPanelSortInfoTab.setLayout(jPanel1Layout2);
                                {
                                    jTextAreaSortInfo = new JTextArea();
                                    jPanelSortInfoTab.add(
                                        jTextAreaSortInfo,
                                        BorderLayout.CENTER);
                                    jTextAreaSortInfo.setEditable(false);
                                }
                            }
                            {
                                jScrollPaneRecords = new JScrollPane();
                                jTabbedPane.addTab("Records", null, jScrollPaneRecords, null);
                                {
                                    jTableDatabaseRecords = new JTable();
                                    jScrollPaneRecords.setViewportView(jTableDatabaseRecords);
                                    jTableDatabaseRecords.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                                }
                            }
                        }
                        jMenuHelp.add(jMenuItemHelp);
                        jMenuItemHelp.setText("Help");
                        jMenuItemHelp.setEnabled(false);
                    }
                    {
                        jMenuItemAbout = new JMenuItem();
                        jMenuHelp.add(jMenuItemAbout);
                        jMenuItemAbout.setText("About...");
                        jMenuItemAbout.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                jMenuItemAboutActionPerformed(evt);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void jMenuItemFileOpenActionPerformed(ActionEvent evt) {
        System.out.println("jMenuItemFileOpen.actionPerformed, event=" + evt);
        

        File file = chooseFile(false);
        
        try {
            PalmDatabase palmDatabase = PalmDatabase.open(file);
            setPalmDatabase(palmDatabase);
        }
        catch (Exception e) {
            // fail
            JOptionPane.showMessageDialog(this, 
                    "Failed to open " + file + "\n\nFile is corrupted or is not a palm database", 
                    "Failed to open", JOptionPane.ERROR_MESSAGE);
            setPalmDatabase(null);
        }
    }
    
    private void displayPrc(PrcFile prcFile) {
        jTableDatabaseRecords.setModel(new PrcRecordsTableModel(prcFile));
    }

    private void displayPdb(PdbFile pdbFile) {
        jTableDatabaseRecords.setModel(new PdbRecordsTableModel(pdbFile));
    }

    private void displayCommons(PalmDatabase palmFile) {
        
        jTextFieldName.setText(palmFile.getName());
        jTextFieldCreator.setText(palmFile.getCreator());
        jTextFieldType.setText(palmFile.getType());
        
        System.out.println("uniqueIdSeed: " + palmFile.getUniqueIdSeed());
        System.out.println("version: " + palmFile.getVersion());

        jTextFieldCreationDate.setValue(palmFile.getCreationDate());
        jTextFieldModificationDate.setValue(palmFile.getModificationDate());
        jTextFieldLastBackupDate.setValue(palmFile.getLastBackupDate());
        
        jCheckBoxAppInfoDirty.setSelected(palmFile.isAppInfoDirty());
        jCheckBoxBackup.setSelected(palmFile.isBackup());
        jCheckBoxBundle.setSelected(palmFile.isBundle());
        jCheckBoxCopyPrevention.setSelected(palmFile.isCopyPrevention());
        jCheckBoxHidden.setSelected(palmFile.isHidden());
        jCheckBoxLaunchableData.setSelected(palmFile.isLaunchableData());
        jCheckBoxOkToInstallNewer.setSelected(palmFile.isOkToInstallNewer());
        jCheckBoxOpen.setSelected(palmFile.isOpen());
        jCheckBoxReadOnly.setSelected(palmFile.isReadOnly());
        jCheckBoxRecyclable.setSelected(palmFile.isRecyclable());
        jCheckBoxResDb.setSelected(palmFile.isResDb());
        jCheckBoxResetAfterInstall.setSelected(palmFile.isResetAfterInstall());
        jCheckBoxStream.setSelected(palmFile.isStream());
      
        if (palmFile.hasAppInfoBlock()) {
            try {
                jTextAreaAppInfo.setText(new String(palmFile.getAppInfoBlockBytes()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            jTextAreaAppInfo.setText("This file does not contains an AppInfo block");
        }

        if (palmFile.hasSortInfoBlock()) {
            try {
                jTextAreaSortInfo.setText(new String(palmFile.getSortInfoBlockBytes()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            jTextAreaSortInfo.setText("This file does not contains a SortInfo block");
        }
        
        jTextFieldFileSize.setText("" + palmFile.getFileSize() + " bytes");
        jTextFieldRecords.setText(Integer.toString(palmFile.getRecordCount()));
        
    }

    private void jMenuItemFileExitActionPerformed(ActionEvent evt) {
        System.out.println("jMenuItemFileExit.actionPerformed, event=" + evt);
        dispose();
    }

    public void setPalmDatabase(PalmDatabase palmDatabase) {
        this.palmDatabase = palmDatabase;
        if (palmDatabase == null) {
            reset();
            return;
        }
        
        displayCommons(palmDatabase);
        if (palmDatabase instanceof PdbFile) {
            displayPdb((PdbFile) palmDatabase);
        }
        else if (palmDatabase instanceof PrcFile) {
            displayPrc((PrcFile) palmDatabase);
        } 
    }

    private void reset() {
        // TODO reset fields
    }
    
    private void jMenuItemAboutActionPerformed(ActionEvent evt) {
        AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setVisible(true);
	}
	
	private void jMenuItemFileSaveAsActionPerformed(ActionEvent evt) {
        if (palmDatabase == null) {
            JOptionPane.showMessageDialog(this,  "Please load a Palm database first.", 
                    "Save failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = chooseFile(true);
        if (file == null) {
            return; // cancelled
        }
        
        if (file.exists()) {
            int option = JOptionPane.showConfirmDialog(this, 
                    "File " + file + " exists, do you wish to overwrite it?",
                    "File exists", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        
        try {
            palmDatabase.writeTo(file);
        }
        catch (Exception e) {
            // fail
            JOptionPane.showMessageDialog(this, 
                    "Failed to save " + file + " :\n\n" + e.toString(), 
                    "Save failed", JOptionPane.ERROR_MESSAGE);
        }
        
	}

    private static File chooseFile(boolean forSaving) {
        JFileChooser chooser = new JFileChooser();
        SuffixFileFilter filter = new SuffixFileFilter();
        filter.addExtension("pdb");
        filter.addExtension("prc");
        filter.addExtension("pqa");
        filter.setDescription("Palm databases");
        chooser.setFileFilter(filter);
        int choice = ( forSaving ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null));
        if (choice != JFileChooser.APPROVE_OPTION) {
            System.out.println("Cancelled");
            System.exit(1);
        }
        return chooser.getSelectedFile();
        
    }
    
    private void jMenuItemFileCloseActionPerformed(ActionEvent evt) {
        setPalmDatabase(null);
	}

}
