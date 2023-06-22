FROM openjdk:17-jdk-slim-buster
RUN apk add --no-cache tzdata
ENV TZ America/Bogota
ENV APP_DIR /usr/share/app

RUN adduser -D java

RUN mkdir -p $APP_DIR && \
     chown -R java $APP_DIR

ENV SERVER_PORT 8080
EXPOSE $SERVER_PORT
ENV CONNECT_PORT 22
EXPOSE $CONNECT_PORT

USER java

ENV JAR_NAME \
    pignoraticio-0.0.1-SNAPSHOT

COPY $JAR_NAME.jar $APP_DIR/

CMD ["sh", "-c", "java -jar $APP_DIR/$JAR_NAME.jar"]
