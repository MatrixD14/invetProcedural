public class item extends Component {
  public final Map<String, Texture> SpriteCach = new HashMap<String, Texture>();
  public String name, typeDC;
  public Texture ui;
  private Texture Spait;
  public VertexFile vertex;
  public int value, QuatItemGrup, maxgrup, id;
  public int MapSpriteX, MapSpriteY;
  private int altura = 32, largura = 32;
  public atributo logica;
  public item() {
    super();
  }

  public item(String name, int id, String typeDC, Texture ui, VertexFile vertex, int value, int QuatItemGrup, int maxgrup, int MapSpriteX, int MapSpriteY, atributo logica) {
    super();
    this.name = name;
    this.id = id;
    this.typeDC = typeDC;
    this.ui = ui;
    this.vertex = vertex;
    this.value = value;
    this.QuatItemGrup = QuatItemGrup;
    this.maxgrup = maxgrup;
    this.MapSpriteX = MapSpriteX;
    this.MapSpriteY = MapSpriteY;
    //this.Spait = getSpait();
    this.logica = logica;
  }

  public Texture getSpait() {
    if (ui == null) return null;
    if (Spait == null) Spait = Atlas();
    return Spait;
  }

  public void Usando() {
    if (logica != null) logica.Used(this);
  }

  public void NoUsando() {
    if (logica != null) logica.NoUsed(this);
  }
  
  public Texture Atlas() {   
    if (ui == null) return null;
    String key = name + "_" + MapSpriteX + "_" + MapSpriteY;
    Texture cach = SpriteCach.get(key);
    if (cach != null) return cach;

    Texture mapSprite = new Texture(largura, altura, true);
    int spaitx = largura * MapSpriteX;
    int spaity = altura * MapSpriteY;

    for (int x = 0; x < largura; x++) {
      int uiX = x + spaitx;
      for (int y = 0; y < altura; y++) {
        int uiY = y + spaity;
        Color corMap = ui.get(uiX, uiY);
        mapSprite.setPixel(x, y, corMap);
      }
    }
    mapSprite.apply();
    SpriteCach.put(key, mapSprite);
    return mapSprite;
  }
}
