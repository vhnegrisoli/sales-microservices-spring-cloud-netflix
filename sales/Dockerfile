FROM gradle:6.6.1-jdk11 AS build
COPY --chown=gradle:gradle . /usr/sales/src
WORKDIR /usr/sales/src
RUN gradle build -x test --no-daemon
EXPOSE 8082
CMD ["gradle", "bootRun"]