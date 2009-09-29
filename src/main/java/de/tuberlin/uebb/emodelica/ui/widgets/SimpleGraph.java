/**
 * 
 */
package de.tuberlin.uebb.emodelica.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.LineAttributes;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import de.tuberlin.uebb.emodelica.model.experiments.ICurve;

/**
 * @author choeger
 * 
 */
public class SimpleGraph extends Canvas implements PaintListener,
		MouseListener, MouseMoveListener {
	private float viewX = 0;
	private float viewY = 0;
	private float scaleF = 5.0f;

	private List<ICurve> curves = new ArrayList<ICurve>();
	float mousePos[] = new float[2];
	private float stepWidth;

	Transform center;
	Transform view;
	Transform scale;
	Transform iCenter;
	Transform iView;
	Transform iScale;

	private float startViewX;
	private float startViewY;

	public SimpleGraph(Composite parent, int style) {
		super(parent, style | SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND);

		view = new Transform(getDisplay());
		scale = new Transform(getDisplay());
		center = new Transform(getDisplay());
		iView = new Transform(getDisplay());
		iScale = new Transform(getDisplay());
		iCenter = new Transform(getDisplay());

		addPaintListener(this);
		addMouseListener(this);
	}

	@Override
	public void paintControl(PaintEvent e) {
		Composite co = (Composite) e.getSource();
		final Rectangle rect = co.getClientArea();

		updateScale(rect);
		updateCenter(rect);

		e.gc.setBackground(new Color(getDisplay(), 255, 255, 255));
		e.gc.fillRectangle(rect);
		drawAxes(e.gc, rect);
		renderCurves(e.gc);
	}

	private void renderCurves(GC gc) {
		if (curves.size() <= 1)
			return;

		ICurve xCurve = curves.get(0);
		ICurve yCurve = curves.get(1);

		float points[] = new float[xCurve.getPoints().size() * 2];
		for (int i = 0; i < xCurve.getPoints().size(); i++) {
			points[i * 2] = xCurve.getPoints().get(i).floatValue();
			points[i * 2 + 1] = yCurve.getPoints().get(i).floatValue();
		}

		graphToPixel(points);

		for (int i = 1; i < xCurve.getPoints().size(); i++) {
			int lastX = (i - 1) * 2;
			int lastY = (i - 1) * 2 + 1;
			gc.drawLine((int) points[lastX], (int) points[lastY],
					(int) points[i * 2], (int) points[i * 2 + 1]);
		}
	}

	private void drawAxes(GC gc, Rectangle rect) {
		// System.err.println("drawing: " + rect);
		float zero[] = { 0.0f, 0.0f };
		graphToPixel(zero);
		//System.err.println("view: " +viewX + ":" +viewY + " zero: " + zero[0] + ":" + zero[1]);

		LineAttributes thick = new LineAttributes(2.0f);
		LineAttributes thin = new LineAttributes(1.0f);
		gc.setLineAttributes(thick);

		gc.setForeground(new Color(this.getDisplay(), 128, 128, 128));

		gc.setLineAttributes(thin);
		
		drawGrid(gc, rect, 0.2f);

		gc.setLineAttributes(thick);
		
		drawGrid(gc,rect, 1.0f);

		gc.setForeground(new Color(getDisplay(), 0, 0, 0));

		gc.drawLine((int) zero[0], rect.y + rect.height, (int) zero[0], rect.y);
		gc.drawLine(rect.x, (int) zero[1], rect.x + rect.width, (int) zero[1]);

		drawAxisDeco(gc, rect);
	}

	private void drawAxisDeco(GC gc, Rectangle rect) {
		final float viewPort[] = { 0, 0, rect.width, rect.height };
		pixelsToGraph(viewPort);
		
		final float yArrow[] = {0.0f, viewPort[1], -stepWidth / 10.f, viewPort[1] -stepWidth / 5.f , stepWidth / 10.f , viewPort[1]-stepWidth / 5.f};
		graphToPixel(yArrow);
		
		final int yArrowIntPoints[] = new int[6];
		for (int i = 0; i<6;i++) {
			yArrowIntPoints[i] = (int)yArrow[i];
		}

		final float xArrow[] = {viewPort[2], 0.f,viewPort[2] -stepWidth / 5.f, -stepWidth / 10.f ,viewPort[2]-stepWidth/5.f,stepWidth / 10.f};
		graphToPixel(xArrow);
		
		final int xArrowIntPoints[] = new int[6];
		for (int i = 0; i<6;i++) {
			xArrowIntPoints[i] = (int)xArrow[i];
		}
		
		gc.drawText("x", xArrowIntPoints[2], xArrowIntPoints[3]+2);
		gc.drawText("y", yArrowIntPoints[4] +2, yArrowIntPoints[5]);
		gc.setBackground(new Color(getDisplay(),0,0,0));
		gc.fillPolygon(yArrowIntPoints);
		gc.fillPolygon(xArrowIntPoints);
		gc.setBackground(new Color(getDisplay(),255,255,255));
	}
	
	/**
	 * @param gc
	 * @param rect
	 * @return
	 */
	private void drawGrid(GC gc, Rectangle rect,float incrementFactor) {
		float width = updateStepwidth(rect);
		float step = 0.0f;
		float increment = stepWidth * incrementFactor;
		float startx = (float) Math.floor(viewX / increment)
				* increment;
		float starty = (float) Math.floor(viewY / increment)
				* increment;

		while (step < width) {
			float point[] = 
				{ startx + step, starty, startx - step, starty,
				  startx, starty + step, startx, starty - step};
			
			graphToPixel(point);
			//System.err.println("startx: " + startx + " step: " + step + " point: " + point[0]);
			gc.drawLine((int) point[0], 0, (int) point[0], rect.y + rect.height);
			gc.drawLine((int) point[2], 0, (int) point[2], rect.y + rect.height);
			
			gc.drawLine(0, (int) point[5], rect.x + rect.width, (int) point[5]);
			gc.drawLine(0, (int) point[7], rect.x + rect.width, (int) point[7]);
			step += increment;
		}
	}

	/**
	 * @param rect
	 * @return the maximum size of the visible graph
	 */
	private float updateStepwidth(Rectangle rect) {
		float width[] = { 0, 0, rect.width, rect.height };
		pixelsToGraph(width);
		float totalwidth = 0.0f;
		if (width[2] - width[0] > width[3] - width[1])
			totalwidth = width[2] - width[0];
		else
			totalwidth = width[3] - width[1];
		stepWidth = calculateStepWidth(totalwidth);
		//		
		// System.err.println("totalWidth: " + totalwidth + " stepWidth: " +
		// stepWidth);
		return totalwidth;
	}

	private float calculateStepWidth(float visible) {
		float log = (float) Math.log10(visible);
//		System.err.println("log: " + log + " floor: " + Math.floor(log)
//				+ " pow: " + Math.pow(10.0, Math.floor(log)));
		return (float) Math.pow(10.0, Math.floor(log));
	}

	/**
	 * @param rect
	 */
	private void updateView() {
		view.identity();
		view.translate(-viewX, -viewY);
		iView.identity();
		iView.multiply(view);
		iView.invert();
		// System.err.println("view: " + view);
		// System.err.println("view^-1: " + iView);
	}

	/**
	 * @param rect
	 */
	private void updateScale(Rectangle rect) {
		scale.identity();
		int min = Math.min(rect.width, rect.height);
		scale.scale(min / scaleF,- min / scaleF);
		iScale.identity();
		iScale.multiply(scale);
		iScale.invert();
		// System.err.println("scale: " + scale);
		// System.err.println("scale^-1: " + iScale);
	}

	private void updateCenter(Rectangle rect) {
		center.identity();
		center.translate(rect.width / 2.0f, rect.height / 2.0f);
		iCenter.identity();
		iCenter.multiply(center);
		iCenter.invert();
		// System.err.println("center: " + center);
		// System.err.println("center^-1: " + iCenter);
	}

	private void graphToPixel(float[] points) {
		view.transform(points);
		scale.transform(points);
		center.transform(points);
	}

	private void pixelsToGraph(float[] points) {
		iCenter.transform(points);
		iScale.transform(points);
		iView.transform(points);
	}

	/**
	 * @return the curves
	 */
	public List<ICurve> getCurves() {
		return curves;
	}

	/**
	 * @param curves
	 *            the curves to set
	 */
	public void setCurves(List<ICurve> curves) {
		this.curves = curves;
		System.err.println("got " + curves.size() + " curves!");
		redraw();
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseDown(MouseEvent e) {
		if (e.button == 1) {
			this.setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
			// System.err.println("down! -> " + e.x + e.y);
			mousePos[0] = e.x;
			mousePos[1] = e.y;

			addMouseMoveListener(this);

			startViewX = viewX;
			startViewY = viewY;
		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
		if (e.button == 1) {
			this.setCursor(null);
			removeMouseMoveListener(this);
		}
	}

	@Override
	public void mouseMove(MouseEvent e) {

		float vector[] = new float[] { e.x - mousePos[0], e.y - mousePos[1] };
		// System.err.println("pixelpos: " + e.x + ":" + e.y);
		// System.err.println("vector: " + vector[0] + ":" + vector[1]);
		iScale.transform(vector);
		// System.err.println("translated  vector: " + vector[0] + ":" +
		// vector[1]);

		// System.err.println("old view: " + startViewX + ":" + startViewY);
		viewX = startViewX - vector[0];
		viewY = startViewY - vector[1];
		// System.err.println("new view: " + viewX + ":" + viewY);
		updateView();
		redraw();
	}
}
