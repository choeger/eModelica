eModelica Build Howto
=====================

This is a pom-first project built by maven2, to develop on it you will need to

	a) install your eclipse stuff into a (local) maven repository, by using:		
	mvn eclipse:to-maven -DstripQualifier=true -DeclipseDir=<YOUR_ECLIPSE_INSTALLATION>

	b) install a recent version of the page-runtime library (available from http://projects.uebb.tu-berlin.de/jgtlr) by issuing: 

	mvn install:install-file -Dfile=<PAGE_RUNTIME_JAR> -DartifactId=page-runtime -DgroupId=de.tuberlin.uebb.page -Dpackaging=jar

	c) issue the build via

	mvn package

	d) drop the emodelica jar from target/ to your eclipse installations plugin folder
