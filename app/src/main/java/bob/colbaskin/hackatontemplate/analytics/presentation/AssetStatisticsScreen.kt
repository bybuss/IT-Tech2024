package bob.colbaskin.hackatontemplate.analytics.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.navigation.TopNavBar
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bob.colbaskin.hackatontemplate.analytics.utils.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.rememberVerticalLegend
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AssetStatisticsScreen(
    assetType: String,
    assetId: String,
    onScaffoldBackClick: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {

    val pointsData = viewModel.getAssetPoints(assetType, assetId)
    val modelProducer = remember { CartesianChartModelProducer() }

    Scaffold (
        topBar = {
            TopNavBar(
                onClick = onScaffoldBackClick,
                title = assetType
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ChartCard(
                pointsData = pointsData,
                modelProducer = modelProducer,
                modifier = Modifier,
            )
        }
    }
}

private val LegendLabelKey = ExtraStore.Key<Set<String>>()
private val DateMap = ExtraStore.Key<Map<Float, LocalDate>>()

@Composable
fun ChartCard(
    pointsData: Map<String, Map<LocalDate, Number>>,
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier
) {
    val lineColors = listOf(Color(0xFF77D879), Color(0xFF77D879), Color(0xFF2A8ED0))
    val legendItemLabelComponent = rememberTextComponent(vicoTheme.textColor)
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            val xToDates = pointsData.flatMap { (_, map) -> map.keys }
                .associateBy { it.toEpochDay().toFloat() }

            lineSeries {
                pointsData.forEach { (_, map) ->
                    val xValues = map.keys.map { it.toEpochDay().toFloat() }
                    val yValues = map.values
                    series(x = xValues, y = yValues)
                }
            }

            extras { extraStore ->
                extraStore[LegendLabelKey] = pointsData.keys
                extraStore[DateMap] = xToDates
            }
        }
    }

    val bottomAxisValueFormatter = CartesianValueFormatter { context, x, _ ->
        (context.model.extraStore[DateMap][x.toFloat()] ?: LocalDate.ofEpochDay(x.toLong()))
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    val RangeProvider =
        object : CartesianLayerRangeProvider {

            override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore): Double =
                pointsData.flatMap { it.value.values }
                    .minOfOrNull { it.toDouble() }
                    ?.let { it * 0.99 } ?: 0.0

//            override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore) =
//                -getMinY(minY, maxY, extraStore)
        }

    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    lineColors.map { color ->
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(color)),
                            areaFill = null,
                            pointProvider =
                            LineCartesianLayer
                                .PointProvider.single(
                                LineCartesianLayer.point(rememberShapeComponent(fill(color), CorneredShape.Pill))
                            ),
                        )
                    }
                ),
                rangeProvider = RangeProvider
            ),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(valueFormatter = bottomAxisValueFormatter),
            marker = rememberMarker(),
            legend =
            rememberVerticalLegend(
                items = { extraStore ->
                    extraStore[LegendLabelKey].forEachIndexed { index, label ->
                        add(
                            LegendItem(
                                shapeComponent(fill(lineColors[index]), CorneredShape.Pill),
                                legendItemLabelComponent,
                                label,
                            )
                        )
                    }
                },
                padding = insets(top = 16.dp),
            )
        ),
        modelProducer,
        modifier.height(304.dp),
        rememberVicoScrollState(scrollEnabled = false),
    )
}

@Preview
@Composable
fun AssetStatisticsScreenPreview() {
    AssetStatisticsScreen(
        assetType = "shares",
        assetId = "asdsa",
        onScaffoldBackClick = {}
    )
}
