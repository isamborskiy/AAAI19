package sg.edu.nus.samborskii

import weka.classifiers.Evaluation

fun Evaluation.getPrecision(numClasses: Int = confusionMatrix().size): Double = (0 until numClasses).map { precision(it) }.mapNaNto0().average()

fun Evaluation.getRecall(numClasses: Int = confusionMatrix().size): Double = (0 until numClasses).map { recall(it) }.mapNaNto0().average()

fun Evaluation.getFMeasure(numClasses: Int = confusionMatrix().size): Double {
    val precision = getPrecision(numClasses)
    val recall = getRecall(numClasses)
    return 2 * precision * recall / (precision + recall)
}

fun Evaluation.getConfusionMatrix(): List<List<Int>> = confusionMatrix().map { row -> row.map { it.toInt() } }

private fun List<Double>.mapNaNto0(): List<Double> = map { if (it.isNaN()) 0.0 else it }
