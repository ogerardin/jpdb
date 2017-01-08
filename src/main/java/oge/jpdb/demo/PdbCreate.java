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

import oge.jpdb.PdbFile;
import oge.jpdb.PdbRecordEntry;

/**
 * This example demonstrates how to create from scratch a PDB file using the 
 * JPDB library.
 * 
 * @author Olivier Gérardin
 */
public class PdbCreate {

    public static void main(String[] args) throws IOException {
        
        PdbFile pdbFile = new PdbFile();
        
        pdbFile.setName("a new database");
        pdbFile.setCreator("ABCD");
        pdbFile.setType("DATA");
        pdbFile.setVersion((short) 10);
     
        byte[] sampledata = new byte[] {
                1, 2, 3, 4, 5, 9, 8, 7, 6, 0
        };
        
        PdbRecordEntry entry = new PdbRecordEntry(sampledata);
        pdbFile.addRecord(entry);
        
        pdbFile.writeTo(new File("test.pdb"));
    }
}
