package de.fh;

import java.util.ArrayList;
import java.util.List;

import de.fh.agentI.IAction;
import de.fh.agentI.IAgentActions;
import de.fh.agentI.IAgentState;
import de.fh.connection.ActionEffect;
import de.fh.connection.Percept;
import de.fh.connection.StartInfo;
import de.fh.connection.Vector2;
import de.fh.connection.client.WumpusClientConnector;
import de.fh.connection.wumpus.AgentAction;
import de.fh.connection.wumpus.AgentPercept;
import de.fh.connection.wumpus.AgentStartInfo;

/*
 * DIESE KLASSE VERÄNDERN SIE BITTE NUR AN DEN GEKENNZEICHNETEN STELLEN
 * wenn die Bonusaufgabe bewertet werden soll.
 */
class MyAgent implements IAgentActions, IAgentState {

	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;
	private int direction = EAST;
	private AgentAction nextAction = AgentAction.GO_FORWARD;
	private AgentPercept percept;
	private Vector2 agentcoords = new Vector2(1, 1);
	private List<List<Feld>> map = new ArrayList<List<Feld>>();
	private int actionEffect;
	private AgentStartInfo agentStartInfo;

	public static void main(String[] args) {
		MyAgent ki = new MyAgent();
		// Client connect to the server with given predefined settings
		WumpusClientConnector wumpusClient = new WumpusClientConnector(ki, ki);
		wumpusClient.run();
	}

	/**
	 * Diese Funktion wird einmal aufgerufen und übergibt alle
	 * Startinformationen über die Welt
	 *
	 * @param startInfo
	 *            Startinfo-Objekt mit alle Startinformationen
	 */
	@Override
	public void setStartInfo(StartInfo startInfo) {
		/**
		 * In dem Startinfo Objekt befinden sich alle Startinformationen, auf
		 * die die "interne Welt", die Wissenbasis aufbaut Achtung: Die
		 * Feldgröße ist im Standard unbekannt also -1
		 */
		this.agentStartInfo = (AgentStartInfo) startInfo;

		// Our Code
		for (int i = 0; i < 20; i++) {
			map.add(new ArrayList<Feld>());
			for (int j = 0; j < 20; j++) {
				map.get(i).add(new Feld(new Vector2(j + 1, i + 1)));
			}
		}

		// Startinformationen einmal ausgeben
		System.out.println(agentStartInfo.toString());
	}

	/**
	 * In dieser Methode kann das Wissen über die Welt (der State, der Zustand)
	 * entsprechend der aktuellen Wahrnehmungen anpasst, und die "interne Welt",
	 * die Wissensbasis, des Agenten kontinuierlich ausgebaut werden.
	 *
	 * Wichtig: Diese Methode wird aufgerufen, bevor der Agent handelt, d.h.
	 * bevor die chooseAction()-Methode aufgerufen wird...
	 *
	 * @param percept
	 *            Aktuelle Wahrnehmung
	 * @param actionEffect
	 *            Reaktion des Servers
	 */
	@Override
	public void updateState(Percept percept, int actionEffect) {

		/**
		 * Je nach Sichtbarkeit & Schwierigkeitsgrad (laut Serverkonfiguration)
		 * aktuelle Wahrnehmung des Agenten
		 *
		 * percept enthält die verschiedene Wahrnehmungen, die mit folgenden
		 * Methoden zugreifbar sind:
		 *
		 * boolean isBump() -> Ist eine Wand zu spüren? boolean isBreeze() ->
		 * Ist eine Falle zu spüren? boolean isStench() -> Ist der Wumpus im
		 * Bereich zu riechen? boolean isScream() -> Wurde der Wumpus getroffen?
		 * boolean isGold() -> Ist Gold unter dem Agenten? int[][]
		 * getWumpusStenchRadar() -> In welcher Manhattendistanz ist der Wumpus
		 * zu riechen? boolean isRumble() -> Hat sich der Wumpus bewegt?
		 */
		this.percept = (AgentPercept) percept;

		/**
		 * Aktuelle Reaktion des Server auf die letzte übermittelte Action
		 */
		this.actionEffect = actionEffect;

		for (int i = 0; i < map.size(); i++) // y
		{
			for (int j = 0; j < map.size(); j++) // x
			{
				if (map.get(i).get(j).GetCoords().getX() == agentcoords.getX()) {
					if (map.get(i).get(j).GetCoords().getY() == agentcoords.getY()) {
						map.get(i).get(j).SetVisited();
						break;
					}
				}
			}
		}
		String feld = "";
		for (int i = 0; i < map.size(); i++) // map size -> y da anzahl rows
												// liefert
		{
			for (int j = 0; j < map.size(); j++) // rows size -> x
			{
				if (map.get(i).get(j).IsVisited())
					feld = feld + "0";
				else
					feld = feld + "1";
			}
			feld = feld + "\n";
		}
		System.out.print(feld);
		/*
		 * for(int j=0; j <= map.size(); j++) { for(int i=0; i<=
		 * elements.size(); i++) { if (elements.get(j).getX() != coords.getX())
		 * { if (elements.get(j).getY() != coords.getY()) {
		 * elements.add(coords); } } } }
		 */

		this.actionEffect = actionEffect;

		// TODO [UpdateState]: Erweitern Sie diese updateState-Methode

		// Beispiel:

		// In diesem Array kann man den Wumpus vielleicht wahrnehmen,
		// wenn nah genug
		for (int i = 0; this.percept.getWumpusStenchRadar()[i][0] != 0; i++) {
			System.out.println("Dis: [" + this.percept.getWumpusStenchRadar()[i][0] + "]: "
					+ this.percept.getWumpusStenchRadar()[i][1]);
		}

		// Der Wumpus hat das Feld gewechselt
		if (this.percept.isRumble() == true) {
			System.out.println("<------- Der Wumpus bewegt sich! ------->");
		}

		System.out.println("-----------");

		// Alle möglichen Serverrückmeldungen:
		switch (actionEffect) {
		case ActionEffect.SUCCESS:
			break;
		case ActionEffect.GOLD_FOUND:
			break;
		case ActionEffect.GOLD_NOT_FOUND:
			break;
		case ActionEffect.INVALID_LOCATION:
			break;
		case ActionEffect.WUMPUS_KILLED:
			break;
		case ActionEffect.WUMPUS_NOT_KILLED:
			break;
		default:
			// Startsituation - aller erster Aufruf!
			break;
		}
	}

