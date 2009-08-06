/**
 * 
 */
package de.tuberlin.uebb.emodelica.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.tuberlin.uebb.emodelica.model.experiments.ICurve;
import de.tuberlin.uebb.emodelica.model.experiments.IExperiment;
import de.tuberlin.uebb.emodelica.model.project.IMosilabEnvironment;

/**
 * @author choeger
 * 
 */
public class TabularExperimentView extends AbstractExperimentViewPart {

	private Table table;

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		GridData data = new GridData(GridData.FILL_BOTH);
		container.setLayout(layout);
		container.setLayoutData(data);

		table = new Table(container, SWT.BORDER);
		table.setLayoutData(data);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		
		updateTableItems();
	}

	private void updateTableItems() {
		table.removeAll();

		for (TableColumn column : table.getColumns())
			column.dispose();

		if (experiment != null)
			for (int i = 0; i < experiment.getCurves().size(); i++) {
				TableColumn column = new TableColumn(table, SWT.NONE);
				final ICurve curve = experiment.getCurves().get(i);
				column.setText(curve.getVariableName());
				for (int j = 0; j < curve.getPoints().size(); j++) {
					Double point = curve.getPoints().get(j);
					TableItem item;
					if (table.getItemCount() <= j)
						item = new TableItem(table, SWT.NONE);
					else
						item = table.getItem(j);
					item.setText(i, point.toString());
				}
			}

		for (TableColumn column : table.getColumns())
			column.pack();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.views.AbstractExperimentViewPart#setExperiment(de.tuberlin.uebb.emodelica.model.experiments.IExperiment)
	 */
	@Override
	public void setExperiment(IExperiment experiment) {
		super.setExperiment(experiment);
		if (table != null) {
			updateTableItems();
			table.redraw();
		}
	}
}
