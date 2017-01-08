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

import java.util.Arrays;

/**
 * Represents an item in the record table of a palm database, together with 
 * the associated raw data.
 * 
 * Abstract superclass for PDB and PRC record entries. 
 * 
 * @author Olivier Gérardin
 */
public abstract class RecordEntry implements Cloneable {
    
    private int localChunkId;
    private byte[] rawData;
    
    /**
     * Creates a RecordEntry with a localChunkId of 0
     *
     */
    protected RecordEntry() {
        this(0);
    }
    
    /**
     * Creates a RecordEntry with the specified localChunkId
     * @param localChunkId
     */
    protected RecordEntry(int localChunkId) {
        this.localChunkId = localChunkId;
        this.rawData = null;
    }
    
    /**
     * Returns the local chunk ID of this record, that is the location of the
     * first byte of this record's raw data, relative to the beginning of the 
     * file. 
     */
    public int getLocalChunkId() {
        return localChunkId;
    }
    protected void setLocalChunkId(int localChunkId) {
        this.localChunkId = localChunkId;
    }
    
    /**
     * Returns this record's raw data as a byte array.
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * Sets this record's raw data from a byte array.
     */
    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }
    
    public Object clone() throws CloneNotSupportedException {
        RecordEntry clone = (RecordEntry) super.clone();
        clone.setRawData((byte[]) rawData.clone());
        return clone;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof RecordEntry)) {
            return false;
        }
        
        RecordEntry other = (RecordEntry) obj;
        
        if (this.localChunkId != other.localChunkId) { return false; }
        
        if (! Arrays.equals(this.getRawData(), other.getRawData())) {
            return false;
        }
        
        return true;
    }

}
