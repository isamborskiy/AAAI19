package sg.edu.nus.samborskii

import sg.edu.nus.samborskii.classifier.EarlyFusion
import sg.edu.nus.samborskii.weka.join
import weka.classifiers.trees.RandomForest
import weka.core.Instances
import weka.filters.Filter
import weka.filters.unsupervised.attribute.Normalize

fun main(args: Array<String>) {
    val reader = G2DReader()
    val groundTruth = reader.groundTruth()
    val features: List<Instances> = listOf(
            reader.genresData(),
            reader.genresTimeData(),
            reader.categoriesData(),
            reader.categoriesTimeData(),
            reader.gamesData(),
            reader.gamesTimeData())

    var joinedFeatures = features.first()
    features.drop(1).forEach { joinedFeatures = joinedFeatures.join(it) }

    val normalize = Normalize().apply { setInputFormat(joinedFeatures) }
    joinedFeatures = Filter.useFilter(joinedFeatures, normalize)

    val instances = joinedFeatures.join(groundTruth)

    val earlyFusionBuilder = { EarlyFusion(RandomForest()) }

    val evaluation = Evaluation(groundTruth.numClasses())
    val statistics = evaluation.crossValidation(earlyFusionBuilder, listOf(instances), 10)
    println(statistics)
}
