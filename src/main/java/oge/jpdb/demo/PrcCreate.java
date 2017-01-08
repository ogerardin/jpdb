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

import oge.jpdb.PrcFile;
import oge.jpdb.PrcRecordEntry;

/**
 * This example demonstrates how to create from scratch a PRC file using the 
 * JPDB library.
 * 
 * @author Olivier Gérardin
 */
public class PrcCreate {

    public static void main(String[] args) throws IOException {
        
        // Instantiate a PrcFile
        PrcFile prcFile = new PrcFile();

        // Set some properties
        prcFile.setName("My resources");
        prcFile.setCreator("ABCD");
        prcFile.setType("rsrc");

        // Add records
        byte[] data = new byte[] { 0x31, 0x2e, 0x30, 0x00 };
        PrcRecordEntry entry = new PrcRecordEntry("tver", (short) 1000, data);
        prcFile.addRecord(entry);

        // Write to a file
        prcFile.writeTo(new File("myfile.prc"));    }
}
