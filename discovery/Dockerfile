FROM gradle:6.6.1-jdk11 AS build
COPY --chown=gradle:gradle . /usr/discovery/src
WORKDIR /usr/discovery/src
RUN gradle build -x test --no-daemon
EXPOSE 8083
CMD ["gradle", "bootRun"]