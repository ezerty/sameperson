package com.mycompany.sameperson;

import com.mycompany.sameperson.utils.IndexCalculator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {

    public static void main(String[] argv) {
        new Main().run(argv);
    }

    private void run(String[] argv) {
        JavaSparkContext sparkContext = new JavaSparkContext("local", "MyApp");

        JavaRDD<String> inputData = sparkContext.textFile(argv[0]);
        JavaRDD<String> outputData = inputData.map((String t) -> {
            System.out.println(t);

            return String.format("%s|%s", IndexCalculator.INSTANCE.calculateIndex(t), t);
        });

        outputData.saveAsTextFile(argv[1]);

        sparkContext.stop();
    }

}
