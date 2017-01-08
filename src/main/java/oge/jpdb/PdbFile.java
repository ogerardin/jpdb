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
package oge.jpdb;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents a PDB database, that is a PalmDatabase where each record is a 
 * PdbRecordEntry  
 * 
 * @author Olivier Gérardin
 * @see oge.jpdb.PalmDatabase
 * @see oge.jpdb.PdbRecordEntry
 */
public class PdbFile extends PalmDatabase {

    /**
     * Creates a new default PDB database
     */
    public PdbFile() {
        super();
        setResDb(false);
    }
    
    /**
     * Creates a new PDB database from the specified file. The database header
     * and record entry table are read and parsed immediately; access to the raw data
     * is deferred until {@link #getRecordBytes(int)} is called. 
     * 
     * @param file
     * @throws IOException
     */
    public PdbFile(File file) throws IOException {
        super(file);
        if (isResDb()) {
            throw new PalmDbFormatException("The database is not a PDB file");
        }
    }
    
    /* (non-Javadoc)
     * @see oge.jpdb.PalmDatabase#readRecordEntry(java.io.RandomAccessFile)
     */
    protected RecordEntry readRecordEntry(RandomAccessFile raf) throws IOException {
        int localChunkId = raf.readInt();
        byte attributes = raf.readByte();
        byte[] uniqueIdBytes = new byte[3];
        int n = raf.read(uniqueIdBytes);
        if (n < 3) {
            throw new EOFException();
        }
        int uniqueId = Bits.makeInt((byte) 0, uniqueIdBytes[0], uniqueIdBytes[1], uniqueIdBytes[2]);
        
        return new PdbRecordEntry(localChunkId, attributes, uniqueId);
    }

    /* (non-Javadoc)
     * @see oge.jpdb.PalmDatabase#writeRecordEntry(oge.jpdb.RecordEntry, java.io.RandomAccessFile)
     */
    protected void writeRecordEntry(RecordEntry entry, RandomAccessFile raf) throws IOException {
        PdbRecordEntry pdbRecordEntry = (PdbRecordEntry) entry;
        
        raf.writeInt(pdbRecordEntry.getLocalChunkId());
        raf.writeByte(pdbRecordEntry.getAttributes());

        byte[] uniqueIdBytes = new byte[4];
        Bits.putInt(uniqueIdBytes, 0, pdbRecordEntry.getUniqueId());
        raf.write(uniqueIdBytes, 1, 3);
    }

    protected int getRecordEntrySize() {
        return 8;
    }

    
    
}
