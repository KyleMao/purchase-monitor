package graph

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import breeze.plot._

import utils.ConfigReader
import distribution.DistributionFactory
import period.PeriodFactory
import types.DistType
import types.PeriodType

/** An class that implements the graph drawing methods.
  * 
  * @author Zexi Mao
  */
class Graph {
  val cr = new ConfigReader
  val gd = cr.getGraphDir
  val lh = new LabelHelper

  def plotDist(t: DistType.Value) = {
    val dist = getDist(t)
    val y = dist.getCnts
    val x = linspace(1, y.length, y.length)
    val f = Figure()
    val p = f.subplot(0)
    p += plot(x, y)
    val (xl, yl, sn) = lh.plotDistHelper(t, gd)
    p.xlabel = xl
    p.ylabel = yl
    f.saveas(sn)
  }

  def plotPeriodHistory(t: PeriodType.Value) = {
    val y = getHistory(t)
    val x = linspace(1, y.length, y.length)
    val f = Figure()
    val p = f.subplot(0)
    p += plot(x, y)
    val (xl, yl, tt, sn) = lh.plotPeriodHelper(t, gd)
    p.xlabel = xl
    p.ylabel = yl
    p.title = tt
    f.saveas(sn)
  }

  def plotHistory(t: DistType.Value, id: String) = {
    val dist = getDist(t)
    if (dist.isObjectPresent(id)) {
      val y = dist.getWeeklyHistory(id)
      val x = linspace(1, y.length, y.length)
      val f = Figure()
      val p = f.subplot(0)
      p += plot(x, y)
      val (xl, yl, tt, sn) = lh.plotHistoryHelper(t, gd, id)
      p.xlabel = xl
      p.ylabel = yl
      p.title = tt
      f.saveas(sn)
    } else println(s"Invalid identifier!")
  }

  def drawHist(t: PeriodType.Value) = {
    val ph = getHistory(t)
    val f = Figure()
    val p = f.subplot(0)
    p += hist(ph, cr.getBinNum(t))
    val (xl, yl, tt, sn) = lh.drawPHistHelper(t, gd)
    p.xlabel = xl
    p.ylabel = yl
    p.title = tt
    f.saveas(sn)
  }

  def drawClusterHist(t: DistType.Value, nCluster: Int) = {
    if ((nCluster >= 3) && (nCluster <= 9)) {
      val dist = getDist(t)
      val (bins, sizes) = dist.getKmeansRange(nCluster)
      drawHist(t, bins, sizes)
    } else println("Invalid number of clusters!")
  }

  def drawClusterHist(t: DistType.Value) = {
    val dist = getDist(t)
    val (bins, sizes) = dist.getKmeansRange
    drawHist(t, bins, sizes)
  }

  private def drawHist(t: DistType.Value, bins: Array[Int], sizes: Array[Int]) = {
    val f = Figure()
    val p = f.subplot(0)
    val buf = ArrayBuffer.empty[Int]
    val disc = new StringBuilder
    val (xl, yl, tt, sn) = lh.drawHistHelper(t, gd)
    disc ++= tt

    for ((size, i) <- sizes.view.zipWithIndex) {
      buf ++= Array.fill[Int](size)(i+1)
      val low = bins(i)
      val high = bins(i+1) - 1
      disc ++= s"  $low-$high: $size"
    }
    p += hist(buf.toArray, sizes.length)
    p.xlabel = xl
    p.ylabel = yl
    p.title = disc.toString
    f.saveas(sn)
  }
  
  private def getDist(t: DistType.Value) = {
    val df = new DistributionFactory
    df.createDist(t)
  }

  private def getHistory(t: PeriodType.Value) = {
    val pf = new PeriodFactory
    val ps = pf.createPeriod(t)
    ps.getAllHistory
  }
  
}
