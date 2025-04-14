.PHONY: build_jar clean run

all: build_jar

clean:
	mvn clean
	rm -f dependency-reduced-pom.xml

build_jar:
	mvn clean package

run:
	mvn javafx:run
