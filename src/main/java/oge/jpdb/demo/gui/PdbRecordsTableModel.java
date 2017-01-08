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
package oge.jpdb.demo.gui;

import java.io.IOException;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import oge.jpdb.PdbFile;
import oge.jpdb.PdbRecordEntry;


/**
 * A JTableModel that encapsulates the set of records in a PdbFile
 * 
 * @author Olivier Gérardin
 */
public class PdbRecordsTableModel extends AbstractTableModel implements TableModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 844375150987715645L;
    
    private final PdbFile database;

    public PdbRecordsTableModel(PdbFile database) {
        this.database = database;
    }

    public int getColumnCount() {
        return 8;
    }

    public int getRowCount() {
        return database.getRecordCount();
    }

    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
        case 0:
            return Integer.class;
        case 1:
            return Integer.class;
        case 2:
            return Integer.class;
        case 3:
            return Boolean.class;
        case 4:
            return Boolean.class;
        case 5:
            return Boolean.class;
        case 6:
            return Boolean.class;
        case 7:
            return String.class;
        default:
            return Object.class;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        PdbRecordEntry recordEntry = (PdbRecordEntry) database.getRecordEntry(rowIndex);
        byte[] recordBytes;
        try {
            recordBytes = database.getRecordBytes(rowIndex);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        switch(columnIndex) {
        case 0:
            return new Integer(rowIndex);
        case 1:
            return new Integer(recordBytes.length);
        case 2:
            return new Integer(recordEntry.getUniqueId());
        case 3:
            return Boolean.valueOf(recordEntry.isBusy());
        case 4:
            return Boolean.valueOf(recordEntry.isDirty());
        case 5:
            return Boolean.valueOf(recordEntry.isSecret());
        case 6:
            return Boolean.valueOf(recordEntry.isDelete());
        case 7:
            return new String(recordBytes);
        default:
            return null;
        }
    }

    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
        case 0:
            return "Index";
        case 1:
            return "Size";
        case 2:
            return "UniqueId";
        case 3:
            return "Busy";
        case 4:
            return "Dirty";
        case 5:
            return "Secret";
        case 6:
            return "Delete";
        case 7:
            return "Contents";
        default:
            return null;
        }
    }

}