	/**
	 * Die chooseAction-Methode erweitern Sie so, dass die nächste sinnvolle
	 * Aktion, auf Basis der vorhandenen Zustandsinformationen und gegebenen
	 * Zielen, ausgeführt wird. Die chooseAction-Methode soll den Agenten so
	 * intelligent wie möglich handeln lassen.
	 *
	 * Beispiel: Wenn die letzte Wahrnehmung "percept.isGold() == true"
	 * enthielt, ist "Action.GRAB" eine geeignete Tätigkeit. Wenn Sie wissen,
	 * dass ein Quadrat "unsicher" ist, können Sie wegziehen
	 *
	 * @param
	 * @return Die nächste Pacman-Action die vom Server ausgeführt werden soll
	 */
	@Override
	public IAction chooseAction() {

		// TODO [ChooseAction]: Erweitern Sie diese chooseAction-Methode.

		// Beispiel
		// Agent läuft immer im Kreis... und hebt das Gold auf
		if (percept.isGold()) {
			System.out.println("GOLD BELOW");
			return AgentAction.GRAB;
		}
		switch (actionEffect) {
		case ActionEffect.INVALID_LOCATION:
			// map.get(agentcoords.getY()).get(agentcoords.getX()).SetWall();
			switch (direction) {
			case 0:
				agentcoords.set(agentcoords.getX(), agentcoords.getY() + 1);
				break;
			case 1:
				agentcoords.set(agentcoords.getX() - 1, agentcoords.getY());
				break;
			case 2:
				agentcoords.set(agentcoords.getX(), agentcoords.getY() - 1);
				break;
			case 3:
				agentcoords.set(agentcoords.getX() + 1, agentcoords.getY());
				break;
			}
			nextAction = AgentAction.TURN_RIGHT;
			if (direction < WEST)
				direction++;
			else
				direction = NORTH;
			break;
		default:
			nextAction = AgentAction.GO_FORWARD;
		}
		if (nextAction == AgentAction.GO_FORWARD) {
			switch (direction) {
			case NORTH:
				agentcoords.set(agentcoords.getX(), agentcoords.getY() - 1);
				break;
			case EAST:
				agentcoords.set(agentcoords.getX() + 1, agentcoords.getY());
				break;
			case SOUTH:
				agentcoords.set(agentcoords.getX(), agentcoords.getY() + 1);
				break;
			case WEST:
				agentcoords.set(agentcoords.getX() - 1, agentcoords.getY());
				break;
			}
		}

		return nextAction;
	}
}