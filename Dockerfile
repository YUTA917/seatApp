FROM ubuntu:latest AS build
COPY ./ /home/app
RUN cd /home/app && ./gradlew build


COPY --from=build /home/app/build/libs/spring-render-deploy-0.0.1-SNAPSHOT.jar /usr/local/lib/spring-render-deploy.jar

CMD ["java","-jar","build/libs/server.jar"]