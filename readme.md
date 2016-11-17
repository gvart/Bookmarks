# Developers Sky Application

## Running Sky Application locally
### Before run project setup your tomcat application! (IP, USER, PASSWORD) like this:
server.xml
```
<Connector
    port="8080"
    protocol="HTTP/1.1"
	address="localhost"
    connectionTimeout="20000"
    redirectPort="8443" />

```
tomcat-user.xml
```
<role rolename="manager"/>
<role rolename="manager-script"/>
<role rolename="manager-gui"/>
<user username="admin" password="admin" roles="manager,manager-gui,manager-script"/>
```
### To run do:
```
	git clone https://github.com/gvart/bookmarks.git
	cd bookmarks
	mvn  tomcat7:deploy
```

You can then access bookmarks here: http://localhost:8080/




