public class invertore extends Component {
  private List<SpatialObject> Item = new ArrayList<SpatialObject>();
  @Hide public List<item> items = new ArrayList<item>();
  @Hide public SUIImage[] spait = new SUIImage[30];
  @Hide public SUIText[] cont = new SUIText[30];
  @Hide public int[] slotAlmout;
  private SpatialObject button;
  private boolean onoff = false;
  private float time = 0;
  void start() {
    button = WorldController.findObject("colete");
    slotAlmout = new int[cont.length];
    slot();
    for (int i = 0; i < cont.length; i++) {
      items.add(i, null);
      if (cont[i] != null) cont[i].setText("");
      if (spait[i] != null) {
        spait[i].setColor(new Color(0, 0, 0, 0));
        spait[i].setImage(null);
      }
    }
  }

  void repeat() {
    if (time < 1) time += 0.01f;
    laser();
    button.setEnabled(onoff);
  }

  private void slot() {
    for (int i = 0; i < 30; i++) {
      String obj = i < 5 ? "slotH" + (i + 1) : i < 20 ? "slot" + (i - 4) : i < 29 ? "slotCria" + (i - 19) : "output";
      String child = i < 5 ? "spait" + (i + 1) : i < 20 ? "item" + (i - 4) : i < 29 ? "item" + (i - 19) : "outputCria";
      Item.add(i, WorldController.findObject(obj).findChildObject(child));
      cont[i] = Item.get(i).findComponent("suitext");
      spait[i] = Item.get(i).findComponent("suiimage");
    }
  }

  private void laser() {
    SpatialObject camera = WorldController.findObject("see_player");
    String text;
    LaserHit hit = new Laser().trace(camera.globalPosition, camera.forward(), 6f);
    if (hit == null || !"object".equals(hit.getObject().tag)) {
      onoff = false;      
      return;
    }
    
    item objecthit = hit.getObject().findComponent("item");
    text = objecthit.QuatItemGrup + objecthit.name + ", " + objecthit.typeDC + ": " + objecthit.value;
    GUI.drawText(text,(Screen.width()/2)-200,(Screen.height()/2)+160,30,12,8);
    onoff = true;
    if ((!Input.isKeyPressed("coleta") && !Input.keyboard.isKeyDown("e")) || time < 0.45f) return;
    for (int i = 0; i < (items.size() - 7); i++) {
      if (items.get(i) == null || (items.get(i) != null && items.get(i).id == objecthit.id)) {
        int spaceVoid = objecthit.maxgrup - slotAlmout[i];
        if (spaceVoid >= objecthit.QuatItemGrup) {
          addItem(objecthit, i);
          hit.getObject().destroy();
          onoff = false;
          time = 0;
          break;
        }
      }
    }
  }

  public void addItem(item additem, int i) {
    slotAlmout[i] += additem.QuatItemGrup;
    int valuesoma = slotAlmout[i];
    items.set(i, additem);
    if (cont[i] == null) return;
    cont[i].setText(valuesoma > 0 ? String.valueOf(valuesoma) : "1");
    if (i >= spait.length) return;
    spait[i].setImage(additem.getSpait());
    spait[i].setColor(new Color());
  }
}
