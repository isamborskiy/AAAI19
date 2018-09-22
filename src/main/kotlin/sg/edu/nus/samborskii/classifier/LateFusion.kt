package sg.edu.nus.samborskii.classifier

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import weka.core.DenseInstance
import weka.core.Instance
import weka.core.Instances

class LateFusion(
        private val classifiers: List<weka.classifiers.Classifier>,
        private var weights: List<Double> = classifiers.map { 1.0 },
        logging: Boolean = true
) : Classifier {

    private val logger: Logger? = if (logging) LogManager.getLogger(LateFusion::class.java) else null

    init {
        assert(classifiers.size == weights.size)
    }

    fun setWeights(weights: List<Double>) {
        assert(classifiers.size == weights.size)
        this.weights = weights
    }

    override fun buildClassifier(datasets: List<Instances>) {
        assert(datasets.size == classifiers.size)
        classifiers.zip(datasets).forEach { (classifier, instances) ->
            classifier.buildClassifier(instances)
        }
        logger?.info("Finish classifiers building")
    }

    override fun classifyInstance(instanceList: List<Instance>): Double {
        assert(instanceList.size == classifiers.size)
        val distribution = DoubleArray(instanceList.first().classAttribute().numValues())
        instanceList.forEachIndexed { index, instance ->
            val classifier = classifiers[index]
            val instances = instance.toInstances()
            val classesValues = classifier.distributionForInstance(instances.firstInstance())
            classesValues.forEachIndexed { classValue, value -> distribution[classValue] += weights[classValue] * value }
        }

        var maxValue = 0.0
        var maxIndex = 0
        distribution.forEachIndexed { index, value ->
            if (value > maxValue) {
                maxValue = value
                maxIndex = index
            }
        }
        return maxIndex.toDouble()
    }
}
