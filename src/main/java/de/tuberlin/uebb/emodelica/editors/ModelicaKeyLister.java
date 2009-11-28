package de.tuberlin.uebb.emodelica.editors;

import java.util.Stack;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.link.LinkedModeUI;
import org.eclipse.jface.text.link.LinkedPosition;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.texteditor.link.EditorLinkedModeUI;

/**
 * This class handles "smart" inputs it is based upon
 * <code>BracketInserter</code> in JDT's <code>CompilationUnitEditor</code>
 * 
 * @author choeger
 * 
 */
public class ModelicaKeyLister implements VerifyKeyListener,
		ILinkedModeListener {

	private ModelicaEditor fModelicaEditor;
	private final ISourceViewer fSourceViewer;
	private final String CATEGORY = toString();
	private final IPositionUpdater fUpdater = new DefaultPositionUpdater(
			CATEGORY);
	private final Stack<BracketLevel> fBracketLevelStack = new Stack<BracketLevel>();

	private static class BracketLevel {
		LinkedModeUI fUI;
		Position fFirstPosition;
		Position fSecondPosition;
	}

	public ModelicaKeyLister(ModelicaEditor editor, ISourceViewer sourceViewer) {
		fModelicaEditor = editor;
		fSourceViewer = sourceViewer;
	}

	@Override
	public void verifyKey(VerifyEvent event) {

		// early pruning to slow down normal typing as little as possible
		if (!event.doit || fModelicaEditor.isBlockSelectionModeEnabled()
				&& fModelicaEditor.isMultilineSelection())
			return;
		switch (event.character) {
		case '(':
		case '\"':
			break;
		default:
			return;
		}

		final ISourceViewer sourceViewer = fSourceViewer;
		IDocument document = sourceViewer.getDocument();

		final Point selection = sourceViewer.getSelectedRange();
		final int offset = selection.x;
		final int length = selection.y;

		final char character = event.character;
		final char closingCharacter = getPeerCharacter(character);
		final StringBuffer buffer = new StringBuffer();
		buffer.append(character);
		buffer.append(closingCharacter);
		try {
			document.replace(offset, length, buffer.toString());

			BracketLevel level = new BracketLevel();
			fBracketLevelStack.push(level);

			LinkedPositionGroup group = new LinkedPositionGroup();

			final LinkedPosition position = new LinkedPosition(document,
					offset + 1, 0, LinkedPositionGroup.NO_STOP);
			group.addPosition(position);

			LinkedModeModel model = new LinkedModeModel();
			model.addLinkingListener(this);
			model.addGroup(group);
			model.forceInstall();

			// set up position tracking for our magic peers
			if (fBracketLevelStack.size() == 1) {
				document.addPositionCategory(CATEGORY);
				document.addPositionUpdater(fUpdater);
			}

			level.fFirstPosition = new Position(offset, 1);
			level.fSecondPosition = new Position(offset + 1, 1);
			document.addPosition(CATEGORY, level.fFirstPosition);
			document.addPosition(CATEGORY, level.fSecondPosition);

			level.fUI = new EditorLinkedModeUI(model, sourceViewer);
			level.fUI.setSimpleMode(true);

			level.fUI.setExitPosition(sourceViewer, offset + 2, 0,
					Integer.MAX_VALUE);
			level.fUI.setCyclingMode(LinkedModeUI.CYCLE_NEVER);
			level.fUI.enter();

			event.doit = false;

			sourceViewer.setSelectedRange(offset + 1, 0);

		} catch (BadLocationException e) {
			return;
		} catch (BadPositionCategoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private char getPeerCharacter(char character) {
		switch (character) {
		case '(':
			return ')';

		case '"':
			return character;

		default:
			throw new IllegalArgumentException();

		}
	}

	@Override
	public void left(LinkedModeModel model, int flags) {

		final BracketLevel level = (BracketLevel) fBracketLevelStack.pop();

		if (flags != ILinkedModeListener.EXTERNAL_MODIFICATION)
			return;

		// remove brackets
		final IDocument document = fSourceViewer.getDocument();

		if (document instanceof IDocumentExtension) {
			IDocumentExtension extension = (IDocumentExtension) document;
			extension.registerPostNotificationReplace(null,
					new IDocumentExtension.IReplace() {

						public void perform(IDocument d, IDocumentListener owner) {
							if ((level.fFirstPosition.isDeleted || level.fFirstPosition.length == 0)
									&& !level.fSecondPosition.isDeleted
									&& level.fSecondPosition.offset == level.fFirstPosition.offset) {
								try {
									document.replace(
											level.fSecondPosition.offset,
											level.fSecondPosition.length, ""); //$NON-NLS-1$
								} catch (BadLocationException e) {

								}
							}

							if (fBracketLevelStack.size() == 0) {
								document.removePositionUpdater(fUpdater);
								try {
									document.removePositionCategory(CATEGORY);
								} catch (BadPositionCategoryException e) {

								}
							}
						}
					});
		}

	}

	@Override
	public void resume(LinkedModeModel model, int flags) {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend(LinkedModeModel model) {
		// TODO Auto-generated method stub

	}
}
