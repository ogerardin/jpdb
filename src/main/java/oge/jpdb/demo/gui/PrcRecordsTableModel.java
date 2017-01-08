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

import oge.jpdb.PrcFile;
import oge.jpdb.PrcRecordEntry;


/**
 * A JTableModel that encapsulates the set of resources in a PrcFile
 * 
 * @author Olivier Gérardin
 */
public class PrcRecordsTableModel extends AbstractTableModel implements TableModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8229104304826829323L;

    private final PrcFile database;

    public PrcRecordsTableModel(PrcFile database) {
        this.database = database;
    }

    public int getColumnCount() {
        return 5;
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
            return String.class;
        case 3:
            return Integer.class;
        case 4:
            return String.class;
        default:
            return Object.class;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        PrcRecordEntry recordEntry = (PrcRecordEntry) database.getRecordEntry(rowIndex);
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
            return recordEntry.getTypeAsString();
        case 3:
            return new Integer(recordEntry.getId());
        case 4:
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
            return "Type";
        case 3:
            return "Id";
        case 4:
            return "Contents";
        default:
            return null;
        }
    }

}
