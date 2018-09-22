package sg.edu.nus.samborskii

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import sg.edu.nus.samborskii.classifier.Classifier
import sg.edu.nus.samborskii.weka.deleteIdAttr
import weka.core.Instance
import weka.core.Instances

class Evaluation(private val numClasses: Int, logging: Boolean = true) {

    private val logger: Logger? = if (logging) LogManager.getLogger(Evaluation::class.java) else null

    fun crossValidation(classifierBuilder: () -> Classifier, datasetWithIds: List<Instances>, folds: Int, numIters: Int = 1, idAttrName: String = ID_ATTR_NAME): Statistics {
        val idToInstancesList = datasetWithIds.map { instances ->
            val idToInstances = instances.associateBy {
                it.stringValue(instances.attribute(idAttrName))
            }
            instances.deleteIdAttr()
            idToInstances
        }

        var globalFScore = 0.0
        var globalAccuracy = 0.0

        for (iter in 0 until numIters) {
            logger?.info("Evaluation iteration $iter/$numIters has started")

            val idsFolds = idToInstancesList.asSequence().first()
                    .map { it.key to it.value.classValue().toInt() }
                    .toMap()
                    .uniformChunked(folds)

            for (i in 0 until folds) {
                logger?.info("Evaluation fold $i/$folds has started")
                val confusionMatrix = (0 until numClasses).map { _ -> (0 until numClasses).map { 0.001 }.toMutableList() }

                val ids = idsFolds[i]
                val train = idToInstancesList
                        .asSequence()
                        .map { idToInstances -> idToInstances.filter { it.key !in ids }.map { it.value } }
                        .map { it.toInstances() }
                        .toList()
                val test = ids.map { id -> idToInstancesList.map { it[id]!! } }

                val classifier = classifierBuilder()
                classifier.buildClassifier(train)
                test.forEach {
                    val realClassId = it.first().classId()
                    val predictedClassId = classifier.classifyInstance(it).toInt()
                    confusionMatrix[realClassId][predictedClassId]++
                }

                val accuracy = (0 until numClasses).map { confusionMatrix[it][it] }.sum() / test.size
                val precision = (0 until numClasses).map { cls -> confusionMatrix[cls][cls] / (0 until numClasses).map { confusionMatrix[cls][it] }.sum() }.average()
                val recall = (0 until numClasses).map { cls -> confusionMatrix[cls][cls] / (0 until numClasses).map { confusionMatrix[it][cls] }.sum() }.average()
                val fScore = 2 * precision * recall / (precision + recall)

                globalAccuracy += accuracy
                globalFScore += fScore
            }
        }

        val times = numIters * folds
        return Statistics(globalFScore / times, globalAccuracy / times)
    }

    private fun Map<String, Int>.uniformChunked(folds: Int): List<Set<String>> {
        val classIds = keys.groupBy { get(it)!! }
        classIds.forEach { _, ids -> ids.shuffled() }
        return (0 until folds)
                .map { foldIndex ->
                    classIds.entries
                            .map { (_, ids) -> ids.chunked(ids.size / folds + 1) }
                            .flatMap { it[foldIndex] }
                            .toSet()
                }
    }

    private fun List<Instance>.toInstances(): Instances {
        val instance = first()
        val attributes = ArrayList((0 until instance.numAttributes()).map { instance.attribute(it) })

        val instances = Instances("", attributes, size)
        instances += this
        instances.setClassIndex(instance.classIndex())
        return instances
    }

    private fun Instance.classId(): Int = classValue().toInt()

    data class Statistics(val fScore: Double, val accuracy: Double) {

        override fun toString(): String = """
            F-score: $fScore
            Accuracy: $accuracy
        """.trimIndent()
    }
}
