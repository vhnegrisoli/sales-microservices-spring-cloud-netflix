FROM gradle:6.6.1-jdk11 AS build
COPY --chown=gradle:gradle . /usr/product/src
WORKDIR /usr/product/src
RUN gradle build -x test --no-daemon
EXPOSE 8081
CMD ["gradle", "bootRun"]