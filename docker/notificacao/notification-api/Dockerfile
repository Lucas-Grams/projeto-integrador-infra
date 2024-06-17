FROM openjdk:17.0.1-jdk-slim-bullseye as BUILDER

WORKDIR /build

COPY . ./
# COPY settings.xml ./settings.xml
# ARG EXTRA_BUILD_ARGS
RUN --mount=type=cache,target=/root/.gradle/ \
    ./gradlew api:bootJar --no-daemon --info --stacktrace

# find JDK dependencies dynamically from jar
RUN jdeps \
    # dont worry about missing modules
    --ignore-missing-deps \
    # suppress any warnings printed to console
    -q \
    # java release version targeting
    --multi-release 17 \
    # output the dependencies at end of run
    --print-module-deps \
    # pipe the result of running jdeps on the app jar to file
    api/build/libs/api.jar > jre-deps.info


# new since last time!
RUN jlink --verbose \
    --compress 2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --output jre \
    --add-modules $(cat jre-deps.info)


# take a smaller runtime image for the final output
FROM debian:bullseye-slim
WORKDIR /deployment

# copy the custom JRE produced from jlink
COPY --from=BUILDER /build/jre jre

# copy the app
COPY --from=BUILDER /build/api/build/libs/api.jar api.jar


RUN groupadd -r api \
    && useradd --no-log-init -mr -g api api
USER api

EXPOSE 8080

ENV SERVER_CONTEXT_PATH="/"
HEALTHCHECK --start-period=10s --interval=15s --timeout=3s --retries=3 \
	CMD curl --fail "http://localhost:8080${SERVER_CONTEXT_PATH}/ping" || exit 1

CMD ["/deployment/jre/bin/java", "-Djava.security.egd=file:/dev/urandom", "-jar", "/deployment/api.jar"]
