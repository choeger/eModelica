package de.tuberlin.uebb.emodelica.editors;

import java.util.Stack;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.link.ILinkedModeListener;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.link.LinkedModeUI;
import org.eclipse.jface.text.link.LinkedPosition;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.jface.text.link.LinkedModeUI.ExitFlags;
import org.eclipse.jface.text.link.LinkedModeUI.IExitPolicy;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.texteditor.link.EditorLinkedModeUI;

import de.tuberlin.uebb.emodelica.Constants;

/**
 * This class handles "smart" inputs it is based upon
 * <code>BracketInserter</code> in JDT's <code>CompilationUnitEditor</code>
 * 
 * @author choeger
 * 
 */
public class ModelicaKeyLister implements VerifyKeyListener,
		ILinkedModeListener {

	private class ExitPolicy implements IExitPolicy {

		final char fExitCharacter;
		final char fEscapeCharacter;
		final Stack fStack;
		final int fSize;

		public ExitPolicy(char exitCharacter, char escapeCharacter, Stack stack) {
			fExitCharacter = exitCharacter;
			fEscapeCharacter = escapeCharacter;
			fStack = stack;
			fSize = fStack.size();
		}

		/*
		 * @see
		 * org.eclipse.jdt.internal.ui.text.link.LinkedPositionUI.ExitPolicy
		 * #doExit(org.eclipse.jdt.internal.ui.text.link.LinkedPositionManager,
		 * org.eclipse.swt.events.VerifyEvent, int, int)
		 */
		public ExitFlags doExit(LinkedModeModel model, VerifyEvent event,
				int offset, int length) {

			if (fSize == fStack.size() && !isMasked(offset)) {
				if (event.character == fExitCharacter) {
					BracketLevel level = (BracketLevel) fStack.peek();
					if (level.fFirstPosition.offset > offset
							|| level.fSecondPosition.offset < offset)
						return null;
					if (level.fSecondPosition.offset == offset && length == 0)
						// don't enter the character if if its the closing peer
						return new ExitFlags(ILinkedModeListener.UPDATE_CARET,
								false);
				}
				// when entering an anonymous class between the parenthesis', we
				// don't want
				// to jump after the closing parenthesis when return is pressed
				if (event.character == SWT.CR && offset > 0) {
					IDocument document = fSourceViewer.getDocument();
					try {
						if (document.getChar(offset - 1) == '{')
							return new ExitFlags(ILinkedModeListener.EXIT_ALL,
									true);
					} catch (BadLocationException e) {
					}
				}
			}
			return null;
		}

		private boolean isMasked(int offset) {
			IDocument document = fSourceViewer.getDocument();
			try {
				return fEscapeCharacter == document.getChar(offset - 1);
			} catch (BadLocationException e) {
			}
			return false;
		}
	}

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
		final IDocument document = sourceViewer.getDocument();

		final Point selection = sourceViewer.getSelectedRange();
		final int offset = selection.x;
		final int length = selection.y;
		try {

			ITypedRegion partition = TextUtilities.getPartition(document,
					Constants.MODELICA_DOCUMENT_PARTITIONING, offset, true);

			if (!IDocument.DEFAULT_CONTENT_TYPE.equals(partition.getType()))
				return;

			final char character = event.character;
			final char closingCharacter = getPeerCharacter(character);
			final StringBuffer buffer = new StringBuffer();
			buffer.append(character);
			buffer.append(closingCharacter);

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
			level.fUI.setExitPolicy(new ExitPolicy(closingCharacter,
					getEscapeCharacter(closingCharacter), fBracketLevelStack));
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

	private char getEscapeCharacter(char character) {
		switch (character) {
		case '"':
		case '\'':
			return '\\';
		default:
			return 0;
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
