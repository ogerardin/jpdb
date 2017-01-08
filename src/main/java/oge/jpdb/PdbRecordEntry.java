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

/**
 * Represents an entry in the record table of a PDB file, together with the 
 * associated raw data.
 * 
 * @author Olivier Gérardin
 * @see oge.jpdb.PdbFile
 */
public class PdbRecordEntry extends RecordEntry {
    
    private static final short dmRecAttrDelete	= 0x80;	// delete this record next sync
    private static final short dmRecAttrDirty	= 0x40;	// archive this record next sync
    private static final short dmRecAttrBusy		= 0x20;	// record currently in use
    private static final short dmRecAttrSecret	= 0x10;	// "secret" record - password protected

    private boolean delete;
    private boolean dirty;
    private boolean busy;
    private boolean secret;
    
    private int uniqueId;

    
    /**
     * Creates a PdbRecordEntry with the specified localChunkId, attributes (as a byte value)
     * and unique ID
     * 
     * @param localChunkId
     * @param attributes
     * @param uniqueId
     */
    protected PdbRecordEntry(int localChunkId, byte attributes, int uniqueId) {
        super(localChunkId);
        setAttributes(attributes);
        this.uniqueId = uniqueId;
    }

    /**
     * Creates a PdbRecordEntry with the specified unique ID and raw data, and all
     * attributes set to false.
     *  
     * @param uniqueId
     * @param rawData
     */
    public PdbRecordEntry(int uniqueId, byte[] rawData) {
        setAttributes((byte) 0);
        setUniqueId(uniqueId);
        setRawData(rawData);
    }

    /**
     * Creates a PdbRecordEntry with the specified raw data and a unique ID of zero.
     * 
     * @param rawData
     */
    public PdbRecordEntry(byte[] rawData) {
        this(0, rawData);
    }

    /**
     * Returns the attributes of this PdbRecordEntry as a byte value.
     */
    byte getAttributes() {
        byte attributes = 0;
        attributes |= (isDelete() ? dmRecAttrDelete : 0);
        attributes |= (isDirty() ? dmRecAttrDirty : 0);
        attributes |= (isBusy() ? dmRecAttrBusy: 0);
        attributes |= (isSecret() ? dmRecAttrSecret : 0);
        return attributes;
    }
    
    /**
     * Sets the attributes of this PdbRecordEntry from a byte value.
     * @param attributes
     */
    protected void setAttributes(byte attributes) {
        setDelete((attributes & dmRecAttrDelete) != 0);
        setDirty((attributes & dmRecAttrDirty) != 0);
        setBusy((attributes & dmRecAttrBusy) != 0);
        setSecret((attributes & dmRecAttrSecret) != 0);
    }
    
    public boolean isBusy() {
        return busy;
    }
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    
    public boolean isDelete() {
        return delete;
    }
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    public boolean isDirty() {
        return dirty;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    public boolean isSecret() {
        return secret;
    }
    public void setSecret(boolean secret) {
        this.secret = secret;
    }
    
    public int getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (! super.equals(obj)) {
            return false;
        }

        if (! (obj instanceof PdbRecordEntry)) {
            return false;
        }
        
        PdbRecordEntry other = (PdbRecordEntry) obj;
        
        if (this.isBusy() != other.isBusy()) { return false; }
        if (this.isDelete() != other.isDelete()) { return false; }
        if (this.isDirty() != other.isDirty()) { return false; }
        if (this.isSecret() != other.isSecret()) { return false; }
        
        return true;
    }
}
