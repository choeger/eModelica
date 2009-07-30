/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.chart.device.IDeviceRenderer;
import org.eclipse.birt.chart.device.IDisplayServer;
import org.eclipse.birt.chart.device.swt.SwtDisplayServer;
import org.eclipse.birt.chart.exception.ChartException;
import org.eclipse.birt.chart.factory.GeneratedChartState;
import org.eclipse.birt.chart.factory.Generator;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.ChartWithAxes;
import org.eclipse.birt.chart.model.attribute.Bounds;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.LegendItemType;
import org.eclipse.birt.chart.model.attribute.impl.BoundsImpl;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;
import org.eclipse.birt.chart.model.component.Axis;
import org.eclipse.birt.chart.model.component.Series;
import org.eclipse.birt.chart.model.component.impl.SeriesImpl;
import org.eclipse.birt.chart.model.data.NumberDataSet;
import org.eclipse.birt.chart.model.data.SeriesDefinition;
import org.eclipse.birt.chart.model.data.TextDataSet;
import org.eclipse.birt.chart.model.data.impl.NumberDataSetImpl;
import org.eclipse.birt.chart.model.data.impl.SeriesDefinitionImpl;
import org.eclipse.birt.chart.model.data.impl.TextDataSetImpl;
import org.eclipse.birt.chart.model.impl.ChartWithAxesImpl;
import org.eclipse.birt.chart.model.type.BarSeries;
import org.eclipse.birt.chart.model.type.impl.BarSeriesImpl;
import org.eclipse.birt.chart.util.PluginSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.sun.xml.internal.ws.api.server.Container;

/**
 * @author choeger
 * 
 */
public class SimpleGraph extends Canvas implements PaintListener, Listener {

	private List<Curve> curves = new ArrayList<Curve>();
	private int squareDist = 100;

	private IDeviceRenderer render;
	private Image cachedImage;
	private GeneratedChartState state;
	private Chart chart;
	private Axis xAxis;
	private Axis yAxis;

	public SimpleGraph(Composite parent, int style) {
		super(parent, style);

		PluginSettings ps = PluginSettings.instance();
		try {
			render = ps.getDevice("dv.SWT");
		} catch (ChartException e) {
			e.printStackTrace();
		}
		createChart();
		addPaintListener(this);
		addListener(SWT.Resize, this);
	}

	@Override
	public void drawBackground(GC gc, int x, int y, int width, int height) {
		gc.setBackground(new Color(gc.getDevice(), 255, 255, 255));
		gc.fillRectangle(x, y, width, height);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Composite co = (Composite) e.getSource();
		final Rectangle rect = co.getClientArea();

		if (cachedImage == null) {
			render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, e.gc);
			buildChart();
			drawToCachedImage(rect);
		}
		e.gc.drawImage(cachedImage, 0, 0, cachedImage.getBounds().width,
				cachedImage.getBounds().height, 0, 0, rect.width, rect.height);
	}

	private void buildChart() {
		Point size = getSize();
		Bounds bo = BoundsImpl.create(0, 0, size.x, size.y);
		IDisplayServer displayServer = render.getDisplayServer();
		int resolution = displayServer.getDpiResolution();
		bo.scale(72d / resolution);
		try {
			Generator gr = Generator.instance();
			state = gr.build(displayServer, chart, bo, null);
		} catch (ChartException ex) {
			System.err.println(ex.toString());
		}
	}

	public void drawToCachedImage(Rectangle size) {
		GC gc = null;
		try {
			if (cachedImage != null)
				cachedImage.dispose();
			cachedImage = new Image(Display.getCurrent(), size);

			gc = new GC(cachedImage);
			render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, gc);

			Generator gr = Generator.instance();
			gr.render(render, state);
		} catch (ChartException ex) {
			System.err.println(ex);
		} finally {
			if (gc != null)
				gc.dispose();
		}
	}

	/**
	 * @return the curves
	 */
	public List<Curve> getCurves() {
		return curves;
	}

	/**
	 * @param curves
	 *            the curves to set
	 */
	public void setCurves(List<Curve> curves) {
		this.curves = curves;
		createChart();
	}

	private void createChart() {
		chart = ChartWithAxesImpl.create();

		chart.setDimension(ChartDimension.TWO_DIMENSIONAL_LITERAL);
		chart.getPlot().setBackground(ColorDefinitionImpl.WHITE());
		chart.getPlot().getClientArea().setBackground(
				ColorDefinitionImpl.WHITE());
		chart.getLegend().setItemType(LegendItemType.CATEGORIES_LITERAL);
		chart.getLegend().setVisible(true);

		chart.getTitle().getLabel().getCaption().setValue("CharT!");
		chart.getTitle().getLabel().getCaption().getFont().setSize(14);
		chart.getTitle().getLabel().getCaption().getFont().setName("Courier");

		xAxis = ((ChartWithAxes) chart).getPrimaryBaseAxes()[0];
		xAxis.getTitle().setVisible(true);
		xAxis.getTitle().getCaption().setValue("X");

		yAxis = ((ChartWithAxes) chart).getPrimaryOrthogonalAxis(xAxis);
		yAxis.getTitle().setVisible(true);
		yAxis.getTitle().getCaption().setValue("Y");
		yAxis.getScale().setStep(1.0);

		TextDataSet categoryValues = TextDataSetImpl.create(new String[] {"one","two"});
		Series seCategory = SeriesImpl.create();
		seCategory.setDataSet(categoryValues);
		SeriesDefinition sdX = SeriesDefinitionImpl.create();
		sdX.getSeriesPalette().update(1);
		xAxis.getSeriesDefinitions().add(sdX);
		sdX.getSeries().add(seCategory);

		NumberDataSet orthoValuesDataSet1 = NumberDataSetImpl.create(new double[] {1.0,10.0});

		BarSeries bs1 = (BarSeries) BarSeriesImpl.create();
		bs1.setDataSet(orthoValuesDataSet1);

		SeriesDefinition sdY = SeriesDefinitionImpl.create();
		yAxis.getSeriesDefinitions().add(sdY);
		sdY.getSeries().add(bs1);

	}

	@Override
	public void handleEvent(Event event) {
		if (event.type == SWT.Resize && cachedImage != null) {
			System.err.println("resize");
			
			render.setProperty(IDeviceRenderer.GRAPHICS_CONTEXT, new GC(cachedImage));
			buildChart();
			drawToCachedImage(this.getBounds());
		}
	}
}
