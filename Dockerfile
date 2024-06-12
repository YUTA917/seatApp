FROM amazoncorretto:17-alpine AS build
COPY ./ /home/seatApp
RUN cd /home/seatApp && ./gradlew build

FROM amazoncorretto:17-alpine

COPY --from=build /home/app/build/libs/seatApp-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","build/libs/seatApp-0.0.1-SNAPSHOT.jar"]