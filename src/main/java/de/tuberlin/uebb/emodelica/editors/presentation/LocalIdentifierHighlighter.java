/**
 * 
 */
package de.tuberlin.uebb.emodelica.editors.presentation;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;

import de.tuberlin.uebb.modelica.im.IComponentReference;
import de.tuberlin.uebb.modelica.im.IIdentifier;
import de.tuberlin.uebb.modelica.im.IComponentReference.IComponentPart;
import de.tuberlin.uebb.modelica.im.equations.IBlockEquation;
import de.tuberlin.uebb.modelica.im.equations.IConditionalEquation;
import de.tuberlin.uebb.modelica.im.equations.IConnectEquation;
import de.tuberlin.uebb.modelica.im.equations.IDisconnectEquation;
import de.tuberlin.uebb.modelica.im.equations.IEquation;
import de.tuberlin.uebb.modelica.im.equations.IForEquation;
import de.tuberlin.uebb.modelica.im.equations.ISimpleEquation;
import de.tuberlin.uebb.modelica.im.equations.IConditionalEquation.IEquationWithCondition;
import de.tuberlin.uebb.modelica.im.expressions.IComponentExpression;
import de.tuberlin.uebb.modelica.im.expressions.IExpression;
import de.tuberlin.uebb.modelica.im.expressions.IFunctionCallExpression;
import de.tuberlin.uebb.modelica.im.expressions.IOperatorExpression;
import de.tuberlin.uebb.modelica.im.nodes.IClassNode;
import de.tuberlin.uebb.modelica.im.nodes.INode;
import de.tuberlin.uebb.modelica.im.nodes.IStoredDefinitionNode;
import de.tuberlin.uebb.modelica.im.nodes.IVarDefNode;
import de.tuberlin.uebb.modelica.im.statements.ICallStatement;
import de.tuberlin.uebb.modelica.im.statements.IStatement;
import de.tuberlin.uebb.modelica.im.statements.IWhileStatement;

/**
 * @author choeger
 *
 */
public class LocalIdentifierHighlighter implements ISemanticHighlighter {
	
	//TODO: make colors customizable
	private static final TextAttribute italicAttribute = new TextAttribute(null, null, SWT.ITALIC);
	private static final TextAttribute identAttribute = new TextAttribute(ModelicaColors.identifierColor);
	private Set<HighlightingPosition> positions = new HashSet<HighlightingPosition>();
	private IClassNode currentNode;
	
	public LocalIdentifierHighlighter() {
	}
	
	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.editors.presentation.ISemanticHighlighter#getHighlightingPositions()
	 */
	@Override
	public Collection<HighlightingPosition> getHighlightingPositions() {
		return positions;
		
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.editors.presentation.ISemanticHighlighter#handleNode(de.tuberlin.uebb.modelica.im.nodes.INode)
	 */
	@Override
	public boolean handleNode(INode node) {
		if (node instanceof IStoredDefinitionNode)
			return true;
		
		if (node instanceof IClassNode) {
			currentNode = (IClassNode) node;
			handleStatements(((IClassNode) node).getAlgorithm());
			
			handleEquations(((IClassNode) node).getEquations());
			
			return true;
		} else if (node instanceof IVarDefNode) {
			IIdentifier last = ((IVarDefNode)node).getIdentifier();
			final int length = last.getEndOffset() - last.getStartOffset();
			if (length > 0)
				positions.add(new HighlightingPosition(last.getStartOffset(), length, identAttribute));
		}
		return false;
	}

	private void handleStatements(List<IStatement> algorithm) {
		for (IStatement statement : algorithm) {
			
			if (statement instanceof IWhileStatement)
				handleExpression(((IWhileStatement) statement).getCondition());
			
			if (statement instanceof ICallStatement) {
				//Highlight the expressions
				for (IExpression expression : ((ICallStatement) statement).getParameters().values())
					handleExpression(expression);
				
				createHighlightingFromComponentRef(((ICallStatement) statement).getComponentReference(), italicAttribute);
			}
		}
	}

	private void handleEquations(List<IEquation> equations) {
		for (IEquation equation : equations) {
			handleEquation(equation);	
		}
	}

	private void handleEquation(final IEquation equation) {
		if (equation instanceof ISimpleEquation) {
			handleExpression(((ISimpleEquation) equation).getSimple());
			handleExpression(((ISimpleEquation) equation).getRightHand());
		} else if (equation instanceof IConditionalEquation) {
				for (IEquationWithCondition eqCond : ((IConditionalEquation) equation).getConditionalEquations()) {
					handleEquation(eqCond.getEquation());
					handleExpression(eqCond.getCondition());
				}
		} else if (equation instanceof IBlockEquation) {
			handleEquations(((IBlockEquation) equation).getEquations());
		} else if (equation instanceof IForEquation) {
			handleEquations(((IForEquation) equation).getEquations());
		} else if (equation instanceof IConnectEquation) {
			createHighlightingFromComponentRef(((IConnectEquation) equation).getComponentReferenceA(), identAttribute);
			createHighlightingFromComponentRef(((IConnectEquation) equation).getComponentReferenceB(), identAttribute);
		} else if (equation instanceof IDisconnectEquation) {
			createHighlightingFromComponentRef(((IDisconnectEquation) equation).getComponentReferenceA(), identAttribute);
			createHighlightingFromComponentRef(((IDisconnectEquation) equation).getComponentReferenceB(), identAttribute);
		}
	}

	private void handleExpressions(Collection<IExpression> expressions) {
		for (IExpression expression : expressions)
			handleExpression(expression);
	}
	
	private void handleExpression(final IExpression expression) {
		
		if (expression instanceof IComponentExpression) {
			createHighlightingFromComponentRef(((IComponentExpression) expression).getComponentReference(), identAttribute);
		} else 	if (expression instanceof IFunctionCallExpression) {
			createHighlightingFromComponentRef(((IFunctionCallExpression) expression).getComponentReference(), italicAttribute);
			handleExpressions(((IFunctionCallExpression) expression).getParameters().values());
		} else if (expression instanceof IOperatorExpression) {
			IOperatorExpression opEx = (IOperatorExpression) expression;
			handleExpression(opEx.getLeftSide());
			handleExpression(opEx.getRightSide());
		}
	}

	private void createHighlightingFromComponentRef(IComponentReference ref, TextAttribute attr) {
		final List<IComponentPart> componentParts = ref.getComponentParts();
		final int index = componentParts.size() - 1;
		IComponentPart last = componentParts.get(index);
		final int length = last.getEndOffset() - last.getStartOffset();
		positions.add(new HighlightingPosition(last.getStartOffset(), length, attr));
	}

	/* (non-Javadoc)
	 * @see de.tuberlin.uebb.emodelica.editors.presentation.ISemanticHighlighter#nodeHandled(de.tuberlin.uebb.modelica.im.nodes.INode)
	 */
	@Override
	public void nodeHandled(INode node) {
		
	}

}
