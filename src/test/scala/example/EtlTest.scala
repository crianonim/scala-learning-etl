package example

import com.github.tototoshi.csv.{CSVParser, defaultCSVFormat}
import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class EtlTest extends AnyFreeSpec with Matchers with OptionValues {
  val parser = new CSVParser(defaultCSVFormat)

  "clean" - {
    "header row returns None" in {
      val str = """Station name,National Location Code (NLC),"Three Letter Code(TLC)",Region,Local authority: district or unitary,Apr 1997 to Mar 1998,Apr 1998 to Mar 1999,Apr 1999 to Mar 2000,Apr 2000 to Mar 2001,Apr 2001 to Mar 2002,Apr 2002 to Mar 2003,Apr 2003 to Mar 2004,Apr 2004 to Mar 2005,Apr 2005 to Mar 2006,Apr 2006 to Mar 2007,Apr 2007 to Mar 2008,Apr 2008 to Mar 2009,Apr 2009 to Mar 2010,Apr 2010 to Mar 2011,Apr 2011 to Mar 2012,Apr 2012 to Mar 2013,Apr 2013 to Mar 2014,Apr 2014 to Mar 2015,Apr 2015 to Mar 2016,Apr 2016 to Mar 2017,Apr 2017 to Mar 2018,Apr 2018 to Mar 2019,Apr 2019 to Mar 2020,Apr 2020 to Mar 2021,Apr 2021 to Mar 2022"""
      val row = parser.parseLine(str).value
      Etl.clean(row) shouldEqual None
    }
    "incomplete data row returns None" in {
      val str = """Maghull North,6576,MNS,North West,Sefton,[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],[z],"339,066","517,166","138,794","400,764""""
      val row = parser.parseLine(str).value
      Etl.clean(row) shouldEqual None
    }
    "complete data row returns the correct values" in {
      val str = """Old Street,6003,OLD,London,Islington,[z],[z],[z],[z],[z],[z],[z],[z],[z],"733,612","813,166","827,762","1,326,797","1,434,785","1,336,722","1,396,260","1,455,920","1,682,134","3,611,484","5,323,546","5,756,246","7,119,730","6,768,052","2,230,872","3,672,524""""
      val row = parser.parseLine(str).value
      val expected = Row("OLD","London",5756246,7119730,6768052,2230872,3672524)
      Etl.clean(row) shouldEqual Some(expected)
    }
  }

}
