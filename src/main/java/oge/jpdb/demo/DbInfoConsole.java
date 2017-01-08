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
import oge.jpdb.PdbFile;
import oge.jpdb.PdbRecordEntry;
import oge.jpdb.PrcFile;
import oge.jpdb.PrcRecordEntry;
import oge.jpdb.demo.gui.SuffixFileFilter;

/**
 * Example application that prints to the console database and record 
 * information from a PDB or PRC file using the JPDB library.
 * 
 * @author Olivier Gérardin
 */
public class DbInfoConsole {

    public static void main(String[] args) throws IOException {
        
        File file = null;
        
        if (args.length >= 1) {
            // file given on the command line
            file = new File(args[0]);
        }
        else {
	        // no file given: choose interactively
	        JFileChooser chooser = new JFileChooser();
	        SuffixFileFilter filter = new SuffixFileFilter();
	        filter.addExtension("pdb");
	        filter.addExtension("prc");
	        filter.addExtension("pqa");
	        filter.setDescription("Palm databases");
	        chooser.setFileFilter(filter);
	        int choice = chooser.showOpenDialog(null);
	        if (choice != JFileChooser.APPROVE_OPTION) {
	            System.out.println("Cancelled");
	            return;
	        }
	        file = chooser.getSelectedFile();
        }
        
        System.out.println("Querying file: " + file.getName());
        PalmDatabase palmDatabase = PalmDatabase.open(file);
        dumpCommons(palmDatabase);
        if (palmDatabase instanceof PdbFile) {
            dumpPdb((PdbFile) palmDatabase);
        }
        else if (palmDatabase instanceof PrcFile) {
            dumpPrc((PrcFile) palmDatabase);
        } 
    }
    
    
    private static void dumpPrc(PrcFile prcFile) throws IOException {
        for (int i = 0; i < prcFile.getRecordCount(); i++) {
            PrcRecordEntry recordEntry = (PrcRecordEntry) prcFile.getRecordEntry(i);
            
            byte[] recordBytes = prcFile.getRecordBytes(i);
            System.out.print("resource[" + i + "]: ");
            System.out.print("\"" + recordEntry.getTypeAsString() + "\" ");
            
            System.out.println("" + recordBytes.length + " bytes");
        }
    }


    private static void dumpPdb(PdbFile pdbFile) throws IOException {
        for (int i = 0; i < pdbFile.getRecordCount(); i++) {
            PdbRecordEntry recordEntry = (PdbRecordEntry) pdbFile.getRecordEntry(i);
            
            byte[] recordBytes = pdbFile.getRecordBytes(i);
            System.out.print("record[" + i + "]: ");
            if (recordEntry.isBusy()) {
                System.out.print("busy,");
            }
            if (recordEntry.isDelete()) {
                System.out.print("delete, ");
            }
            if (recordEntry.isDirty()) {
                System.out.print("dirty, ");
            }
            if (recordEntry.isSecret()) {
                System.out.print("secret, ");
            }
            
            System.out.println("" + recordBytes.length + " bytes");
        }
    }


    private static void dumpCommons(PalmDatabase palmFile) {
        System.out.println("name: " + palmFile.getName());
        System.out.println("creator: " + palmFile.getCreator());
        System.out.println("type: " + palmFile.getType());
        System.out.println("uniqueIdSeed: " + palmFile.getUniqueIdSeed());
        System.out.println("version: " + palmFile.getVersion());
        
        System.out.println("creationDate: " + palmFile.getCreationDate());
        System.out.println("modificationDate: " + palmFile.getModificationDate());
        System.out.println("lastbackupDate: " + palmFile.getLastBackupDate());
        
        System.out.print("Attributes: ");
        if (palmFile.isAppInfoDirty()) {
            System.out.print("dirty ");
        }
        if (palmFile.isBackup()) {
            System.out.print("backup ");
        }
        if (palmFile.isBundle()) {
            System.out.print("bundle ");
        }
        if (palmFile.isCopyPrevention()) {
            System.out.print("copyPrevention ");
        }
        if (palmFile.isLaunchableData()) {
            System.out.print("launchableData ");
        }
        if (palmFile.isOkToInstallNewer()) {
            System.out.print("okToInstallNewer ");
        }
        if (palmFile.isOpen()) {
            System.out.print("open ");
        }
        if (palmFile.isReadOnly()) {
            System.out.print("readOnly ");
        }
        if (palmFile.isRecyclable()) {
            System.out.print("recyclable ");
        }
        if (palmFile.isResDb()) {
            System.out.print("resDb ");
        }
        if (palmFile.isResetAfterInstall()) {
            System.out.print("resetAfterInstall ");
        }
        if (palmFile.isStream()) {
            System.out.print("stream ");
        }
        System.out.println();
        
        System.out.println("Has AppInfo block: " + palmFile.hasAppInfoBlock());
        System.out.println("Has SortInfo block: " + palmFile.hasSortInfoBlock());
        
        
        System.out.println("Size: " + palmFile.getFileSize());
        int recordCount = palmFile.getRecordCount();
        System.out.println("Record count: " + recordCount);
        
    }
}
