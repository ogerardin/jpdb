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
package oge.jpdb.demo;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import oge.jpdb.PalmDatabase;
import oge.jpdb.demo.gui.SuffixFileFilter;

/**
 * This example demonstrates how to copy a Palm OS database using the JPDB
 * library.
 * 
 * @author OGE
 */
public class DbCopy {

    public static void main(String[] args) throws IOException {
        
        File infile;
        File outfile;
        
        if (args.length >= 1) {
            infile = new File(args[0]);
        }
        else {
	        // no file given: choose interactively
	        infile = chooseFile(false);
        }

        if (args.length >= 2) {
            outfile = new File(args[1]);
        }
        else {
	        // no file given: choose interactively
	        outfile = chooseFile(true);
        }

        System.out.println("Reading " + infile);
        PalmDatabase palmDatabase = PalmDatabase.open(infile);
        
        System.out.println("Writing " + outfile);
        palmDatabase.writeTo(outfile);
        
        System.exit(0);
    }
    
    private static File chooseFile(boolean forSaving) {
        JFileChooser chooser = new JFileChooser();
        SuffixFileFilter filter = new SuffixFileFilter();
        filter.addExtension("pdb");
        filter.addExtension("prc");
        filter.addExtension("pqa");
        filter.setDescription("Palm databases");
        chooser.setFileFilter(filter);
        int choice = (forSaving ? chooser.showSaveDialog(null) : chooser.showOpenDialog(null));
        if (choice != JFileChooser.APPROVE_OPTION) {
            System.out.println("Cancelled");
            System.exit(1);
        }
        return chooser.getSelectedFile();
        
    }
}
