# SimulatedMapReduce

## What Is This

This repository provides a Maven project as a pre-assignment for Sakamoto's lectures about Hadoop (e.g. [BIGCHA](http://bigcha.net/) and TopSE).
The project includes a simulated Hadoop MapReduce framework working on a pure ~~Java environment~~. __JavaなんかやめてKotlin使おうぜ！__  
Note that this simulated framework has some differences from the official framework.

Please clone this repository and import it as a Maven project into ~~Eclipse~~ (or __Intellij IDEA__). ←絶対Intellij使おうな！  
You should see the detailed instructions in the Excercise[1-6]Main classes.

## How to Import Excercise Projects

1. Run [~~Eclipse IDE for Java Developers~~](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr) [IntelliJ IDEA the Java IDE](https://www.jetbrains.com/idea/)
2. ~~File > Import > Maven > Existing Maven Projects~~ File > New > Project from Existing Sources... で後はテキトーにやっとけ！
3. Set your directory which is cloned from GitHub as Root Directory
4. Select all projects
5. Push Finish

If you face a problem caused by the inconsistency between the required JDK version and your PC's JDK version, please modify the following statements in `pom.xml`.

```
		<maven.compiler.target>1.8</maven.compiler.target>
		<maven.compiler.source>1.8</maven.compiler.source>
```

__Kotlinの設定も必要だろ！__

## Excercises

1. Excercise 1  
Run `jp.ac.nii.exercise1.Excercise1Main` class.
2. Excercise 2  
Modify `jp.ac.nii.exercise2.Excercise2Main` class and add new classes.
3. Excercise 3  
Modify `jp.ac.nii.exercise3.Excercise3Main` class and add new classes.
4. Excercise 4  
Modify `jp.ac.nii.exercise4.AverageCalculationMapper/Reducer` and `jp.ac.nii.exercise4.StandardDeviationCalculationMapper/Reducer` classes.
5. Excercise 5  
Modify `jp.ac.nii.exercise5.AverageCalculationMapper/Reducer` and `jp.ac.nii.exercise5.StandardDeviationCalculationMapper/Reducer` classes.
6. Excercise 6  
Modify `jp.ac.nii.exercise6.AllPairAggregationMapper/Reducer`, `jp.ac.nii.exercise6.RelativityCalculationReducer/Job`, and `jp.ac.nii.exercise6.SpecPairAggregationMapper/Reducer` classes.
