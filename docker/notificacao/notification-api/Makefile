.PHONY: run
run:
	./mvnw clean compile spring-boot:run



.PHONY: run
docker-build:
	docker build \
		--platform linux/amd64 \
		-t registry.gitlab.com/pdsa-rs/notification-api \
		.
