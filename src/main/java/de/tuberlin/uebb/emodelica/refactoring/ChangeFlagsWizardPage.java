/**
 * 
 */
package de.tuberlin.uebb.emodelica.refactoring;

import java.util.Set;

import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import de.tuberlin.uebb.modelica.im.StringConstants;
import de.tuberlin.uebb.modelica.im.nodes.ENodeFlags;

/**
 * @author choeger
 *
 */
public class ChangeFlagsWizardPage extends UserInputWizardPage implements SelectionListener {

	private static final String[] INOUT_ITEMS = new String[] {"input", "output", "none"};
	private static final String[] FLOW_ITEMS = new String[] {"flow", "stream","sum", "event", "none"};
	private static final String[] KIND_ITEMS = new String[] {"discrete", "parameter", "constant", "dynamic", "none"};
	private ChangeFlagsRefactoring fRefactoring;
	private Button visProtected;
	private Combo inOrOut;
	private Combo flowOrStream;
	private Combo kind;
	
	
	public ChangeFlagsWizardPage(String pageName,
			ChangeFlagsRefactoring fRefactoring) {
		super(pageName);
		this.fRefactoring = fRefactoring;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		// Type_Prefix = [FlowOrStream] [Kind] [InputOrOutput];
		// FlowOrStream = "flow" | "stream" | "sum" | "event";
		// Kind = "discrete" | "parameter" | "constant" | "dynamic";
		// InputOrOutput = "input" | "output";
		
		addVisibilityGroup(container);
		
		addFlowOrStreamGroup(container);
		
		addKindGroup(container);
		
		addInputOutputGroup(container);
		
		fillFromFlags();
	}

	public void writeBackFlags() {
		final Set<ENodeFlags> flags = fRefactoring.getFlags();
		flags.clear();
		if (visProtected.getSelection())
			flags.add(ENodeFlags.PROTECTED);
		
		ENodeFlags flag = StringConstants.stringToNodeFlag.get(KIND_ITEMS[kind.getSelectionIndex()]);
		if (flag != null)
			flags.add(flag);
		
		flag = StringConstants.stringToNodeFlag.get(FLOW_ITEMS[flowOrStream.getSelectionIndex()]);
		if (flag != null)
			flags.add(flag);
		
		flag = StringConstants.stringToNodeFlag.get(INOUT_ITEMS[inOrOut.getSelectionIndex()]);
		if (flag != null)
			flags.add(flag);		
	}

	private void fillFromFlags() {
		final Set<ENodeFlags> flags = fRefactoring.getFlags();
		visProtected.setSelection(flags.contains(ENodeFlags.PROTECTED));
		
		flowOrStream.select(4);
		if (flags.contains(ENodeFlags.FLOW))
			flowOrStream.select(0);
		else if (flags.contains(ENodeFlags.STREAM))
			flowOrStream.select(1);
		else if (flags.contains(ENodeFlags.SUM))
			flowOrStream.select(2);
		else if (flags.contains(ENodeFlags.EVENT))
			flowOrStream.select(3);
		
		inOrOut.select(2);
		if (flags.contains(ENodeFlags.INPUT))
			inOrOut.select(0);
		else if (flags.contains(ENodeFlags.OUTPUT))
			inOrOut.select(1);
		
		kind.select(4);
		if (flags.contains(ENodeFlags.DISCRETE))
			kind.select(0);
		else if (flags.contains(ENodeFlags.PARAMETER))
			kind.select(1);
		else if (flags.contains(ENodeFlags.CONSTANT))
			kind.select(2);
		else if (flags.contains(ENodeFlags.DYNAMIC))
			kind.select(3);
	}


	private void addKindGroup(Composite container) {
		initializeDialogUnits(container);
		
		Group kindGroup = new Group(container, SWT.NONE);
		kindGroup.setText("Kind");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		kindGroup.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		kindGroup.setLayout(gridLayout);
		kind = new Combo(kindGroup, SWT.READ_ONLY);
		kind.setItems(KIND_ITEMS);
		
		kind.addSelectionListener(this);
	}


	private void addVisibilityGroup(Composite container) {
		initializeDialogUnits(container);
		
		Group visibility = new Group(container, SWT.NONE);
		visibility.setText("Visibility");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		visibility.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		visibility.setLayout(gridLayout);
		visProtected = new Button(visibility, SWT.CHECK);
		visProtected.setText("protected");
		
		visProtected.addSelectionListener(this);
	}

	private void addInputOutputGroup(Composite container) {
		initializeDialogUnits(container);
		
		Group inOutGroup = new Group(container, SWT.NONE);
		inOutGroup.setText("Input/Output");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		inOutGroup.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		inOutGroup.setLayout(gridLayout);
		inOrOut = new Combo(inOutGroup, SWT.READ_ONLY);
		inOrOut.setItems(INOUT_ITEMS);
		
		inOrOut.addSelectionListener(this);
	}

	private void addFlowOrStreamGroup(Composite container) {
		initializeDialogUnits(container);
		
		Group flowOrStreamGroup = new Group(container, SWT.NONE);
		flowOrStreamGroup.setText("Flow/Stream");
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		flowOrStreamGroup.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		flowOrStreamGroup.setLayout(gridLayout);
		flowOrStream = new Combo(flowOrStreamGroup, SWT.READ_ONLY);
		flowOrStream.setItems(FLOW_ITEMS);
		
		flowOrStream.addSelectionListener(this);
	}


	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
				
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		writeBackFlags();		
	}
	
}
