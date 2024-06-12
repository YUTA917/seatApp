FROM amazoncorretto:17-alpine AS build
COPY ./ /home/app
RUN cd /home/app && ./gradlew build

FROM amazoncorretto:17-alpine
COPY --from=build /home/seatApp/build/libs/seatApp-0.0.1-SNAPSHOT.jar build/lib/seatApp.jar
ENTRYPOINT ["java","-jar","build/libs/seatApp.jar"]