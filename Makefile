build:
	./gradlew clean build

install:
	./gradlew installDist

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

dev:
	./gradlew run

local:
	./gradlew bootRun --args='--spring.profiles.active=file'

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=prod'

check-java-deps:
	./gradlew dependencyUpdates -Drevision=release

.PHONY: build