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
 * Represents an entry in the record table of a PRC file, together with the associated
 * resource data.
 *
 * @author Olivier Gérardin
 * @see oge.jpdb.PrcFile
 */
public class PrcRecordEntry extends RecordEntry {

    private int type;
    private short id;

    protected PrcRecordEntry(int localChunkId, int type, short id) {
        super(localChunkId);
        this.type = type;
        this.id = id;
    }
    
    /**
     * Creates a PrcRecordEntry with the specified type (as a String), id and 
     * resource data.
     * 
     * @param type a 4-character String representing the type
     * @param id the ID of the resource contained in this record
     * @param rawData the raw data of the resource
     */
    public PrcRecordEntry(String type, short id, byte[] rawData) {
        setRawData(rawData);
        setType(type);
        this.id = id;
    }

    /**
     * Creates a PrcRecordEntry with the specified type (as an int), id and 
     * resource data.
     * 
     * @param type a 4-character String representing the type
     * @param id the ID of the resource contained in this record
     * @param rawData the raw data of the resource
     */
    public PrcRecordEntry(int type, short id, byte[] rawData) {
        setRawData(rawData);
        this.type = type;
        this.id = id;
    }


    public short getId() {
        return id;
    }
    public void setId(short id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    
    /**
     * Sets the type of this resource from a String.
     * @param type a 4-character String
     * @throws IllegalArgumentException if the specified type is not valid
     */
    public void setType(String type) {
        if (type.length() != 4) {
            throw new IllegalArgumentException("type must be exactly 4 characters long");
        }
        byte[] typeBytes = type.getBytes();
        this.type = Bits.getInt(typeBytes, 0);
    }

    public String getTypeAsString() {
        byte[] typeBytes = new byte[4];
        Bits.putInt(typeBytes, 0, type);
        
        return new String(typeBytes);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (! super.equals(obj)) {
            return false;
        }
    
        if (! (obj instanceof PrcRecordEntry)) {
            return false;
        }
        
        PrcRecordEntry other = (PrcRecordEntry) obj;

        if (this.getType() != other.getType()) { return false; }
        if (this.getId() != other.getId()) { return false; }
        
        return true;
    }
}
