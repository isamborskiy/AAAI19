package sg.edu.nus.samborskii.optimization

import java.util.*


class BruteForceOptimization(
        minParams: DoubleArray,
        maxParams: DoubleArray,
        step: DoubleArray,
        logging: Boolean = true
) : AbstractOptimization(minParams, maxParams, step, logging) {

    private val maxParamsMul: IntArray = (0 until minParams.size)
            .map { (maxParams[it] - minParams[it]) / step[it] }
            .map { it.toInt() }
            .toIntArray()

    override fun findParams(model: (DoubleArray) -> Double): DoubleArray {
        val vector = IntArray(step.size)
        var bestVector = Arrays.copyOf(vector, vector.size)
        var bestResult = model(vector.generateParams())
        logger?.info("Initial maximum: $bestResult (when params ${bestVector.generateParams().prettyString()})")

        while (vector.hasNextVector()) {
            vector.nextVector()
            val curParams = vector.generateParams()

            logger?.info("Current vector is: ${curParams.prettyString()}")
            val result = model(curParams)

            if (result > bestResult) {
                bestResult = result
                bestVector = Arrays.copyOf(vector, vector.size)
                logger?.info("Iteration maximum: $bestResult (when params ${bestVector.generateParams().prettyString()})")
            }
        }

        logger?.info("The best params are: $bestResult (when params ${bestVector.generateParams().prettyString()})")
        return bestVector.generateParams()
    }

    private fun IntArray.generateParams(): DoubleArray = mapIndexed { index, value -> minParams[index] + value * step[index] }.toDoubleArray()

    private fun IntArray.hasNextVector(): Boolean = filterIndexed { index, value -> value != maxParamsMul[index] }.isNotEmpty()

    private fun IntArray.nextVector() {
        for (i in indices) {
            this[i]++
            if (this[i] > maxParamsMul[i]) this[i] = 0 else break
        }
    }
}
