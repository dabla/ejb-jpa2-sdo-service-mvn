To be able to build this maven project, you'll have to install some dependencies manually.  Some of them can be found under your jdeveloper installation
directory in oracle_common/modules.  You'll also have to download coherence 3.7 from the Oracle site:

http://www.oracle.com/technetwork/middleware/coherence/overview/index.html


You'll need to install following dependencies: 

call mvn install:install-file -Dfile=adfbcsvc-share.jar -DgroupId=com.oracle.adf.model -DartifactId=adfbcsvc-share -Dversion=11.1.1.4.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=adfm.jar -DgroupId=com.oracle.adf.model -DartifactId=adfm -Dversion=11.1.1.4.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=wsclient-rt.jar -DgroupId=com.oracle.webservices -DartifactId=wsclient-rt -Dversion=11.1.1 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=ojdbc14.jar -DgroupId=oracle.jdbc -DartifactId=ojdbc14 -Dversion=10.2.0.4.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=coherence.jar -DgroupId=com.oracle.coherence -DartifactId=coherence -Dversion=3.7.1.0b27797 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=coherence-jpa.jar -DgroupId=com.oracle.coherence -DartifactId=coherence-jpa -Dversion=3.7.1.0b27797 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=toplink-grid.jar -DgroupId=com.oracle.toplink -DartifactId=toplink-grid -Dversion=11.1.1 -Dpackaging=jar -DgeneratePom=true


To fix the "Absent Code attribute in method that is not native or abstract in class" error at runtime when executing the unit tests, I switched the javaee-api
dependency with the glassfish-embedded-all dependency.  Reason why this error occurs at runtime is explained here:

http://www.mkyong.com/maven/how-to-download-j2ee-api-javaee-jar-from-maven/


I followed this blog post to configure Coherence with JPA but did some modifications to make it run on Coherence 3.7:

http://blog.eisele.net/2011/03/high-performance-jpa-with-glassfish-and.html