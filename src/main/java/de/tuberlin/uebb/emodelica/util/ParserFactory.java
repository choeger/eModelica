/**
 * 
 */
package de.tuberlin.uebb.emodelica.util;

import java.util.ArrayDeque;

import de.tuberlin.uebb.emodelica.generated.parser.ModelicaStateFactory;
import de.tuberlin.uebb.page.parser.Automaton;

/**
 * @author choeger This class returns a parser Singleton
 */
public class ParserFactory {

	static class ParserConstructor implements Runnable {

		private ArrayDeque<Automaton> parserQue;
		private ModelicaStateFactory stateFactory;

		public ParserConstructor(ArrayDeque<Automaton> parser) {
			this.parserQue = parser;
			stateFactory = new ModelicaStateFactory();
		}

		@Override
		public void run() {
			while (true) {
				Automaton parser = new Automaton();
				parser.setStateList(stateFactory.generateStates());
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
