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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents a PRC database, that is a PalmDatabase where each record is a PrcRecordEntry.  
 * 
 * @author Olivier Gérardin
 * @see oge.jpdb.PalmDatabase
 * @see oge.jpdb.PrcRecordEntry
 */
public class PrcFile extends PalmDatabase {

    /**
     * Creates a new default PRC database
     */
    public PrcFile() {
        super();
        setResDb(true);
    }
    
    /**
     * Creates a new PRC database from the specified file. The database header
     * and record entry table are read and parsed immediately; access to the raw resource data
     * is deferred until {@link #getRecordBytes(int)} is called. 
     * 
     * @param file
     * @throws IOException
     */
    public PrcFile(File file) throws IOException {
        super(file);
        if (! isResDb()) {
            throw new PalmDbFormatException("The database is not a PRC file");
        }
    }

    /* (non-Javadoc)
     * @see oge.jpdb.PalmDatabase#readRecordEntry(java.io.RandomAccessFile)
     */
    protected RecordEntry readRecordEntry(RandomAccessFile raf) throws IOException {
        int type = raf.readInt();
        short id = raf.readShort();
        int localChunkId = raf.readInt();
        
        return new PrcRecordEntry(localChunkId, type, id);
    }

    /* (non-Javadoc)
     * @see oge.jpdb.PalmDatabase#writeRecordEntry(oge.jpdb.RecordEntry, java.io.RandomAccessFile)
     */
    protected void writeRecordEntry(RecordEntry entry, RandomAccessFile raf)
            throws IOException {
        PrcRecordEntry prcRecordEntry = (PrcRecordEntry) entry;
        
        raf.writeInt(prcRecordEntry.getType());
        raf.writeShort(prcRecordEntry.getId());
        raf.writeInt(prcRecordEntry.getLocalChunkId());
    }

    protected int getRecordEntrySize() {
        return 10;
    }
}
