import SimpleSearch.{FileName, InMemoryFile}
import org.scalatest.WordSpec

class SimpleSearchTest extends WordSpec {

  "SimpleSearch" when {
    "no arguments present" should {
      "produce an IllegalArgumentException" in {
        SimpleSearch.validArguments(Nil) match {
          case Right(_) => fail("should have thrown an error")
          case Left(exception) => assert(exception.isInstanceOf[IllegalArgumentException], "should have thrown an IllegalArgumentException")
        }
      }
    }

    "there are arguments present" should {
      "return false if there are more than one argument" in {
        SimpleSearch.validArguments(Seq("a", "a")) match {
          case Right(result) => assert(!result, "should have returned false")
          case Left(_) => fail("should not have thrown an error")
        }
      }

      "return true if only one argument is present" in {
        SimpleSearch.validArguments(Seq(".")) match {
          case Right(result) => assert(result, "should have returned true")
          case Left(_) => fail("should not have thrown an error")
        }
      }

      "is able to read the path in the arguments list" in {
        assert(SimpleSearch.getPath(Seq(".")).equals("."), "should have found the first element in the list")
      }
    }

    "a path is given" should {
      "list all the files contained in that path" in {
        val files: Seq[FileName] = SimpleSearch.getFiles("./src/test/resources/sample-directory")
        assert(files.head.equals("sample.txt"), "should have found the unique file inside the test/resources directory")
      }
    }

    "a filename is given" should {
      "read all the lines" in {
        val lines: Seq[String] = SimpleSearch.getFileLines("./src/test/resources/sample-directory/", "sample.txt")
        assert(lines.length == 1, "should have read the only line there is inside the file")
      }

      "convert all lines into InMemoryFile structures" in {
        val inMemoryFiles: Seq[InMemoryFile] = SimpleSearch.getInMemoryFiles("./src/test/resources/sample-directory/")
        println(inMemoryFiles)
      }
    }
  }

}
