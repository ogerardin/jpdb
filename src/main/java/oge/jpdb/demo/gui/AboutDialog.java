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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

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
public class AboutDialog extends javax.swing.JDialog {
    /**
     * 
     */
    private static final long serialVersionUID = -8094337708185597111L;
    
    private JPanel jPanelButtons;
    private JButton jButtonOk;
    private JTextPane jTextPaneAbout;

    /**
    * Auto-generated main method to display this JDialog
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        AboutDialog inst = new AboutDialog(frame);
        inst.setVisible(true);
    }
    
    public AboutDialog(JFrame frame) {
        super(frame);
        initGUI();
        this.setLocationRelativeTo(frame);
    }
    
    private void initGUI() {
        try {
            BorderLayout thisLayout = new BorderLayout();
            this.getContentPane().setLayout(thisLayout);
            this.setTitle("About DbInfoSwing");
            {
                jPanelButtons = new JPanel();
                this.getContentPane().add(jPanelButtons, BorderLayout.SOUTH);
                {
                    jButtonOk = new JButton();
                    jPanelButtons.add(jButtonOk);
                    jButtonOk.setText("OK");
                    jButtonOk.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            AboutDialog.this.dispose();
                        }
                    });
                }
            }
            {
                jTextPaneAbout = new JTextPane();
                this.getContentPane().add(jTextPaneAbout, BorderLayout.CENTER);
                jTextPaneAbout.setText("This is DBInfoSwing, a graphical front-end to the JPDB library\n\nJPDB is a Java library intended to read and write Palm OS database storage file formats, that is the format used to store Palm OS databases on a classical filesystem. This includes PDB files (general purpose record-oriented databases) and PRC files (resource-oriented databases, including applications).\n\nJPDB is Free Software. JPDB is released under the terms of the LGPL, a copy of which is provided in the LICENSE.txt file.\n\nFor more information, read the file README.txt and the Javadoc API in doc/index.html\n\n--\nOlivier Gérardin, sept. 2005");
                jTextPaneAbout.setEditable(false);
            }
            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
