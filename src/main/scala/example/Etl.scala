package example

import com.github.tototoshi.csv._

import java.io.FileOutputStream

object Etl {
  def main(args: Array[String]): Unit = {
    val outputFile = new FileOutputStream("/tmp/output.jsonl")
    for {
      rawCsvRow <- extract("data/station-interchanges.csv")
      row <- clean(rawCsvRow)
      insight = transform(row)
      encodedRow = encodeRow(insight)
      _ = load(encodedRow, outputFile)
    } yield ()
  }

  def extract(filename: String): Iterator[Seq[String]] = {
    val reader = CSVReader.open(filename)
    reader.iterator
  }

  private def parseNumber(numberStr: String): Option[Int] =
    numberStr
      .replaceAll(",","")
      .toIntOption

  def clean(row: Seq[String]): Option[Row] = {
    for {
      stationCode <- row.lift(2)
      region <- row.lift(3)
      y2017 <- row.lift(25).flatMap(parseNumber)
      y2018 <- row.lift(26).flatMap(parseNumber)
      y2019 <- row.lift(27).flatMap(parseNumber)
      y2020 <- row.lift(28).flatMap(parseNumber)
      y2021 <- row.lift(29).flatMap(parseNumber)
    } yield Row(stationCode, region, y2017, y2018, y2019, y2020, y2021)
  }

  // Homework:
  def transform(row: Row): StationInterchangeInsights = ???
  def encodeRow(row: StationInterchangeInsights): String = ???
  def load(row: String, fileHandle: FileOutputStream): Unit = ???
}

case class Row(stationCode: String, region: String, y2017: Int, y2018: Int, y2019: Int, y2020: Int, y2021: Int)

case class StationInterchangeInsights(stationCode: String, region: String, prePandemic3YrAverage: Int, y2020: Int, initialDropPercent: Double, y2021: Int, laterDropPercent: Double)
