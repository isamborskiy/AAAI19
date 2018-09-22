package sg.edu.nus.samborskii.optimization

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class AbstractOptimization(
        protected val minParams: DoubleArray,
        protected val maxParams: DoubleArray,
        protected val step: DoubleArray,
        logging: Boolean = true
) : Optimization {

    protected val logger: Logger? = if (logging) LogManager.getLogger(AbstractOptimization::class.java) else null

    protected fun DoubleArray.prettyString(): String = joinToString(", ", "[", "]") { "%.3f".format(it) }
}
