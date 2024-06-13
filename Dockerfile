# FROM amazoncorretto:17-alpine AS build
FROM amazoncorretto:21-alpine AS build
COPY ./ home/app
RUN cd home/app && ls &&./gradlew build --stacktrace --info --scan

FROM amazoncorretto:21-alpine
COPY --from=build /home/app/build/libs/seatapp-0.0.1-SNAPSHOT.jar /usr/local/lib/seatapp.jar
EXPOSE 8080
# RUN cd build/libs && ls
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","/usr/local/lib/seatapp.jar"]