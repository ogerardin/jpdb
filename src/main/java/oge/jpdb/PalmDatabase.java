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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Palm OS database. PalmDatabase is the abstract superclass for 
 * both types of Palm OS Database files: PdbFile represents a generic <b>record 
 * database</b> (PDB file), while PrcFile represents a <b>resource database</b> 
 * (PRC file).
 * 
 * @author Olivier Gérardin
 * @see oge.jpdb.PdbFile
 * @see oge.jpdb.PrcFile
 */
public abstract class PalmDatabase {
    
    private File file;
    private long fileSize;

    private String name;
    private String creator;
    private Date creationDate;
    private Date lastBackupDate;
    private Date modificationDate;
    private int modificationNumber;
    private List /* <RecordEntry> */ recordEntries;
    private String type;
    private int uniqueIdSeed;
    private short version;

    private int appInfoId = 0;
    private byte[] appInfoBlockBytes = null;

    private int sortInfoId = 0;
    private byte[] sortInfoBlockBytes = null;
    
    private boolean readOnly;
    private boolean resDb;
    private boolean open;
    private boolean bundle;
    private boolean recyclable;
    private boolean launchableData;
    private boolean stream;
    private boolean copyPrevention;
    private boolean resetAfterInstall;
    private boolean okToInstallNewer;
    private boolean backup;
    private boolean appInfoDirty;
    private boolean hidden;
    
    private static final int	dmHdrAttrResDB				= 0x0001;	// Resource database
    private static final int    dmHdrAttrReadOnly			= 0x0002;	// Read Only database
    private static final int	dmHdrAttrAppInfoDirty		= 0x0004;	// Set if Application Info block is dirty
                                                                        // Optionally supported by an App's conduit
    private static final int	dmHdrAttrBackup				= 0x0008;	//	Set if database should be backed up to PC if
                                                                        //	no app-specific synchronization conduit has
    															        //	been supplied.
    private static final int	dmHdrAttrOKToInstallNewer 	= 0x0010;	// This tells the backup conduit that it's OK
                                                                        //  for it to install a newer version of this database
            															//  with a different name if the current database is
            															//  open. This mechanism is used to update the 
            															//  Graffiti Shortcuts database, for example.
    private static final int	dmHdrAttrResetAfterInstall	= 0x0020; 	// Device requires a reset after this database is 
                                                                        // installed.
    private static final int	dmHdrAttrCopyPrevention		= 0x0040;	// This database should not be copied to
    private static final int	dmHdrAttrStream				= 0x0080;	// This database is used for file stream implementation.
    private static final int	dmHdrAttrHidden				= 0x0100;	// This database should generally be hidden from view
                                                                        //  used to hide some apps from the main view of the
            															//  launcher for example.
            															// For data (non-resource) databases, this hides the record
            															//	 count within the launcher info screen.
    private static final int	dmHdrAttrLaunchableData		= 0x0200;	// This data database (not applicable for executables)
                                                                        //  can be "launched" by passing it's name to it's owner
                                                                        //  app ('appl' database with same creator) using
                                                                        //  the sysAppLaunchCmdOpenNamedDB action code.
    private static final int	dmHdrAttrRecyclable			= 0x0400;	// This database (resource or record) is recyclable:
                                                                        //  it will be deleted Real Soon Now, generally the next
                                                                        //  time the database is closed.
    private static final int	dmHdrAttrBundle				= 0x0800;	// This database (resource or record) is associated with
                                                                        // the application with the same creator. It will be beamed
                                                                        // and copied along with the application.
    private static final int	dmHdrAttrOpen				= 0x8000;	// Database not closed properly


    /**
     * Creates a PalmDatabase which is backed by the specified file. The database header
     * and record entries are read and parsed immediately; access to the record raw data
     * is deferred until #getRecordBytes(int) is called, and then is cached. 
     *   
     * @param file
     * @throws IOException
     */
    protected PalmDatabase(File file) throws IOException {
        this.file = file;
        readHeader();
    }
    
    /**
     * Creates a default PalmDatabase which is not linked to a file. Contents can be added, 
     * and when it's ready the method #writeTo will create a file.
     */
    protected PalmDatabase() {
        this.file = null;
        this.recordEntries = new ArrayList();
        setName("Unnamed database");
        setCreator("JPDB");
        setType("DATA");
    }

