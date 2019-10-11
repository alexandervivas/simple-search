import java.io.File

import scala.collection.mutable
import scala.io.Source

object SimpleSearch extends App {

  val memoizedCostsWords = mutable.Map[(Word, Word), Double]()

  validArguments(args) match {
    case Right(isValid) => if(isValid) {
      val pathName: PathName = getPath(args)
      val inMemoryFiles: Seq[InMemoryFile] = getInMemoryFiles(pathName)
      println(s"${inMemoryFiles.length} files read in directory $pathName")
      var input = scala.io.StdIn.readLine("search> ")
      while(!input.equals(":quit")) {
        val words: Seq[Word] = getWords(input)
        val results: Seq[(FileName, Double)] = inMemoryFiles.map { inMemoryFile =>
          val wordProbabilities = words.map(wordProbability(inMemoryFile))
          (inMemoryFile.filename, wordProbabilities.sum / wordProbabilities.length)
        }.filter(_._2 > 0.0d)
        if(results.isEmpty) println("no matches found")
        else results.foreach { case(filename, probability) => println(s"$filename:${(probability * 100).toInt}%") }
        input = scala.io.StdIn.readLine("search> ")
      }
    }
    case Left(exception) => println(exception.getMessage)
  }

  type Argument = String
  type FileName = String
  type PathName = String
  type FileLine = String
  type Word = String

  case class InMemoryFile(filename: FileName, words: Set[Word])

  def validArguments(arguments: Seq[Argument]): Either[Exception, Boolean] =
    if(arguments.isEmpty) Left(new IllegalArgumentException("No directory given to index."))
    else Right(arguments.length == 1)

  def getFiles(pathName: PathName): Seq[FileName] = new File(pathName).listFiles().filter(_.isFile).map(_.getName)

  def getPath(arguments: Seq[Argument]): PathName = arguments.head

  def getFileLines(path: PathName, name: FileName): Seq[FileLine] = {
    val bufferedSource = Source.fromFile(Seq(path, name).mkString("/"))
    val fileLines: Seq[FileName] = bufferedSource.getLines.toSeq
    bufferedSource.close
    fileLines
  }

  def getInMemoryFiles(pathName: PathName): Seq[InMemoryFile] = getFiles(pathName)
    .map(fileName =>
      InMemoryFile(
        fileName,
        getFileLines(pathName, fileName)
          .foldLeft[Set[Word]](Set.empty) { (current: Set[Word], next: FileLine) => current ++ getWords(next).toSet }
      )
    )

  def getWords(inputLine: String): Seq[Word] = inputLine.split(" +").map(_.replaceAll("[,.!?:;]", ""))

  def wordProbability(inMemoryFile: InMemoryFile)(word: Word): Double = inMemoryFile.words.map(wordEquality(word)).max

  def wordEquality(search: Word)(target: Word): Double = {
    val s1: String = search
    val s2: String = target
    val memoizedCosts = mutable.Map[(Int, Int), Int]()

    def levenshtein: ((Int, Int)) => Int = {
      case (k1, k2) =>
        memoizedCosts.getOrElseUpdate((k1, k2), (k1, k2) match {
          case (i, 0) => i
          case (0, j) => j
          case (i, j) =>
            Seq(
              1 + levenshtein((i - 1, j)),
              1 + levenshtein((i, j - 1)),
              levenshtein((i - 1, j - 1)) + (if (s1(i - 1) != s2(j - 1)) 1 else 0)
            ).min
        })
    }

    memoizedCostsWords.get((search, target)) match {
      case Some(result) => result
      case None =>
        levenshtein((s1.length, s2.length))
        val bigger = Seq(s1.length, s2.length).max.toDouble
        val result = (bigger - memoizedCosts(s1.length, s2.length).toDouble) / bigger
        memoizedCostsWords.put((s1, s2), result)
        result
    }
  }

}
