javac -d out *.java &&  cd "./out/" && "../scripts/testReverse.cmd" %1 && "../scripts/testWordStat.cmd" %2 && "../scripts/testFastReverse.cmd"