    public Date getCreationDate() {
        return creationDate;
    }
    public String getCreator() {
        return creator;
    }
    public Date getLastBackupDate() {
        return lastBackupDate;
    }
    public Date getModificationDate() {
        return modificationDate;
    }
    public int getModificationNumber() {
        return modificationNumber;
    }
    public String getName() {
        return name;
    }

    /**
     * Returns the raw bytes of a record as a byte array. If the record has not yet been
     * loaded and the database is backup by a file, the raw bytes are loaded from the file.
     *  
     * @param n the record index
     * @return the record's raw bytes as a byte array, or null if it has not been set. 
     * @throws IOException
     */
    public byte[] getRecordBytes(int n) throws IOException {
        RecordEntry recordEntry = (RecordEntry) recordEntries.get(n);
        
        byte[] chunk = recordEntry.getRawData(); 
        if (chunk != null) {
            // record already loaded: return it
            return chunk;
        }
        
        if (file == null) {
            // database not backed up by a file: nothing more we can do
            return null;
        }
        
        // otherwise, load record data from file
        int localChunkId = recordEntry.getLocalChunkId();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        // determine chunk size
        int chunkSize = 0;
        if ((n + 1) < recordEntries.size()) {
            RecordEntry nextRecordEntry = (RecordEntry) recordEntries.get(n+1);
            int nextLocalChunkId = nextRecordEntry.getLocalChunkId();
            chunkSize = nextLocalChunkId - localChunkId;
        }
        else {
            // last record: determine size by looking at file size
            chunkSize = (int) (raf.length() - localChunkId);
        }
        
        // read raw bytes into byte array
        chunk = new byte[chunkSize];
        raf.seek(localChunkId);
        int actualByteCount = raf.read(chunk);
        raf.close();
        
        if (actualByteCount < chunkSize) {
            throw new PalmDbFormatException("Premature EOF reading record " + n 
                + " (" + chunkSize + "bytes @" + localChunkId + ")");
        }
        
        // store raw data
        recordEntry.setRawData(chunk);
        return chunk;
    }

    
    /**
     * Returns the number of records in the database
     */
    public int getRecordCount() {
        return recordEntries.size();
    }
    
    /**
     * Returns the database type as a 4-character String
     */
    public String getType() {
        return type;
    }
    
    public short getVersion() {
        return version;
    }
    
    
    /**
     * Parse the database header bytes into the member variables.
     * 
     * @param databaseHdr
     */
    private void parseHeader(byte[] databaseHdr) {
        name = Bits.parseString(databaseHdr, 0, 0x20);
        short attributes = Bits.getShort(databaseHdr, 0x20);
        parseAttributes(attributes);
        
        version = Bits.getShort(databaseHdr, 0x22);
        creationDate = Bits.parseDate(Bits.getInt(databaseHdr, 0x24));
        modificationDate = Bits.parseDate(Bits.getInt(databaseHdr, 0x28));
        lastBackupDate = Bits.parseDate(Bits.getInt(databaseHdr, 0x2c));
        modificationNumber = Bits.getInt(databaseHdr, 0x30);
        
        appInfoId = Bits.getInt(databaseHdr, 0x34);
        sortInfoId = Bits.getInt(databaseHdr, 0x38);
        
        type = new String(databaseHdr, 0x3c, 4);
        creator = new String(databaseHdr, 0x40, 4);
        uniqueIdSeed = Bits.getInt(databaseHdr, 0x44);
    }
    
