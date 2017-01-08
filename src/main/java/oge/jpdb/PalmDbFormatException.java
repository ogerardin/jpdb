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

import java.io.IOException;

/**
 * Signals that the file being read is invalid or corrupted.
 * 
 * @author Olivier Gérardin
 */
public class PalmDbFormatException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = -2188857296951813322L;

    public PalmDbFormatException() {
        super();
    }
    
    public PalmDbFormatException(String s) {
        super(s);
    }
    
}
