# 318732484
# foglero
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	rm sources.txt

bin:
	mkdir bin

run:
	java -cp biuoop-1.4.jar:ass6game.jar:resources Ass6Game

jar:
	jar -cfm ass6game.jar manifest.txt -C bin . -C resources .
	
