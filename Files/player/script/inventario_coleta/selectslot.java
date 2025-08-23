public class selectslot extends Component {
  private List<SpatialObject> objslot = new ArrayList<SpatialObject>();
  private List<Key> keys = new ArrayList<Key>();
  private SUIImage[] slot = new SUIImage[30];
  private SUIText name;
  private SpatialObject paineldados, player;
  private int select = -1, trocaslot = -1;
  private invertore invent;
  private float clicktime = 0;
  private int contclick = 0;
  private SpatialObject Object;
  private onoffinvent OpenExit;
  private Color White, Black, Transparent, BlueWhite;
  public ObjectFile drops;
  private removeItem removedor;
  
  void start() {
    White = new Color(255, 255, 255);
    Black = new Color(0, 0, 0);
    Transparent = new Color(0, 0, 0, 0);
    BlueWhite = new Color(0, 255, 255);
    
    removedor = new removeItem();
    player = WorldController.findObject("player");
    Object = WorldController.findObject("object");
    paineldados = WorldController.findObject("information");
    name = paineldados.findComponent("suitext");
    invent = myObject.findComponent("invertore");
    OpenExit = player.findComponent("onoffinvent");
    addslot();
    name.setText("");
    paineldados.setEnabled(false);
  }

  void repeat() {
    if (clicktime < 5) clicktime += 0.01f;
    select();
    upslot();
    
    int valueDele = removedor.valueDele();
    if (Input.isKeyDown("delAll") && select != -1) RemoveSlot(select, invent.slotAlmout[select]);
    if (Input.isKeyDown("del") && select != -1) RemoveSlot(select,valueDele);
    if (Input.isKeyDown("invent") || Input.keyboard.isKeyDown("r")) {
      if (select > 4) select = -1;
      paineldados.setEnabled(false);
    }
    if (!OpenExit.onoff && trocaslot != -1) {
      paineldados.setEnabled(false);
      trocaslot = -1;
      select = -1;
    }
  }

  private void addslot() {
    for (int i = 0; i < slot.length; i++) {
      String objpai = i < 5 ? "hotbar" : i < 20 ? "backgroud" : "backgroudCria";
      String objcompot = i < 5 ? "slotH" + (i + 1) : i < 20 ? "slot" + (i - 4) : i < 29 ? "slotCria" + (i - 19) : "output";
      objslot.add(WorldController.findObject(objpai).findChildObject(objcompot));
    }
    for (int i = 0; i < slot.length; i++) {
      objslot.get(i).addComponent(new SUIKeyEventListener());
      SUIKeyEventListener key = objslot.get(i).findComponent("SUIKeyEventListener");
      key.setKeyname(i < 5 ? "slotH" + (i + 1) : i < 29 ? "item" + (i - 4) : "output");
      if (slot[i] != null && keys.get(i) != null) continue;
      slot[i] = objslot.get(i).findComponent("suiimage");
      keys.add(Input.getKey(i < 5 ? "slotH" + (i + 1) : i < 29 ? "item" + (i - 4) : "output"));
    }
  }

  private void select() {
    for (int i = 0; i < slot.length; i++) {
      if (keys.get(i).isDown()) {
        UseNoUse(false, select);
        if (select == i) {
          select = -1;
          offonObj("", false, i);
          modvalue(-1);
        } else {
          if (invent.items.get(i) != null && invent.items.get(i).name != null) {
            item dodasitem = invent.items.get(i);
            String dados = "\n  name: " + dodasitem.name + "\n  " + dodasitem.typeDC + ": " + dodasitem.value;
            offonObj(dados, true, i);
          } else {
            offonObj("", false, i);
          }
          select = i;
        }
        break;
      }
    }
  }

  private void offonObj(String value, boolean onoff, int i) {
    name.setText(value);
    if (OpenExit.onoff) paineldados.setEnabled(onoff);
    boolean hotbar = i >= 0 && i < 5;
    Object.setEnabled(onoff && hotbar);
    UseNoUse(onoff, i);
    ModelRenderer texture = Object.findComponent("modelrenderer");
    if (texture == null || invent.items.get(i) == null) return;
    texture.setModelFile(invent.items.get(i).vertex);
  }

  private void UseNoUse(boolean yesNo, int is) {
    if (is >= 0 && invent.items.get(is) != null && invent.items.get(is).logica != null) {
      item dodasitem = invent.items.get(is);
      if (!Input.isKeyDown("output") && yesNo) dodasitem.Usando();
      else dodasitem.NoUsando();
    }
  }

  private void upslot() {
    for (int i = 0; i < slot.length; i++) {
      if (keys.get(i).isDown() && OpenExit.onoff) {
        if (contclick == i && clicktime < 0.1f) {
          modvalue(trocaslot == i ? -1 : i);
        } else {
          contclick = i;
          clicktime = 0;
        }

        if (slot[i] == null) continue;
        if (trocaslot != -1 && trocaslot != i && trocaslot <= invent.items.size()) {
          item watslot = invent.items.get(i);
          item seleslot = invent.items.get(trocaslot);
          if (watslot != null && seleslot != null && watslot.name != null && watslot.name.equals(seleslot.name)) {
            int limita = watslot.maxgrup;
            int space = limita - invent.slotAlmout[i];
            // ele so ajunta quando o dois slot tenho quantidade pequena ele uni em um so
            if (space > 0) {
              int tranfere = Math.min(invent.slotAlmout[trocaslot], space);
              invent.slotAlmout[i] += tranfere;
              invent.slotAlmout[trocaslot] -= tranfere;
              invent.cont[i].setText(invent.slotAlmout[i] > 0 ? String.valueOf(invent.slotAlmout[i]) : "");
              invent.cont[trocaslot].setText(invent.slotAlmout[trocaslot] > 0 ? String.valueOf(invent.slotAlmout[trocaslot]) : "");
              if (invent.slotAlmout[trocaslot] <= 0) {
                invent.items.set(trocaslot, null);
                invent.spait[trocaslot].setImage(Texture.empty());
                invent.spait[trocaslot].setColor(Transparent);
                invent.cont[trocaslot].setText("");
              }
            }
          } else {
            int watvalue = invent.slotAlmout[i];
            int muditem = invent.slotAlmout[trocaslot];
            invent.slotAlmout[trocaslot] = watvalue;
            invent.slotAlmout[i] = muditem;
            tocaslot(i, muditem, seleslot);
            tocaslot(trocaslot, watvalue, watslot);
          }
          select = -1;
          modvalue(-1);
        }
        break;
      }

      if (trocaslot == i && invent.items.get(i) != null && invent.items.get(trocaslot) != null) {
        slot[i].setColor(BlueWhite);
      } else slot[i].setColor(select == i ? Black : White);
    }
  }

  private void modvalue(int valuetroc) {
    trocaslot = valuetroc;
    contclick = -1;
    clicktime = 0;
  }

  private void tocaslot(int i, int whatValue, item seleslot) {
    // troca os dado
    invent.items.set(i, seleslot);
    //// troca ui e color
    if (invent.spait[i] != null) {
      invent.spait[i].setImage(seleslot != null && seleslot.ui != null ? seleslot.getSpait() : Texture.empty());
      invent.spait[i].setColor(seleslot != null ? White : Transparent);
    }
    ////
    // torca de valores
    if (invent.cont[whatValue] != null) {
      invent.cont[i].setText((whatValue) > 0 ? String.valueOf(whatValue) : null);
    }
  }

  private void RemoveSlot(int i, int quant) {
    if (invent.cont[i] == null || invent.spait[i] == null) return;
    UseNoUse(false, i);
    int quantArmaz = invent.slotAlmout[i];
    if (quant > quantArmaz) quant = quantArmaz;

    invent.slotAlmout[i] -= quant;
    RemoveItem(i, quant);
    if (invent.slotAlmout[i] > 0) invent.cont[i].setText("" + invent.slotAlmout[i]);
    else {
      invent.items.set(i, null);
      invent.spait[i].setImage(Texture.empty());
      invent.spait[i].setColor(Transparent);
      invent.cont[i].setText("");
    }
    offonObj("", false, i);
    modvalue(-1);
  } 

  private void RemoveItem(int i, int quant) {
   // removeItem remove = new removeItem();
    item rmItems = invent.items.get(i);
    if (rmItems == null || quant <= 0) return;
    removedor.CaractItem(rmItems, quant, Object, drops);
  }
}
