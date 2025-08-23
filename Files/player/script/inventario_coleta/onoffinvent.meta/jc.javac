public class onoffinvent extends Component {
  private SUIRect invent, craft, button, defDelet;
  @Hide public boolean onoff = false, onoffCraft = false, onoffDele = false;

  void start() {
    invent = WorldController.findObject("inventory").findChildObject("backgroud").findComponent("suirect");
    craft = WorldController.findObject("CriaItem").findChildObject("backgroudCria").findComponent("suirect");
    button = WorldController.findObject("inventory").findChildObject("craftButton").findComponent("suirect");
    defDelet = WorldController.findObject("defDelet").findComponent("suirect");
  }

  void repeat() {
    macacao();
    boolean active = Input.isKeyDown("invent") || Input.keyboard.isKeyDown("r") ? onoff = !onoff : onoff;
    boolean activeCraft = Input.isKeyDown("craft") || Input.keyboard.isKeyDown("q") ? onoffCraft = !onoffCraft : onoffCraft;
    boolean activeDele = Input.isKeyDown("remove") || Input.keyboard.isKeyDown("shift") ? onoffDele = !onoffDele : onoffDele;
    if (Input.isKeyDown("delAll") || Input.isKeyDown("del")) onoffDele = false;
    button.setInt("BottomMargin", onoff ? -145 : 145);
    invent.setInt("BottomMargin", onoff && !onoffCraft ? -390 : 400);
    craft.setInt("BottomMargin", onoff && onoffCraft ? -390 : 400);
    defDelet.setInt("BottomMargin", onoff && onoffDele ? -390 : 400);
  } 

  private void macacao() {
    GUI.drawImage(new Color(), null, (Screen.getWidth() / 2) - 12, (Screen.getHeight() / 2) - 12, 15, 15);
  }
}
