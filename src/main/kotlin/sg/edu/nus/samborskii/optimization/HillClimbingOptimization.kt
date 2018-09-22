package sg.edu.nus.samborskii.optimization

import java.util.*


class HillClimbingOptimization(
        minParams: DoubleArray,
        maxParams: DoubleArray,
        step: DoubleArray,
        logging: Boolean = true,
        private val numIter: Int = 30,
        private val numSteps: Int = 30
) : AbstractOptimization(minParams, maxParams, step, logging) {

    private val random = Random()

    override fun findParams(model: (DoubleArray) -> Double): DoubleArray {
        var bestParam = DoubleArray(minParams.size)
        var bestResult = -1.0

        for (i in 0 until numIter) {
            var initParams = randomVector()
            var initValue = model(initParams)
            var maxParams = initParams
            var maxValue = initValue

            for (j in 0 until numSteps) {
                for (l in 0 until 2 * initParams.size) {
                    val params = initParams.nextVector(l)
                    if (params != null) {
                        val value = model(params)
                        if (maxValue <= value) {
                            maxValue = value
                            maxParams = params
                            logger?.info("Find local maximum: $value (when params ${params.prettyString()})")
                        }
                    }
                }
                if (initValue == maxValue) break
                initParams = maxParams
                initValue = maxValue
                logger?.info("Finish step: $j/$numSteps")
            }

            if (bestResult < maxValue) {
                bestParam = maxParams
                bestResult = maxValue
                logger?.info("Iteration maximum: $bestResult (when params ${bestParam.prettyString()})")
            }
            logger?.info("Finish iteration: $i/$numIter")
        }

        logger?.info("The best params are: $bestResult (when params ${bestParam.prettyString()})")
        return bestParam
    }

    private fun DoubleArray.nextVector(direction: Int): DoubleArray? {
        val i = direction / 2
        if (this[i] == maxParams[i] || this[i] == minParams[i]) return null

        val copiedVector = Arrays.copyOf(this, this.size)
        if (direction % 2 == 0) {
            copiedVector[i] = Math.min(maxParams[i], copiedVector[i] + step[i])
        } else {
            copiedVector[i] = Math.max(minParams[i], copiedVector[i] - step[i])
        }
        return copiedVector
    }

    private fun randomVector(): DoubleArray = (0 until minParams.size)
            .map { minParams[it] + random.nextDouble() * (maxParams[it] - minParams[it]) }
            .toDoubleArray()
}
