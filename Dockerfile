FROM websphere-liberty:19.0.0.9-microProfile3

MAINTAINER IBM Java engineering at IBM Cloud

USER root
COPY /target/liberty/wlp/usr/servers/defaultServer /config/
COPY /target/liberty/wlp/usr/extension /opt/ibm/wlp/usr/extension

# To read LTPA keys
RUN chown 1001:0 /opt/ibm/wlp/usr/servers/defaultServer/resources/security/ltpa.keys
RUN chown 1001:0 /config/server.env
USER 1001

RUN echo "COUCHDB_HOST=$COUCHDB_HOST" >> /config/server.env
RUN echo "COUCHDB_PORT=$COUCHDB_PORT" >> /config/server.env

# Install required features if not present
RUN installUtility install --acceptLicense defaultServer

CMD ["/opt/ibm/wlp/bin/server", "run", "defaultServer"]

# Upgrade to production license if URL to JAR provided
ARG LICENSE_JAR_URL
RUN \
  if [ $LICENSE_JAR_URL ]; then \
    wget $LICENSE_JAR_URL -O /tmp/license.jar \
    && java -jar /tmp/license.jar -acceptLicense /opt/ibm \
    && rm /tmp/license.jar; \
  fi
