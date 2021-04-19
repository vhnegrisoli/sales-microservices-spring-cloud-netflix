FROM gradle:6.6.1-jdk11 AS build
COPY --chown=gradle:gradle . /usr/gateway/src
WORKDIR /usr/gateway/src
RUN gradle build -x test --no-daemon
EXPOSE 3000
CMD ["gradle", "bootRun"]