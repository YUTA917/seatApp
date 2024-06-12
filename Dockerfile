FROM amazoncorretto:17-alpine AS build
COPY ./ /home/app
RUN cd /home/app && ./gradlew build

FROM amazoncorretto:17-alpine

COPY --from=build /home/app/build/libs/spring-render-deploy-0.0.1-SNAPSHOT.jar /usr/local/lib/seatApp.jar

ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","build/libs/seatApp.jar"]