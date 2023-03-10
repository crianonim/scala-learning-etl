package example
import com.github.tototoshi.csv._

import java.io.FileOutputStream

object Etl {
  def main(args: Array[String]): Unit = {
    // read the csv file
    // extract the columns we need
    val outputFile = new FileOutputStream("/tmp/output.jsonl")
    for {
      rawCsvRow <- extract("data/station-interchanges.csv")
      row <- clean(rawCsvRow)
      insight = transform(row)
      encodedRow = encodeRow(insight)
      _ = load(encodedRow, outputFile)
    } yield ()
    // calculate
    // encode to json
    // write to file
  }

  def extract(fileName: String): Iterator[Seq[String]] = {
    ???
  }

  def clean(row: Seq[String]): Option[Row] = ???
  def transform(row: Row): StationInterchangeInsights = ???
  def encodeRow(insightsRow: StationInterchangeInsights): String = ???
  def load(rowString: String, fileHandle: FileOutputStream): Unit = ???

}

case class Row(stationCode: String, region: String, y2017: Int, y2018: Int, y2019: Int, y2020: Int, y2021: Int)

case class StationInterchangeInsights(stationCode: String, region: String, prePandemic3YrAverage: Int, y2020: Int, initialDropPercent: Double, y2021: Int, laterDropPercent: Double)