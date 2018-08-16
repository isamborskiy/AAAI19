package sg.edu.nus.samborskii

import sg.edu.nus.samborskii.g2d.G2DReader
import sg.edu.nus.samborskii.g2d.toInstances
import weka.classifiers.Evaluation
import weka.classifiers.trees.RandomForest
import java.util.*


fun main(args: Array<String>) {
    val reader = G2DReader()

    val data = mapOf(
            reader.genresData() to "genres",
            reader.genresTimeData() to "genres_time",
            reader.gamesData() to "games",
            reader.gamesTimeData() to "games_time"
    )
    val gt = reader.groundTruth()

    data.map { (dataMap, label) ->
        val instances = toInstances(dataMap, gt)

        val classifier = RandomForest()

        val evaluation = Evaluation(instances)
        evaluation.crossValidateModel(classifier, instances, 10, Random(1))
        println(label)
        println(evaluation.getFMeasure(2))
        println()
    }
}
