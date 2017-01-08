
				JPDB, a Java library to read/write Palm OS file formats


Introduction
============

JPDB is a Java library intended to read and write Palm OS database storage
file formats, that is the format used to store Palm OS databases on a classical
filesystem. This includes PDB files (general purpose record-oriented databases) 
and PRC files (resource-oriented databases, including applications).

JPDB may be useful for Java applications that need to generate PDB or PRC files.


License
=======

JPDB is Free Software. JPDB is released under the terms of the LGPL, a copy of 
which is provided in the LICENSE.txt file.


Status
======

What JPDB currently does:
-read and write PDB and PRC files
-access all database and record attributes
-access record and resource contents as raw bytes

What JPDB does not:
-parse PQA files (not likely to be implemented, because a PQA is only a PDB 
with a specific format, and it is now becoming obsolete)

JPDB is largely untested and as such should not be considered stable.


Package contents
================

README.txt		this file
LICENSE.txt		the full text of the LGPL license

jpdb.jar		the JPDB library to include in your projects

DbInfoSwing.jar		self-executable interactive demo

src/oge/jpdb/			library sources (src package only)
src/oge/jpdb/demo		demo applications sources
src/oge/jpdb/demo/gui	interactive demo sources (main class DbInfoSwing)

doc/index.html		API documentation


What next ?
===========

1) Run the interactive demo:
If your JRE is configured properly, double-click on DbInfoSwing.jar and the 
application will start; otherwise open a command prompt, cd to where 
DbInfoSwing.jar is and type:

	java -cp DbInfoSwing.jar oge.jpdb.demo.gui.DbInfoSwing
	
2) Check out the other demos in src/oge/jpdb/demo

3) Read the API documentation in doc/index.html

4) Start using JPDB in your own projects!


Contact
=======
Comments, questions, suggestions: olivier@palmattitude.org

--
Olivier Gérardin, 2005-09-01