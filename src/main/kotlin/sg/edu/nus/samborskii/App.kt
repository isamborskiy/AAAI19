package sg.edu.nus.samborskii

import sg.edu.nus.samborskii.g2d.G2DReader
import sg.edu.nus.samborskii.g2d.toInstances
import weka.classifiers.Classifier
import weka.classifiers.Evaluation
import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.functions.LibSVM
import weka.classifiers.lazy.IBk
import weka.classifiers.trees.J48
import weka.classifiers.trees.RandomForest
import weka.filters.Filter
import weka.filters.unsupervised.attribute.Normalize
import java.util.*

private const val NUM_FOLDS = 10

private val CLASSIFIERS_BUILDER: Map<() -> Classifier, String> = mapOf(
        { IBk() } to "kNN",
        { NaiveBayes() } to "NaiveBayes",
        { J48() } to "DecisionTree",
        { LibSVM() } to "SVM",
        { RandomForest() } to "RandomForest"
)

private val READER = G2DReader()
private val DATA: Map<() -> Map<Int, List<Int>>, String> = mapOf(
        { READER.genresData() } to "genres",
        { READER.genresTimeData() } to "genres_time",
        { READER.gamesData() } to "games",
        { READER.gamesTimeData() } to "games_time"
)
private val GT = READER.groundTruth()
private val CLASS_NUM = GT.values.distinct().count()

fun main(args: Array<String>) {
    DATA.map { (dataReader, label) ->
        var instances = toInstances(dataReader(), GT)
        val filter = Normalize().apply { setInputFormat(instances) }
        instances = Filter.useFilter(instances, filter)

        println("--- $label ---")
        CLASSIFIERS_BUILDER.forEach { (builder, name) ->
            val classifier = builder()
            val evaluation = Evaluation(instances)
            evaluation.crossValidateModel(classifier, instances, NUM_FOLDS, Random(1))
            println(name)
            println(evaluation.getFMeasure(CLASS_NUM))
        }
        println()
    }
}