    /**
     * Parse the attribute bit-field into the boolean member variables
     * @param attributes
     */
    private void parseAttributes(short attributes) {
        setResDb((attributes & PalmDatabase.dmHdrAttrResDB) != 0);
        setReadOnly((attributes & PalmDatabase.dmHdrAttrReadOnly) != 0);
        setAppInfoDirty((attributes & PalmDatabase.dmHdrAttrAppInfoDirty) != 0);
        setBackup((attributes & PalmDatabase.dmHdrAttrBackup) != 0);
        setOkToInstallNewer((attributes & PalmDatabase.dmHdrAttrOKToInstallNewer) != 0);
        setResetAfterInstall((attributes & PalmDatabase.dmHdrAttrResetAfterInstall) != 0);
        setCopyPrevention((attributes & PalmDatabase.dmHdrAttrCopyPrevention) != 0);
        setStream((attributes & PalmDatabase.dmHdrAttrStream) != 0);
        setHidden((attributes & PalmDatabase.dmHdrAttrHidden) != 0);
        setLaunchableData((attributes & PalmDatabase.dmHdrAttrLaunchableData) != 0);
        setRecyclable((attributes & PalmDatabase.dmHdrAttrRecyclable) != 0);
        setBundle((attributes & PalmDatabase.dmHdrAttrBundle) != 0);
        setOpen((attributes & PalmDatabase.dmHdrAttrOpen) != 0);
    }

    private void setOpen(boolean b) {
        open = b;
    }
    private void setBundle(boolean b) {
        bundle = b;
    }
    private void setRecyclable(boolean b) {
        recyclable = b;
    }
    private void setLaunchableData(boolean b) {
        launchableData = b;
    }
    private void setStream(boolean b) {
        stream = b;
    }
    private void setCopyPrevention(boolean b) {
        copyPrevention = b;
    }
    private void setResetAfterInstall(boolean b) {
        resetAfterInstall = b;
    }
    private void setOkToInstallNewer(boolean b) {
        okToInstallNewer = b;
    }
    private void setBackup(boolean b) {
        backup = b;
    }
    private void setAppInfoDirty(boolean b) {
        appInfoDirty = b;
    }
    private void setReadOnly(boolean b) {
        readOnly = b;
    }
    
    protected final void setResDb(boolean b) {
        resDb = b;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Read and parse the database header and record entries from the underlying file.
     * 
     * @throws IOException
     */
    protected final void readHeader() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        this.fileSize = raf.length();

        // read database header
        byte[] databaseHdr = new byte[0x48];
        int n = raf.read(databaseHdr);
        if (n < databaseHdr.length) {
            throw new EOFException();
        }
        parseHeader(databaseHdr);
        
        // read record headers
        int nextRecordId;
        do {
	        nextRecordId = raf.readInt();
	        short numRecords = raf.readShort();
	
	        // read record entries
	        recordEntries = new ArrayList();
	        for (int i = 0; i < numRecords; i++) {
	            RecordEntry recordEntry = readRecordEntry(raf);
	            recordEntries.add(recordEntry);
	        }
	        
            //raf.readShort(); // placeholder bytes
            if (nextRecordId != 0) {
                raf.seek(nextRecordId);
            }
            
        } while (nextRecordId != 0);
        
        raf.close();
    }

    
    /**
     * Read a RecordEntry from the specified RandomAccessFile at the current position.
     * Since the concrete RecordEntry type is different for PDB and PRC files, the 
     * implementation of this method is left to a subclass of PalmDatabase. 
     * 
     * @param raf input RandomAccessFile
     * @return the populated RecordEntry.
     * @throws IOException
     */
    protected abstract RecordEntry readRecordEntry(RandomAccessFile raf) throws IOException;

    
    /**
     * Returns the appInfo block for this PalmDatabase. If the database is backed by a file,
     * the appInfo block is read from the file. 
     *  
     * @return the appInfo block as a byte array
     * @throws IOException
     */
    public byte[] getAppInfoBlockBytes() throws IOException {
        if (! hasAppInfoBlock()) {
            return null;
        }
        
        if (appInfoBlockBytes != null) {
            return appInfoBlockBytes;
        }
        
        // calculate block length
        int nextChunk;
        if (hasSortInfoBlock()) {
            nextChunk = sortInfoId;
        }
        else if (getRecordCount() > 0) {
            RecordEntry firstRecordEntry = (RecordEntry) recordEntries.get(0);
            nextChunk = firstRecordEntry.getLocalChunkId();
        }
        else {
            nextChunk = (int) getFileSize();
        }
        int appInfoBlockSize = appInfoId - nextChunk;
        
        // read from file
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        appInfoBlockBytes = new byte[appInfoBlockSize];
        raf.seek(appInfoId);
        int actualByteCount = raf.read(appInfoBlockBytes);
        raf.close();

        if (actualByteCount < appInfoBlockSize) {
            throw new PalmDbFormatException("Premature EOF reading AppInfo block");
        }
        
        return appInfoBlockBytes;
    }

    
    /**
     * Returns the sortInfo block for this PalmDatabase. If the database is backed by a file,
     * the sortInfo block is read from the file. 
     *  
     * @return the sortInfo block a a byte array.
     * @throws IOException
     */
    public byte[] getSortInfoBlockBytes() throws IOException {
        if (! hasSortInfoBlock()) {
            return null;
        }
        
        if (sortInfoBlockBytes != null) {
            return sortInfoBlockBytes;
        }
        
        // calculate block length
        int nextChunk;
        if (getRecordCount() > 0) {
            RecordEntry firstRecordEntry = (RecordEntry) recordEntries.get(0);
            nextChunk = firstRecordEntry.getLocalChunkId();
        }
        else {
            nextChunk = (int) getFileSize();
        }
        int sortInfoBlockSize = sortInfoId - nextChunk;
        
        // read from file
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        sortInfoBlockBytes = new byte[sortInfoBlockSize];
        raf.seek(sortInfoId);
        int actualByteCount = raf.read(sortInfoBlockBytes);
        raf.close();
        
        if (actualByteCount < sortInfoBlockSize) {
            throw new PalmDbFormatException("Premature EOF reading SortInfo block");
        }
        
        return sortInfoBlockBytes;
    }
    
