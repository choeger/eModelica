package de.tuberlin.uebb.emodelica.actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.actions.ActionGroup;

import de.tuberlin.uebb.emodelica.editors.ModelicaEditor;
import de.tuberlin.uebb.emodelica.model.Model;
import de.tuberlin.uebb.emodelica.refactoring.IRefactoringDescriptor;
import de.tuberlin.uebb.emodelica.refactoring.ModelicaRefactoring;
import de.tuberlin.uebb.modelica.im.ILocation;

public class ModelicaRefactoringActionGroup extends ActionGroup {

	private static class NoActionAvailable extends Action {
		public NoActionAvailable() {
			setEnabled(true);
			setText("No refactoring available");
		}
	}

	private Action fNoActionAvailable = new NoActionAvailable();

	public static final String MENU_ID = "de.tuberlin.uebb.emodelica.refactoring.menu";
	private List<RefactoringAction> actions = new ArrayList<RefactoringAction>();
	private ModelicaEditor fEditor;
	private String fGroup;

	@SuppressWarnings("unchecked")
	public ModelicaRefactoringActionGroup(ModelicaEditor editor, String group) {
		this.fEditor = editor;
		this.fGroup = group;

		IExtensionRegistry reg = Platform.getExtensionRegistry();

		IExtensionPoint point = reg
				.getExtensionPoint("de.tuberlin.uebb.emodelica.refactoring");

		for (IExtension extension : point.getExtensions()) {

			for (IConfigurationElement element : extension
					.getConfigurationElements())
				if (element.getName().equals("RefactoringGroup")) {
					try {
						IRefactoringDescriptor descriptor = (IRefactoringDescriptor) Class
								.forName(element.getAttribute("Descriptor"))
								.newInstance();
						Class<RefactoringWizard> wizardClass = (Class<RefactoringWizard>) Class.forName(element.getAttribute("Wizard"));
						final Class<?> refClass = Class
						.forName(element.getAttribute("Refactoring"));
						ModelicaRefactoring ref = (ModelicaRefactoring) refClass
						.newInstance();
												
						actions.add(new RefactoringAction(descriptor, ref, wizardClass, fEditor));
					} catch (InvalidRegistryObjectException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		MenuManager refactorSubmenu = new MenuManager("Refactor", MENU_ID);

		refactorSubmenu.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				refactorMenuShown(manager);
			}
		});

		refactorSubmenu.add(fNoActionAvailable);
		menu.appendToGroup(fGroup, refactorSubmenu);
	}

	private void refactorMenuShown(IMenuManager manager) {
		Menu menu = ((MenuManager) manager).getMenu();
		menu.addMenuListener(new MenuAdapter() {

			@Override
			public void menuHidden(org.eclipse.swt.events.MenuEvent e) {
				super.menuHidden(e);
				refactorMenuHidden();
			}
			
		});
		ITextSelection textSelection = (ITextSelection) fEditor
				.getSelectionProvider().getSelection();
		
		Model model = fEditor.getModelManager().getModel();
		final ILocation surroundingLocation = model.getSurroundingLocation(textSelection.getOffset(), textSelection.getLength());

		for (RefactoringAction action : actions) {			
			action.update(surroundingLocation, model);
		}
		manager.removeAll();
		if (fillRefactorMenu(manager) == 0)
			manager.add(fNoActionAvailable);
	}

	private int fillRefactorMenu(IMenuManager manager) {
		int count = 0;
		for (RefactoringAction action : actions)
			if (action.isEnabled()) {
				manager.add(action);
				count++;
			}
		return count;
	}

	private void refactorMenuHidden() {
		ITextSelection textSelection = (ITextSelection) fEditor
				.getSelectionProvider().getSelection();
		Model model = fEditor.getModelManager().getModel();
		final ILocation surroundingLocation = model.getSurroundingLocation(textSelection.getOffset(), textSelection.getLength());
		
		for (RefactoringAction action : actions)
			action.update(surroundingLocation, model);
	}
}
