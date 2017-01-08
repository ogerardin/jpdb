package oge.jpdb.test;/*
    JPDB, a Java library to read/write Palm OS database file formats.
    Copyright (C) 2005 Olivier G�rardin

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

import java.io.File;
import java.util.Date;

import junit.framework.TestCase;
import oge.jpdb.PalmDatabase;
import oge.jpdb.PdbFile;
import oge.jpdb.PrcFile;


/**
 * JUnit test cases for JPDB. 
 * Must be run from a directory where files SamplePRC.prc and SamplePDB.pdb can be found. 
 * 
 * @author OGE
 */
public class TestPalmDatabase extends TestCase {

    private static final ClassLoader classLoader = TestPalmDatabase.class.getClassLoader();

    private static final File SAMPLE_PDB = new File(classLoader.getResource("oge/jpdb/test/SamplePDB.pdb").getFile());

    private static final File SAMPLE_PRC = new File(classLoader.getResource("oge/jpdb/test/SamplePRC.prc").getFile());

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestPalmDatabase.class);
    }

    public void  testPdbRecognition() throws Exception {
        // read the original file
        PalmDatabase database = PalmDatabase.open(SAMPLE_PDB);
        assertTrue("Object is a PdbFile", database instanceof PdbFile);
    }

    public void  testPrcRecognition() throws Exception {
        // read the original file
        PalmDatabase database = PalmDatabase.open(SAMPLE_PRC);
        assertTrue("Object is a PrcFile", database instanceof PrcFile);
    }
    
    public void testChangeName() throws Exception {
        PalmDatabase database0 = PalmDatabase.open(SAMPLE_PRC);
        
        String name = "" + new Date().getTime();
        database0.setName(name);
        File SAMPLE_PRC_COPY = File.createTempFile("prccopy", ".prc");;
        database0.writeTo(SAMPLE_PRC_COPY);
        
        PalmDatabase database1 = PalmDatabase.open(SAMPLE_PRC_COPY);
        
        assertEquals(database1.getName(), name);
    }

    /**
     * Test a round-trip: PDB file -> PalmDatabase -> PDB file
     */
    public void testRoundTripPdb() throws Exception {
        // read the original file
        PalmDatabase database0 = PalmDatabase.open(SAMPLE_PDB);
        
        // save as a copy
        File SAMPLE_PDB_COPY = File.createTempFile("pdbcopy", ".pdb");
        database0.writeTo(SAMPLE_PDB_COPY);
        
        // read back copy
        PalmDatabase database1 = PalmDatabase.open(SAMPLE_PDB_COPY);
        
        // compare copy and original
        boolean equals = database1.equals(database0);
        if (! equals) {
            fail("equals() returned false");
        }
    }

    /**
     * Test a round-trip: PRC file -> PalmDatabase -> PRC file
     */
    public void testRoundTripPrc() throws Exception {
        // read the original file
        PalmDatabase database0 = PalmDatabase.open(SAMPLE_PRC);
        
        // save as a copy
        File SAMPLE_PRC_COPY = File.createTempFile("prccopy", ".prc");;
        database0.writeTo(SAMPLE_PRC_COPY);
        
        // read back copy
        PalmDatabase database1 = PalmDatabase.open(SAMPLE_PRC_COPY);
        
        // compare copy and original
        boolean equals = database1.equals(database0);
        if (! equals) {
            fail("equals() returned false");
        }
    }


    protected void setUp() throws Exception {
        super.setUp();
        assertTrue("The test file exists", SAMPLE_PDB.exists());
        assertTrue("The test file is readable", SAMPLE_PDB.canRead());
    }

}
