.PHONY: build
build:
	@javac *.java

.PHONY: clean
clean:
	@find . -name "*.class" -delete

.PHONY: execute
execute: build
	@java SistemaLocadora
	@$(MAKE) clean --no-print-directory

.PHONY: run
run: execute