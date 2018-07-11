FROM fabric8/java-jboss-openjdk8-jdk
ENV JAVA_APP_JAR driver.jar
ENV AB_OFF true
ADD target/$JAVA_APP_JAR /deployments/