package sg.edu.nus.samborskii.g2d

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.FileReader

class G2DReader(private val folder: String = "dataset") {

    fun groundTruth(): Map<Int, Int> = CSVParser(FileReader("$folder/g2d_gender.csv"), CSVFormat.DEFAULT).use { reader ->
        reader.records.map { it[0].toInt() to it[1].toInt() }.toMap()
    }

    fun gamesData(): Map<Int, List<Int>> = readData("g2d_games.csv")

    fun gamesTimeData(): Map<Int, List<Int>> = readData("g2d_games_time.csv")

    fun genresData(): Map<Int, List<Int>> = readData("g2d_genres.csv")

    fun genresTimeData(): Map<Int, List<Int>> = readData("g2d_genres_time.csv")

    private fun readData(filename: String): Map<Int, List<Int>> = CSVParser(FileReader("$folder/$filename"), CSVFormat.DEFAULT).use { reader ->
        reader.records.map { records ->
            records.first().toInt() to records.drop(1).map { it.toInt() }
        }.toMap()
    }
}
