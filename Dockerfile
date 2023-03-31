
#Should create folder /okeandra_project
#and put okeandra.jar and this Dockerfile inside

# For creating image from this Dockerfile run the command:
# docker build -t okeandra_app .


# For running image run the command:
# docker run -d -p 80:8080 okeandra_app


FROM eclipse-temurin:11
RUN mkdir - /opt/okeandra
COPY okeandra.jar /opt/okeandra
EXPOSE 8080
CMD ["java", "-jar", "/opt/okeandra/okeandra.jar"]