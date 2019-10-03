.PHONY: test

test:
	./config/travis/run-checks.sh && \
	./gradlew clean checkstyleMain checkstyleTest test coverage coveralls asciidoctor
