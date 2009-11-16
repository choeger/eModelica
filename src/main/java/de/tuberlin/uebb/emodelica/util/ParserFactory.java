/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import java.util.ArrayDeque;
import java.util.ArrayList;

import de.tuberlin.uebb.modelica.im.impl.generated.moparser.mosilaStateFactory;
import de.tuberlin.uebb.page.parser.Automaton;
import de.tuberlin.uebb.page.parser.symbols.State;

/**
 * @author choeger This class returns a parser Singleton
 */
public class ParserFactory {

	static class ParserConstructor implements Runnable {

		private ArrayDeque<Automaton> parserQue;
		private mosilaStateFactory stateFactory;
		private ArrayList<State> generatedStates;

		public ParserConstructor(ArrayDeque<Automaton> parser) {
			this.parserQue = parser;
			stateFactory = new mosilaStateFactory();
		}

		@Override
		public void run() {
			generatedStates = stateFactory.generateStates();
			while (true) {
				Automaton parser = new Automaton(false);
				parser.setStateList(generatedStates);
				synchronized (parserQue) {
						parserQue.add(parser);
						parserQue.notifyAll();
						if (parserQue.size() >= 2)
							try {
								parserQue.wait();
							} catch (InterruptedException e) {}
				}
			}
		}
	}

	private static ArrayDeque<Automaton> parserQue = new ArrayDeque<Automaton>();
	private static Thread constructor = new Thread(new ParserConstructor(
			parserQue));

	/**
	 * 
	 * @return Parser
	 */
	public static Automaton getAutomatonInstance() {
		if (!constructor.isAlive()) {
			constructor.start();
		}
		
		synchronized (parserQue) {
			while (parserQue.size() == 0) {
				try {
					parserQue.notifyAll();
					parserQue.wait();
				} catch (InterruptedException e) {
				}
			}
			return parserQue.pop();
		}

	}
}
