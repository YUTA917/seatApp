FROM amazoncorretto:17-alpine AS build
COPY ./ home/app
RUN cd home/app
RUN ./gradlew build

FROM amazoncorretto:17-alpine
COPY --from=build /home/app/build/libs/seatApp-0.0.1-SNAPSHOT.jar build/lib/seatApp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","build/libs/seatApp.jar"]