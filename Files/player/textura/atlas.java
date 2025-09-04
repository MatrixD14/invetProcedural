public class atlas extends Component {
  private Texture ui;
  public Texture Spait;
  public int MapSpriteX, MapSpriteY;
  public int altura = 21, largura = 21;
  public SUIImage texture;

  void start() {
    texture = myObject.findComponent("suiimage");
  } 

  void repeat() {
    texture.setImage(getSpait());
  }

  public Texture getSpait() {
    if (ui == null) return Texture.empty();
    if (Spait == null) Spait = Atlas();
    return Spait;
  }

  public Texture Atlas() {
    if (ui == null) return Texture.empty();
    Texture mapSprite = new Texture(largura, altura, true);
    int spaitx = largura * MapSpriteX;
    int spaity = altura * MapSpriteY;

    for (int y = 0; y < altura; y++) {
      for (int x = 0; x < largura; x++) {
        Color color = ui.get(x + spaitx, y + spaity);
        mapSprite.setPixel(x, y, color);
      }
    }
    mapSprite.apply();
    return mapSprite;
  }
}