    public long getFileSize() {
        return fileSize;
    }

    /**
     * Returns true if and only if this database has an associated AppInfo block
     */
    public boolean hasAppInfoBlock() {
        return (appInfoId != 0);
    }

    /**
     * Returns true if and only if this database has an associated SortInfo block
     */
    public boolean hasSortInfoBlock() {
        return (sortInfoId != 0);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    /**
     * Set the creator of this database.
     * @param creator a 4-character String
     * @throws IllegalArgumentException if the specified creator is not valid
     */
    public void setCreator(String creator) {
        if (creator.length() != 4) {
            throw new IllegalArgumentException("type must be exactly 4 characters long");
        }
        this.creator = creator;
    }
    public void setLastBackupDate(Date lastBackupdate) {
        this.lastBackupDate = lastBackupdate;
    }
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
    public void setModificationNumber(int modificationNumber) {
        this.modificationNumber = modificationNumber;
    }

    /**
     * Set the name of this database.
     * @param name a String of at most 31 characters.
     * @throws IllegalArgumentException if the specified name is not valid
     */
    public void setName(String name) {
        if (name.length() > 31) {
            throw new IllegalArgumentException("name must not be longer than 31 characters");
        }
        this.name = name;
    }

    /**
     * Set the type of this database.
     * @param type a 4-character String
     * @throws IllegalArgumentException if the specified type is not valid
     */
    public void setType(String type) {
        if (type.length() != 4) {
            throw new IllegalArgumentException("type must be exactly 4 characters long");
        }
        this.type = type;
    }
    public void setVersion(short version) {
        this.version = version;
    }
    public int getUniqueIdSeed() {
        return uniqueIdSeed;
    }
    public void setUniqueIdSeed(int uniqueIdSeed) {
        this.uniqueIdSeed = uniqueIdSeed;
    }
    public boolean isBackup() {
        return backup;
    }
    public boolean isBundle() {
        return bundle;
    }
    public boolean isCopyPrevention() {
        return copyPrevention;
    }
    public boolean isLaunchableData() {
        return launchableData;
    }
    public boolean isOpen() {
        return open;
    }
    public boolean isReadOnly() {
        return readOnly;
    }
    public boolean isRecyclable() {
        return recyclable;
    }
    public boolean isResDb() {
        return resDb;
    }
    public boolean isResetAfterInstall() {
        return resetAfterInstall;
    }
    public boolean isStream() {
        return stream;
    }
    
    protected abstract void writeRecordEntry(RecordEntry entry, RandomAccessFile f) throws IOException;
    
    protected abstract int getRecordEntrySize();

    /**
     * Writes the database to the specified file. If the database is backed by a file,
     * all records are loaded first by calling {@link #loadAllRecords()}. Results are
     * unspecified if the target file is the same as the underlying file.
     * 
     * @param outfile the target file
     * @throws IOException
     */
    public void writeTo(File outfile) throws IOException {
        
        if (file != null) {
            loadAllRecords();
        }
        
        int recordCount = getRecordCount();
        RandomAccessFile raf = new RandomAccessFile(outfile, "rw");
        
        // skip header and record entries (we will write them after we have 
        // written the raw data)
        // header size + N bytes per record + 2 placeholder bytes
        int recordEntrySize = getRecordEntrySize(); 
        raf.seek(78 + recordEntrySize * recordCount + 2);

        // write AppInfoBlock if there is one
        if (appInfoBlockBytes != null) {
            appInfoId = (int) raf.getFilePointer();
            raf.write(appInfoBlockBytes);
        }
            
        // write SortInfoBlock if there is one
        if (sortInfoBlockBytes != null) {
            sortInfoId = (int) raf.getFilePointer();
            raf.write(sortInfoBlockBytes);
        }

        // Write raw record data
        for (int i = 0; i < recordCount; i++) {
            long pos0 = raf.getFilePointer();

            RecordEntry recordEntry = getRecordEntry(i);

            byte[] rawBytes = getRecordBytes(i);
            raf.write(rawBytes);

            // we can safely store the localChunkId in the record entry
            // because we will not need it for loading any more
            recordEntry.setLocalChunkId((int) pos0);
        }


        // Move back to beginning of file and write database header
        raf.seek(0);
        byte[] databaseHdr = new byte[0x48];
        populateHeader(databaseHdr);
        raf.write(databaseHdr);
        
        raf.writeInt(0); // nextRecordListID not used
        raf.writeShort(recordCount);

        // write record entries
        for (int i = 0; i < recordCount; i++) {
            RecordEntry recordEntry = getRecordEntry(i);
            writeRecordEntry(recordEntry, raf);
        }

        this.file = outfile;
        this.fileSize = raf.length();

        raf.close();
        
    }

    /**
     * Loads all record raw data from the underlying file into memory. This ensures that any 
     * further calls to #getRecordBytes will not need to access the underlying file.
     * @throws IOException
     *
     */
    public void loadAllRecords() throws IOException {
        if (file == null) {
            throw new IllegalStateException("No underlying file");
        }
        
        for (int i = 0; i < getRecordCount(); i++) {
            getRecordBytes(i);
        }
    }

    
    /**
     * Fills the specified byte array as a palm database header.
     * 
     * @param databaseHdr
     */
    private void populateHeader(byte[] databaseHdr) {
        if (! (databaseHdr.length == 0x48)) {
            throw new IllegalArgumentException("byte array size must be 0x48");
        }
        // name
        byte[] nameBytes = name.getBytes();
        System.arraycopy(nameBytes, 0, databaseHdr, 0, nameBytes.length);
        databaseHdr[nameBytes.length] = 0;
        
        short attributes = getAttributes();
        Bits.putShort(databaseHdr, 0x20, attributes);
        Bits.putShort(databaseHdr, 0x22, version);
        Bits.putInt(databaseHdr, 0x24, Bits.getPalmDate(creationDate));
        Bits.putInt(databaseHdr, 0x28, Bits.getPalmDate(modificationDate));
        Bits.putInt(databaseHdr, 0x2c, Bits.getPalmDate(lastBackupDate));
        Bits.putInt(databaseHdr, 0x30, modificationNumber);
        Bits.putInt(databaseHdr, 0x34, appInfoId);
        Bits.putInt(databaseHdr, 0x34, sortInfoId);
        
        byte[] typeBytes = type.getBytes();
        System.arraycopy(typeBytes, 0, databaseHdr, 0x3c, 4);
        byte[] creatorBytes = creator.getBytes();
        System.arraycopy(creatorBytes, 0, databaseHdr, 0x40, 4);
        
        Bits.putInt(databaseHdr, 0x44, uniqueIdSeed);
    }

    /**
     * Returns this database attributes as a bit-mapped field.
     */
    private short getAttributes() {
        short attributes = 0;
        attributes |= (isAppInfoDirty() ? dmHdrAttrAppInfoDirty : 0);
        attributes |= (isBackup() ? dmHdrAttrBackup : 0);
        attributes |= (isBundle() ? dmHdrAttrBundle : 0);
        attributes |= (isCopyPrevention() ? dmHdrAttrCopyPrevention : 0);
        attributes |= (isHidden() ? dmHdrAttrHidden : 0);
        attributes |= (isLaunchableData() ? dmHdrAttrLaunchableData : 0);
        attributes |= (isOkToInstallNewer() ? dmHdrAttrOKToInstallNewer : 0);
        attributes |= (isOpen() ? dmHdrAttrOpen : 0);
        attributes |= (isReadOnly() ? dmHdrAttrReadOnly : 0);
        attributes |= (isRecyclable() ? dmHdrAttrRecyclable : 0);
        attributes |= (isResDb() ? dmHdrAttrResDB : 0);
        attributes |= (isResetAfterInstall() ? dmHdrAttrResetAfterInstall : 0);
        attributes |= (isStream() ? dmHdrAttrStream : 0);
        return attributes;
    }

    /**
     * Returns the RecordEntry at position n. Note that if the database is backed by a file and the 
     * record's raw data has not yet been loaded, the {@link RecordEntry#getRawData()} will return null;
     * This is why {@link #getRecordBytes(int)} is the preferred way to access a record's raw data.
     */
    public RecordEntry getRecordEntry(int n) {
        return (RecordEntry) recordEntries.get(n);
    }
    
    public boolean isAppInfoDirty() {
        return appInfoDirty;
    }
    public boolean isOkToInstallNewer() {
        return okToInstallNewer;
    }

    /**
     * Add a record to the database at the specified index. The record raw
     * data have been set, otherwise an Exception is thrown. 
     * 
     * @throws IllegalArgumentException if the record does not have 
     * raw data associated to it.
     */
    public void addRecord(int n, RecordEntry entry) {
        if (entry.getRawData() == null) {
            throw new IllegalArgumentException("record entry has null raw data");
        }
        recordEntries.add(n, entry);
    }
    
    /**
     * Appends a record at the end of the record list. The record raw
     * data have been set, otherwise an Exception is thrown.
     * 
     * @param entry
     */
    public void addRecord(RecordEntry entry) {
        if (entry.getRawData() == null) {
            throw new IllegalArgumentException("record entry has null raw data");
        }
        recordEntries.add(entry);
    }
    
    /**
     * Remove a record from the database at the specified index
     */
    public void removeRecord(int n) {
        recordEntries.remove(n);
    }
    
    
    /**
     * Convenience method to open a palm database, whether it is a PDB or PRC.
     * 
     * @param file
     * @throws IOException
     */
    public static PalmDatabase open(File file) throws IOException {
        // try to open as PDB
        try {
            PdbFile pdbFile = new PdbFile(file);
            return pdbFile;
        }
        catch (PalmDbFormatException e) {
            // invalid format (not PDB): skip
        }
        catch (IOException e) {
            // other IOEXception: fail
            throw e;
        }

        // try to open as PRC
        try {
            PrcFile prcFile = new PrcFile(file);
            return prcFile;
        }
        catch (PalmDbFormatException e) {
            // invalid format (not PRC): skip
        }
        catch (IOException e) {
            // other IOEXception: fail
            throw e;
        }

        // fail
        throw new IOException("Failed to open " + file + "; file is corrupted or is not a palm database");
    }

    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (! (obj instanceof PalmDatabase)) {
            return false;
        }
        PalmDatabase other = (PalmDatabase) obj;
        
        try {
            if (! Bits.beanPropertiesEquals(this, other)) {
                return false;
            }
        
            this.loadAllRecords();
            other.loadAllRecords();
            for (int i = 0; i < getRecordCount(); i++) {
                RecordEntry entry0 = this.getRecordEntry(i);
                RecordEntry entry1 = other.getRecordEntry(i);
                if (! entry0.equals(entry1)) {
                    return false;
                }
            }
        }
        catch (Exception e) {
            throw new Error(e);
        }
        
        return true;
    }
    
}
