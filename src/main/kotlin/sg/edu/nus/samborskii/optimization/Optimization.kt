package sg.edu.nus.samborskii.optimization

interface Optimization {

    fun findParams(model: (DoubleArray) -> Double): DoubleArray
}
