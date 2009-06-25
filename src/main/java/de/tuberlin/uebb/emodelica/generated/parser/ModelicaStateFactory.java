/*This class was automagically created by the StateFactoryCreator.
DO NOT EDIT it!
changes are likely to get lost
*/
package de.tuberlin.uebb.emodelica.generated.parser;
import de.tuberlin.uebb.page.parser.AbstractStateFactory;
import de.tuberlin.uebb.page.parser.symbols.State;
import java.net.URL;
import java.util.ArrayList;
import de.tuberlin.uebb.page.parser.util.StateFactoryReader;
import java.io.IOException;

public class ModelicaStateFactory extends AbstractStateFactory {
	public ArrayList<State> generateStates() {
		URL stateResource = ModelicaStateFactory.class.getResource("/Modelica.data");
		try {
			StateFactoryReader reader = new StateFactoryReader(stateResource.openStream());
			return reader.readStates();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
