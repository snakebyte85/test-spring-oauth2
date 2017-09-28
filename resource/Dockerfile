FROM openjdk:8-jre-alpine

ARG appname
ENV appname=${appname}
ENV JAVA_OPTS="-Xmx256m"
ENV APP_OPTS=""

RUN mkdir -p /opt/${appname}/
RUN mkdir -p /etc/${appname}/

COPY target/${appname}.jar /opt/${appname}/

EXPOSE 8080

ENTRYPOINT java "$JAVA_OPTS" -jar  /opt/${appname}/${appname}.jar --spring.config.location=file:///etc/${appname}/application.yml "$APP_OPTS"