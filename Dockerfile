
#Should create folder /okeandra_project
#and put demo-0.0.1-SNAPSHOT.jar and this Dockerfile inside

# For creating image from this Dockerfile run the command:
# docker build -t okeandra_app .


# For running image run the command:
# docker run -d -p 80:8080 okeandra_app


FROM eclipse-temurin:11
RUN mkdir - /opt/okeandra
COPY demo-0.0.1-SNAPSHOT.jar /opt/okeandra
EXPOSE 8080
CMD ["java", "-jar", "/opt/okeandra/demo-0.0.1-SNAPSHOT.jar"]