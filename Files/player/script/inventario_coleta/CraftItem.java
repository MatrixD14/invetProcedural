public class CraftItem extends Component {
  public Texture Sprite;
  public ArrayList<Receita> receita = new ArrayList<Receita>();
  public VertexFile[] obj = new VertexFile[1];
  private invertore invent;
  private Color White, Transparent;

  void start() {
    White = new Color();
    Transparent = new Color(0, 0, 0, 0);
    invent = myObject.findComponent("invertore");
    ArrayList<item> Items =
        new ArrayList<item>() {
          {
            add(new item("picareta", 9, "Minera", Sprite, obj[0], 5, 1, 1, 0, 0, null));
            add(new item("espada", 10, "Dano", Sprite, obj[0], 10, 1, 1, 1, 0, new arma(new espada())));
            add(new item("machado", 11, "Dano", Sprite, obj[0], 15, 1, 1, 2, 0, null));
            add(new item("tocha", 8, "Luz", Sprite, obj[0], 10, 1, 64, 0, 1, new tocha()));
            add(new item("pão", 12, "Fome", Sprite, obj[1], 20, 1, 64, 1, 1, null));
            add(new item("tabua", 6, "Contrução", Sprite, obj[0], 20, 1, 64, 3, 2, null));
            add(new item("galho", 2, "Dano", Sprite, obj[0], 20, 1, 64, 0, 2, new arma(new espada())));
            add(new item("craftTable", 7, "CraftItem", Sprite, obj[1], 9, 1, 64, 3, 3, null));
            add(new item("fornalha", 13, "assar", Sprite, obj[1], 64, 1, 64, 3, 1, null));
            add(new item("baú", 14, "armazena", Sprite, obj[1], 64, 1, 64, 2, 3, null));
          }
        };
    receita.add(new Receita(new int[] {3, 3, 3, 0, 2, 0, 0, 2, 0}, Items.get(0)));
    receita.add(new Receita(new int[] {0, 3, 0, 0, 3, 0, 0, 2, 0}, Items.get(1)));
    receita.add(new Receita(new int[] {3, 2, 0, 3, 2, 0, 0, 2, 0}, Items.get(2)));
    receita.add(new Receita(new int[] {0, 4, 0, 0, 2, 0, 0, 0, 0}, Items.get(3)));
    receita.add(new Receita(new int[] {0, 0, 0, 5, 5, 5, 0, 0, 0}, Items.get(4)));
    receita.add(new Receita(new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0}, Items.get(5)));
    receita.add(new Receita(new int[] {0, 0, 0, 0, 6, 0, 0, 0, 0}, Items.get(6)));
    receita.add(new Receita(new int[] {0, 6, 6, 0, 6, 6, 0, 0, 0}, Items.get(7)));
    receita.add(new Receita(new int[] {3, 3, 3, 3, 0, 3, 3, 3, 3}, Items.get(8)));
    receita.add(new Receita(new int[] {6, 6, 6, 6, 0, 6, 6, 6, 6}, Items.get(9)));
  }

  void repeat() {
    checkItem();
    if (Input.isKeyDown("output") && invent.items.get(29) != null) craftItem();
  }

  public void checkItem() {
    List<item> CheckItem = invent.items.subList(20, 29);
    boolean checoReciete = false;
    for (Receita r : receita) {
      if (r.material(CheckItem)) {
        item checkOneObj = r.getGeraItem();
        checoReciete = true;
        if (invent.items.get(29) == null || invent.items.get(29).id != checkOneObj.id) {
          invent.items.set(29, checkOneObj);
          invent.spait[29].setImage(checkOneObj.getSpait());
          invent.spait[29].setColor(White);
        }
        invent.cont[29].setText("1");
        break;
      }
    }
    if (!checoReciete) offItem(29, false);
  }

  public void craftItem() {
    List<item> CheckItem = invent.items.subList(20, 29);
    if (invent.items.get(29) == null) return;
    for (Receita r : receita) {
      if (r.material(CheckItem)) {
        item checkOneObj = r.getGeraItem();
        if (!AddItemSlot(checkOneObj)) return;
        boolean[] uso = new boolean[9];

        for (int items = 20; items < 29; items++) {
          int SeeOneObj = r.getItems()[items - 20];
          int idItem = CheckItem.get(items - 20) != null ? CheckItem.get(items - 20).id : 0;
          if (SeeOneObj == 0 || uso[items - 20] || idItem != SeeOneObj) continue;

          invent.slotAlmout[items]--;
          if (invent.slotAlmout[items] <= 0) offItem(items, true);
          else invent.cont[items].setText("" + invent.slotAlmout[items]);
          uso[items - 20] = true;
        } 
        return;
      }
    }
  }

  public boolean AddItemSlot(item newItem) {
    int Void = -1;
    for (int i = 0; i < 20; i++) {
      item atual = invent.items.get(i);
      if (atual != null) {
        if (invent.items.get(i).id == newItem.id && atual.maxgrup > invent.slotAlmout[i]) {
          invent.cont[i].setText("" + (++invent.slotAlmout[i]));
          return true;
        }
      } else if (Void == -1) Void = i;
    }
    if (Void != -1) {
      invent.items.set(Void, newItem);
      invent.slotAlmout[Void] = 1;
      invent.spait[Void].setImage(newItem.getSpait());
      invent.spait[Void].setColor(White);
      invent.cont[Void].setText("1");
      return true;
    }
    return false;
  }

  private void offItem(int i, boolean off) {
    invent.items.set(i, null);
    invent.spait[i].setImage(Texture.empty());
    invent.spait[i].setColor(Transparent);
    if (off) invent.slotAlmout[i] = 0;
    invent.cont[i].setText("");
  }

  public class Receita {
    private int[] items = new int[9];
    private item GeraItem;

    public Receita() {
      super();
    }

    public Receita(int[] items, item GeraItem) {
      super();
      this.items = items;
      this.GeraItem = GeraItem;
    }

    public int[] getItems() {
      return items;
    }

    public item getGeraItem() {
      return GeraItem;
    }

    public boolean material(List<item> inputSlot) {
      if (inputSlot.size() != 9) return false;
      for (int i = 0; i < 9; i++) {
        int requisitos = items[i];
        int atual = inputSlot.get(i) != null ? inputSlot.get(i).id : 0;
        if (requisitos != atual) return false;
      }
      return true;
    }
  }
}
