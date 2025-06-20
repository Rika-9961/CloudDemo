FROM openjdk:17-ea-slim
ENV JAVA_ARGS="-Dfile.encoding=UTF8 -Duser.timezone=GMT+08"
ENV JAR_FILE="api-gateway-0.0.1-SNAPSHOT.jar"
COPY ./target/${JAR_FILE} ./
EXPOSE 80
CMD java -jar ${JAVA_ARGS} ${JAVA_OPTS} /${JAR_FILE}